package edu.uw.chather.ui.weather;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONObject;
import edu.uw.chather.databinding.FragmentWeatherBinding;


public class WeatherFragment extends Fragment implements LocationListener {

    //Creating a binding for the weather
    private FragmentWeatherBinding binding;

    //Holds a reference to the WeatherViewModel.
    private WeatherViewModel mViewModel;

    //Holds the location
    LocationManager locationManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWeatherBinding.inflate(inflater, container, false);
        // Inflate the layout for this fragment
        return binding.getRoot();
    }


    //I could potentially make the call here for connect from the mViewModel so that I may request
    //data and so that I may also directly call for the local weather if I need to pass an
    //argument.
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(getActivity())
                .get(WeatherViewModel.class);

        //Runtime permissions
        //Used getActivity rather than WeatherFragment or Main Activity
        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            },100);
        }

        getLocation();

    }

    @SuppressLint("MissingPermission")
    private void getLocation() {

        try{
            locationManager = (LocationManager) getActivity().getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000,5, (LocationListener) getActivity());
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*Upon a change of location, we should connect to the web service in order to update the current
    * weather conditions that will be displayed to the user. */
    @Override
    public void onLocationChanged(@NonNull Location location) {
        mViewModel.connect(String.format("%.2f",location.getLongitude()),
                String.format("%.2f",location.getLatitude()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //I need to determine how I am to save a reference to the viewmodel.
        //WeatherViewModel = model =
               // new WeatherViewModel

        //THis should provide an observer to the view model, to check responses
        mViewModel.addResponseObserver(getViewLifecycleOwner(),
                this::observeResponse);

    }

    //I need to add a function here that will do everything that I need to once I have
    //received all the data that I have requested from the web service
    public void displayWeather() {
        //Here i will do something to display the weather from web service.
    }

    /**
     * An observer on the HTTP Response from the web server. This observer should be
     * attached to SignInViewModel.
     *
     * @param response the Response from the server
     */
    private void observeResponse(final JSONObject response) {
        if (response.length() > 0) {
            if (response.has("code")) {

                    Log.e("JSON Parse Error", "the response has failed");

            } else {
                displayWeather(); //i should work with the response JSONObject here.
                //kinda like response.getString("current.temp")
            }
        } else {
            Log.d("JSON Response", "No Response");
        }
    }
}

























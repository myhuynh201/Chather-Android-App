package edu.uw.chather.ui.weather;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import org.json.JSONException;
import org.json.JSONObject;
import edu.uw.chather.databinding.FragmentWeatherBinding;


public class WeatherFragment extends Fragment {

    //Creating a binding for the weather
    private FragmentWeatherBinding binding;

    //Holds a reference to the WeatherViewModel.
    private WeatherViewModel mViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWeatherBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(getActivity())
                .get(WeatherViewModel.class);
    }



    //        mViewModel.connect(String.format("%.2f",location.getLatitude()),
    //                String.format("%.2f",location.getLongitude()));


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Send the Chicago hard-coded location coordinates
        mViewModel.connect("-94.04","33.44");
        //This should provide an observer to the view model, to check responses
        mViewModel.addResponseObserver(getViewLifecycleOwner(),
                this::observeResponse);

    }

    //I need to add a function here that will do everything that I need to once I have
    //received all the data that I have requested from the web service
    public void displayWeather(JSONObject response) {
        //Here i will do something to display the weather from web service.
        try{
            Log.d("Location", response.getString("lat"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
                displayWeather(response); //i should work with the response JSONObject here.
                //kinda like response.getString("current.temp")
            }
        } else {
            Log.d("JSON Response", "No Response");
        }
    }
}
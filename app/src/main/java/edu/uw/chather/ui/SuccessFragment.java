package edu.uw.chather.ui;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import org.json.JSONException;
import org.json.JSONObject;

import edu.uw.chather.MainActivity;
import edu.uw.chather.databinding.FragmentSuccessBinding;
import edu.uw.chather.ui.location.LocationViewModel;
import edu.uw.chather.ui.model.UserInfoViewModel;
import edu.uw.chather.ui.weather.WeatherViewModel;

/**
 * The final fragment displayed once the register fragment or the sign in fragment advances successfully.
 * This fragment will simple show a simple text hello plus the user's email address.
 * @author Charles Bryan, Duy Nguyen, Demarco Best, Alec Mac, Alejandro Olono, My Duyen Huynh
 */
public class SuccessFragment extends Fragment {

    /*
    A binding for things in the success fragment.
     */
    private FragmentSuccessBinding binding;

    private WeatherViewModel mWeatherModel;

    //The ViewModel that will store the current location
    private LocationViewModel mLocationModel;

    private static boolean mFirst = true;

    /*
    A test field for user's email address.
     */
    EditText edEmail;

    /**
     * {@inheritDoc}
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSuccessBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mWeatherModel = new ViewModelProvider(getActivity()).get(WeatherViewModel.class);
        mLocationModel = new ViewModelProvider(getActivity()).get(LocationViewModel.class);

    }

    /**
     * {@inheritDoc}
     */
    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //SuccessFragmentArgs args = SuccessFragmentArgs.fromBundle(getArguments());
        FragmentSuccessBinding mBinding = FragmentSuccessBinding.bind(getView());
        UserInfoViewModel model = new ViewModelProvider(getActivity()).get(UserInfoViewModel.class);
        mWeatherModel = new ViewModelProvider(getActivity()).get(WeatherViewModel.class);
        if (getActivity() instanceof MainActivity) {
            if(mFirst) {
                mWeatherModel.connect();
                mFirst = false;
            }
            //mWeatherModel.connect();//Double.toString(mLocationModel.getCurrentLocation().getLatitude()), Double.toString(mLocationModel.getCurrentLocation().getLongitude()));
        }
        mLocationModel.addLocationObserver(getViewLifecycleOwner(), location -> {
            //mWeatherModel.connect(Double.toString(location.getLatitude()), Double.toString(location.getLongitude()));
        });
        //This should provide an observer to the view model, to check responses
        mWeatherModel.addResponseObserver(getViewLifecycleOwner(), response -> {
            try {
                binding.txtTemperature.setText(response.getJSONObject("current").getString("temp").substring(0, 2) + "°F");
            } catch (JSONException e) {
                e.printStackTrace();
            }

        });

        mBinding.success.setText("Hello " + model.getEmail());
        boolean isClick = false;
    }
}
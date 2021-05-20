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

import edu.uw.chather.databinding.FragmentSuccessBinding;
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

    //private WeatherViewModel mViewModel;
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
        WeatherViewModel wModel = new ViewModelProvider(getActivity()).get(WeatherViewModel.class);

        wModel.connect();
        //This should provide an observer to the view model, to check responses
        wModel.addResponseObserver(getViewLifecycleOwner(),
                this::observeResponse);
        mBinding.success.setText("Hello " + model.getEmail().toString());
        boolean isClick = false;
        mBinding.buttonDarkMode.setOnClickListener(button -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES));
        mBinding.buttonLightMode.setOnClickListener(button -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO));
//        mBinding.switchMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            if(isChecked == true) {
//                Toast.makeText(getContext(), "Dark Mode On", Toast.LENGTH_LONG);
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//            } else {
//                Toast.makeText(getContext(), "Dark Mode Off", Toast.LENGTH_LONG);
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//            }
//        });

    }
    /**
     * An observer on the HTTP Response from the web server. This observer should be
     * attached to SignInViewModel.
     *
     * @param response the Response from the server
     */
    private void observeResponse(final JSONObject response){
        if (response.length() > 0) {
            if (response.has("code")) {

                Log.e("JSON Parse Error", "the response has failed");

            } else {
                try {
                    binding.txtWelcome.setText("Welcome!");
                    binding.txtTemperature.setText(response.getJSONObject("current").getString("temp") + "°F");
                } catch (JSONException e){
                    e.printStackTrace();
                }

                //displayWeather(response); //i should work with the response JSONObject here.
                //kinda like response.getString("current.temp")
            }
        } else {
            Log.d("JSON Response", "No Response");
        }
    }
}
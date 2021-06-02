/*
  WeatherFragment.java

  TCSS 450 - Spring 2021
  Chather Project
 */
package edu.uw.chather.ui.weather;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.Date;
import edu.uw.chather.databinding.FragmentWeatherBinding;

/**
 * The Weatherfragment is where all of the weather components are housed and displayed
 * @author Alejandro Cossio Olono
 */
public class WeatherFragment extends Fragment {

    /**
     * The WeatherFragment binding.
     */
    private FragmentWeatherBinding binding;

    /**
     * Holds the WeatherViewModel
     */
    private WeatherViewModel mViewModel;

    /**
     * The various text components that will accept data from the API.
     */
    private TextView txt_city_name,txt_temperature,
            txt_description,txt_date_time,txt_wind,txt_geo_coord;

    /**
     * The RecyclerView holding the WeatherForecast display
     */
    private RecyclerView recycler_forecast;

    /**
     * The RecyclerView holding the 24 hour forecast
     */
    private RecyclerView recycler_hourly_forecast;

    /**
     * A static instance of the WeatherFragment
     */
    static WeatherFragment instance;

    private static boolean mFirst = true;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWeatherBinding.inflate(inflater, container, false);

        //Making the first call, this should be calling the default call
        if(mFirst) {
            mViewModel.connect();
            mFirst = false;
        }
        txt_city_name = (TextView) binding.txtCityName;
        txt_temperature = (TextView) binding.txtTemperature;
        //txt_description = (TextView) binding.txtDescription;
        txt_date_time = (TextView) binding.txtDateTime;
        txt_wind = (TextView) binding.txtWind;
        txt_geo_coord = (TextView) binding.txtGeoCoord;
        recycler_forecast = (RecyclerView) binding.recyclerForecast;
        recycler_hourly_forecast = (RecyclerView) binding.recyclerHourlyForecast;
        recycler_hourly_forecast.setHasFixedSize(true);
        recycler_hourly_forecast.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
        recycler_forecast.setHasFixedSize(true);
        recycler_forecast.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false));
        return binding.getRoot();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(getActivity())
                .get(WeatherViewModel.class);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
    }

    /**
     * Empty Constructor of the Fragment
     */
    public WeatherFragment() {
    }

    /**
     * Retrieves the static instance of the Fragment
     * @return the static instance
     */
    public static WeatherFragment getInstance() {
        if(instance == null){
            instance = new WeatherFragment();
        }
        return instance;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentWeatherBinding binding = FragmentWeatherBinding.bind(getView());
        binding.buttonSearch.setOnClickListener(this::searchZip);
        //WeatherFragmentArgs args = WeatherFragmentArgs.fromBundle(getArguments());
        //Send the Tacoma hard-coded location coordinates
        //mViewModel.connect();
        //This should provide an observer to the view model, to check responses
//        mViewModel.addResponseObserver(getViewLifecycleOwner(),
//                this::observeResponse);
        //Location should be the map that we make in the web-service.
        mViewModel.addLocationObserver(getViewLifecycleOwner(), location -> {
            if(!location.isEmpty()) {
                binding.textviewZipData.setText(location.get("zip"));
                binding.txtCityName.setText(location.get("city"));
            }
        });
        mViewModel.addResponseObserver(getViewLifecycleOwner(), response -> {
            if (response.length() > 0) {
                try {
                    //binding.txtDescription.setText(new StringBuilder("Weather in " + binding.txtCityName));
                    binding.txtTemperature.setText(response.getJSONObject("current").getString("temp").substring(0, 2) + "°F");
                    Date date = new Date(Integer.parseInt(response.getJSONObject("current").getString("dt")) * 1000L);
                    SimpleDateFormat sdf = new SimpleDateFormat("hh:mma EEE MM/d/yyyy");
                    binding.txtDateTime.setText(sdf.format(date));
                    binding.txtGeoCoord.setText("[" + response.getString("lat") + "," +
                            response.getString("lon") + "]");
                    binding.recyclerForecast.setAdapter(new WeatherForecastAdapter(getContext(), response));
                    binding.recyclerHourlyForecast.setAdapter(new WeatherHourlyForecastAdapter(getContext(), response));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void searchZip(View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        mViewModel.connect(binding.textviewZipData.getText().toString());
    }
}
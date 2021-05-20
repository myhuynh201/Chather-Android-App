package edu.uw.chather.ui.weather;

import android.location.Address;
import android.location.Geocoder;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import edu.uw.chather.databinding.FragmentWeatherBinding;


public class WeatherFragment extends Fragment {

    //Creating a binding for the weather
    private FragmentWeatherBinding binding;

    //Holds a reference to the WeatherViewModel.
    private WeatherViewModel mViewModel;



    //Current weather pic
    //ImageView mImgWeather;

    private TextView txt_city_name,txt_humidity,txt_sunrise,txt_sunset,txt_pressure,txt_temperature,
            txt_description,txt_date_time,txt_wind,txt_geo_coord;

    private RecyclerView recycler_forecast;
    private RecyclerView recycler_hourly_forecast;
    private LinearLayout weather_panel;
    private ProgressBar loading;

    static WeatherFragment instance;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWeatherBinding.inflate(inflater, container, false);

        //mImgWeather = (ImageView) binding.imgWeather;
        txt_city_name = (TextView) binding.txtCityName;
        txt_humidity = (TextView) binding.txtHumidity;
        txt_sunrise = (TextView) binding.txtSunrise;
        txt_sunset = (TextView) binding.txtSunset;
        txt_pressure = (TextView) binding.txtPressure;
        txt_temperature = (TextView) binding.txtTemperature;
        txt_description = (TextView) binding.txtDescription;
        txt_date_time = (TextView) binding.txtDateTime;
        txt_wind = (TextView) binding.txtWind;
        txt_geo_coord = (TextView) binding.txtGeoCoord;
        recycler_forecast = (RecyclerView) binding.recyclerForecast;
        recycler_hourly_forecast = (RecyclerView) binding.recyclerHourlyForecast;
        recycler_hourly_forecast.setHasFixedSize(true);
        recycler_hourly_forecast.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
        recycler_forecast.setHasFixedSize(true);
        recycler_forecast.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false));

        weather_panel = (LinearLayout) binding.weatherPanel;
        loading = (ProgressBar) binding.loading;
        return binding.getRoot();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(getActivity())
                .get(WeatherViewModel.class);
    }

    public WeatherFragment() {

    }

    public static WeatherFragment getInstance() {
        if(instance == null){
            instance = new WeatherFragment();
        }
        return instance;
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
        mViewModel.connect();
        //This should provide an observer to the view model, to check responses
        mViewModel.addResponseObserver(getViewLifecycleOwner(),
                this::observeResponse);
    }


    public void displayWeather(JSONObject response) {

        //Loading the information
        try{
            txt_city_name.setText("Tacoma, WA");
            txt_description.setText(new StringBuilder("Weather in Tacoma, WA"));
            txt_temperature.setText(response.getJSONObject("current").getString("temp") + "°F");
            Date date = new Date(Integer.parseInt(response.getJSONObject("current").getString("dt")) * 1000L);
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mma EEE MM/d/yyyy");
            txt_date_time.setText(sdf.format(date));
            txt_pressure.setText(response.getJSONObject("current").getString("pressure") + " hPa");
            txt_humidity.setText(response.getJSONObject("current").getString("humidity") + " %");
            date = new Date(Integer.parseInt(response.getJSONObject("current").getString("sunrise")) * 1000L);
            sdf = new SimpleDateFormat("hh:mma ");
            txt_sunrise.setText(sdf.format(date));
            date = new Date(Integer.parseInt(response.getJSONObject("current").getString("sunset")) * 1000L);
            sdf = new SimpleDateFormat("hh:mma ");
            txt_sunset.setText(sdf.format(date));
            txt_geo_coord.setText("[" + response.getString("lat") + "," +
                    response.getString("lon") + "]");
            WeatherForecastAdapter adapter = new WeatherForecastAdapter(getContext(), response);
            WeatherHourlyForecastAdapter madapter = new WeatherHourlyForecastAdapter(getContext(), response);
            recycler_hourly_forecast.setAdapter(madapter);
            recycler_forecast.setAdapter(adapter);
            //Displaying the panel
            weather_panel.setVisibility(View.VISIBLE);
            loading.setVisibility(View.GONE);
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
/*
  WeatherHourlyForecastAdapter.java

  TCSS 450 - Spring 2021
  Chather Project
 */
package edu.uw.chather.ui.weather;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.uw.chather.R;

/**
 * The adapter for the WeatherHourlyForecast
 * @author Alejandro Cossio Olono
 */
public class WeatherHourlyForecastAdapter extends RecyclerView.Adapter<WeatherHourlyForecastAdapter.MyViewHolder> {

    /**
     * The context to be observed
     */
    private final Context context;

    /**
     * The API response
     */
    private final JSONObject response;

    /**
     * Constructor for the WeatherHourlyForecastAdapter
     * @param context The context to be observed
     * @param response The Web Service Response
     */
    public WeatherHourlyForecastAdapter(Context context, JSONObject response) {
        this.context = context;
        this.response = response;
    }

    /**
     * An encompassing class for the object holding the cards
     */
    public class MyViewHolder extends RecyclerView.ViewHolder{

        /**
         * The TextViews which will display relevant information
         * regarding date, description, and temperature.
         */
        TextView txt_date, txt_description, txt_temperature_high;

        /**
         * Constructor for the MyViewHolder
         * @param itemView The card item holding the 24-hour Forecast weather information
         */
        public MyViewHolder(View itemView){
            super(itemView);
            txt_date = (TextView) itemView.findViewById(R.id.txt_date);
            txt_temperature_high = (TextView) itemView.findViewById(R.id.txt_temperature_high);
            txt_description = (TextView) itemView.findViewById(R.id.txt_description);

        }
    }

    /**
     * The oncreate method for the ViewHolder
     * @param parent The ViewGroup that the ViewHolder is a part of.
     * @param viewType The variety of the view.
     * @return The view to reference.
     */
    @NonNull
    @Override
    public WeatherHourlyForecastAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_weather_hourly_forecast,parent, false);
        return new MyViewHolder(itemView);
    }

    /**
     * Sets the TextViews with their relevant data.
     * @param holder The ViewHolder
     * @param position The current place in the Weather week list.
     */
    @Override
    public void onBindViewHolder(@NonNull WeatherHourlyForecastAdapter.MyViewHolder holder, int position) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("h:mma");
            Date date = new Date(Integer.parseInt(response.getJSONArray("hourly").
                    getJSONObject(position).getString("dt")) * 1000L);
            holder.txt_date.setText(sdf.format(date));
            holder.txt_temperature_high.setText(response.getJSONArray("hourly").
                    getJSONObject(position).getString("temp").substring(0, 2) + "°F");
            holder.txt_description.setText(response.getJSONArray("hourly").
                    getJSONObject(position).getJSONArray("weather").
                    getJSONObject(0).getString("description"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the number of hours to record data for.
     * @return The number of hours of data to analyze.
     */
    @Override
    public int getItemCount() {
        int itemCnt = 0;
        try {
            itemCnt =  response.getJSONArray("hourly").length() - 23;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return itemCnt;
    }
}

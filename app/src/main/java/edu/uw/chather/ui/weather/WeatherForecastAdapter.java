/*
  WeatherForecastAdapter.java

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
 * The WeatherForecastAdapter the adapter for the RecyclerView.
 * @author Alejandro Cossio Olono
 */
public class WeatherForecastAdapter extends RecyclerView.Adapter<WeatherForecastAdapter.MyViewHolder> {

    /**
     * The context to be observed
     */
    private Context context;

    /**
     * The API response
     */
    private JSONObject response;

    /**
     * Constructor for the WeatherForecastAdapter
     * @param context The context to be observed
     * @param response The Web Service Response
     */
    public WeatherForecastAdapter(Context context, JSONObject response) {
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
        TextView txt_date, txt_descriptions, txt_temperature;

        /**
         * Constructor for the MyViewHolder
         * @param itemView The card item holding the Forecast weather information
         */
        public MyViewHolder(View itemView){
            super(itemView);
            txt_date = (TextView) itemView.findViewById(R.id.txt_date);
            txt_descriptions = (TextView) itemView.findViewById(R.id.txt_descriptions);
            txt_temperature = (TextView) itemView.findViewById(R.id.txt_temperature);

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
    public WeatherForecastAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_weather_forecast,parent, false);
        return new MyViewHolder(itemView);
    }

    /**
     * Sets the TextViews with their relevant data.
     * @param holder The ViewHolder
     * @param position The current place in the Weather week list.
     */
    @Override
    public void onBindViewHolder(@NonNull WeatherForecastAdapter.MyViewHolder holder, int position) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("h:mma EEE MM/d/yyyy");
            Date date = new Date(Integer.parseInt(response.getJSONArray("daily").
                    getJSONObject(position).getString("dt")) * 1000L);
            holder.txt_date.setText(sdf.format(date));
            holder.txt_descriptions.setText(response.getJSONArray("daily").
                    getJSONObject(position).getJSONArray("weather").
                    getJSONObject(0).getString("description"));
            holder.txt_temperature.setText(response.getJSONArray("daily").
                    getJSONObject(position).getJSONObject("temp").getString("day") + "°F");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the number of days in the week.
     * @return The number of days of data.
     */
    @Override
    public int getItemCount() {
        int itemCnt = 0;
        try {
            itemCnt =  response.getJSONArray("daily").length();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return itemCnt;
    }
}

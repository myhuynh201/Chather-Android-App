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

public class WeatherForecastAdapter extends RecyclerView.Adapter<WeatherForecastAdapter.MyViewHolder> {

    private Context context;
    private JSONObject response;

    public WeatherForecastAdapter(Context context, JSONObject response) {
        this.context = context;
        this.response = response;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView txt_date, txt_descriptions, txt_temperature;
        public MyViewHolder(View itemView){
            super(itemView);
            txt_date = (TextView) itemView.findViewById(R.id.txt_date);
            txt_descriptions = (TextView) itemView.findViewById(R.id.txt_descriptions);
            txt_temperature = (TextView) itemView.findViewById(R.id.txt_temperature);

        }
    }
    @NonNull
    @Override
    public WeatherForecastAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_weather_forecast,parent, false);
        return new MyViewHolder(itemView);
    }

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

/*

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

public class WeatherHourlyForecastAdapter extends RecyclerView.Adapter<WeatherHourlyForecastAdapter.MyViewHolder> {

    private Context context;
    private JSONObject response;

    public WeatherHourlyForecastAdapter(Context context, JSONObject response) {
        this.context = context;
        this.response = response;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView txt_date, txt_description, txt_temperature_high;

        public MyViewHolder(View itemView){
            super(itemView);
            txt_date = (TextView) itemView.findViewById(R.id.txt_date);
            txt_temperature_high = (TextView) itemView.findViewById(R.id.txt_temperature_high);
            txt_description = (TextView) itemView.findViewById(R.id.txt_description);

        }
    }

    @NonNull
    @Override
    public WeatherHourlyForecastAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_weather_hourly_forecast,parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherHourlyForecastAdapter.MyViewHolder holder, int position) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("h:mma");
            Date date = new Date(Integer.parseInt(response.getJSONArray("hourly").
                    getJSONObject(position).getString("dt")) * 1000L);
            holder.txt_date.setText(sdf.format(date));
            holder.txt_temperature_high.setText(response.getJSONArray("hourly").
                    getJSONObject(position).getString("temp") + "°F");
            holder.txt_description.setText(response.getJSONArray("hourly").
                    getJSONObject(position).getJSONArray("weather").
                    getJSONObject(0).getString("description"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

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

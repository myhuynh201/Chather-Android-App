/*
  WeatherViewModel.java

  TCSS 450 - Spring 2021
  Chather Project
 */
package edu.uw.chather.ui.weather;


import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import edu.uw.chather.R;
import edu.uw.chather.io.RequestQueueSingleton;
import edu.uw.chather.ui.model.UserInfoViewModel;

/**
 * The ViewModel for the Weather components.
 * @author Alejandro Cossio Olono
 */
public class WeatherViewModel extends AndroidViewModel {

    /**
    A mutable live data that is able to change certain weather properties from response.
     */
    private MutableLiveData<JSONObject> mResponse;

    private MutableLiveData<Map<String, String>> mLocationData;

    /**
     * Constructor for the weather view model.
     * @param application The application.
     */
    public WeatherViewModel(@NonNull Application application) {
        super(application);
        mResponse = new MutableLiveData<>();
        mResponse.setValue(new JSONObject());
        mLocationData = new MutableLiveData<>();
    }

    /**
     * Observes mutable live data.
     * @param owner The owner.
     * @param observer The observer.
     */
    public void addResponseObserver(@NonNull LifecycleOwner owner,
                                    @NonNull Observer<? super JSONObject> observer) {
        mResponse.observe(owner, observer);
    }

    public void addLocationObserver(@NonNull LifecycleOwner owner,
                                    @NonNull Observer<? super Map<String, String>> observer) {
        mLocationData.observe(owner, observer);
    }

    /**
     * In case of a successful response,
     * @param result the response from the request made to web-service
     *
     *               Will likely need to make a change here because the result from the web-service
     *               will encompass both the location and the weather location. So, I need to retrieve
     *               the location data, but it seems I do that below in the TRY block, so its fine for
     *               now, though perhaps Ill need to make a change so that weatherFragment is indeed
     *               receiving and working with the lcoation and weather data appropriately.
     */

    private void handleResult(final JSONObject result) {
        //This will send the weather and location data to WeatherFragment
        try {
            Map<String, String> location = new HashMap<String, String>();
            JSONObject locationData = result.getJSONObject("location");
            location.put("zip", locationData.getString("zip"));
            location.put("city", locationData.getString("city"));
            location.put("Lat", locationData.getString("latitude"));
            location.put("Long", locationData.getString("longitude"));
            mLocationData.setValue(location);
            JSONObject currentRes = result.getJSONObject("weatherData");
            mResponse.setValue(currentRes);

            //The above will send the location info to WeatherFragment
            Log.d("Checkup", result.getString("lat"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the error from the server side.
     * @param error The error.
     */
    private void handleError(final VolleyError error) {
        if (Objects.isNull(error.networkResponse)) {
            try {
                mResponse.setValue(new JSONObject("{" +
                        "error:\"" + error.getMessage() +
                        "\"}"));
            } catch (JSONException e) {
                Log.e("JSON PARSE", "JSON Parse Error in handleError");
            }
        }
        else {
            String data = new String(error.networkResponse.data, Charset.defaultCharset())
                    .replace('\"', '\'');
            try {
                JSONObject response = new JSONObject();
                response.put("code", error.networkResponse.statusCode);
                response.put("data", new JSONObject(data));
                mResponse.setValue(response);
            } catch (JSONException e) {
                Log.e("JSON PARSE", "JSON Parse Error in handleError");
            }
        }
    }

    /**
     * Connect to webservice to get default weather data.
     */
    public void connect() {

        String url = getApplication().getResources().getString(R.string.base_url) +
                "weather?zip=98405";

        Request request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                //no body for this get request
                this::handleResult,
                this::handleError) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                // add headers <key,value>
                headers.put("Authorization", UserInfoViewModel.getmJwt());
                return headers;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                10_000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Instantiate the RequestQueue and add the request to the queue
        Volley.newRequestQueue(getApplication().getApplicationContext())
                .add(request);
    }

    /**
     * Connect to webservice to get weather data through the use of a zip.
     */
    public void connect(String zip) {

        String url = getApplication().getResources().getString(R.string.base_url) +
                "weather?zip=" + zip;

        Request request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                //no body for this get request
                this::handleResult,
                this::handleError) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                // add headers <key,value>
                headers.put("Authorization", UserInfoViewModel.getmJwt());
                return headers;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                10_000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Instantiate the RequestQueue and add the request to the queue
        Volley.newRequestQueue(getApplication().getApplicationContext())
                .add(request);
    }

    /**
     * Connect to webservice to get weather data through the use of a longitude and the latitude.
     */
    public void connect(String lat, String lng) {

        String url = getApplication().getResources().getString(R.string.base_url) +
                "weather?latitude=" + lat + "&longitude=" + lng;

        Request request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                //no body for this get request
                this::handleResult,
                this::handleError) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                // add headers <key,value>
                headers.put("Authorization", UserInfoViewModel.getmJwt());
                return headers;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                10_000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Instantiate the RequestQueue and add the request to the queue
        Volley.newRequestQueue(getApplication().getApplicationContext())
                .add(request);
    }


        /**
         * The old connect method to the hardcoded endpoint
         */
//    public void connect() {
//        String url = "https://tcss450-android-app.herokuapp.com/weather/hardcoded";
//        JSONObject body = new JSONObject();
//        try {
////            body.put("lat", latitude);
////            body.put("lon", longitude);
//            body.put("exclude", new String("minutely, alerts"));
//            body.put("units", new String("imperial"));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        Request request = new JsonObjectRequest(
//                Request.Method.GET,
//                url,
//                body,
//                this::handleResult,
//                this::handleError) {
//            @Override
//            public Map<String, String> getHeaders() {
//                Map<String, String> headers = new HashMap<>();
//                headers.put("Authorization", UserInfoViewModel.getmJwt());
//                Log.d("Check JWT",UserInfoViewModel.getmJwt());
//                return headers;
//            }
//        };
//        try {
//            Log.d("Responsiveness", body.getString("lat"));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        request.setRetryPolicy(new DefaultRetryPolicy(
//                10_000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        //Instantiate the RequestQueue and add the request to the queue
//        Volley.newRequestQueue(getApplication().getApplicationContext())
//                .add(request);
//    }


}

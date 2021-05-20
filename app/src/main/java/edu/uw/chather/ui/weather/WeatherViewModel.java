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

import edu.uw.chather.io.RequestQueueSingleton;
import edu.uw.chather.ui.model.UserInfoViewModel;

public class WeatherViewModel extends AndroidViewModel {

    //Holds the result of the web call.
    private MutableLiveData<JSONObject> mResponse;

    //Instantiating the JSONObject with a temp value.
    public WeatherViewModel(@NonNull Application application) {
        super(application);
        mResponse = new MutableLiveData<>();
        mResponse.setValue(new JSONObject());
    }

    //This should allow for observers to be added to the LiveData
    public void addResponseObserver(@NonNull LifecycleOwner owner,
                                    @NonNull Observer<? super JSONObject> observer) {
        mResponse.observe(owner, observer);
    }

    private void handleResult(final JSONObject result) {
        mResponse.setValue(result);
        try {
            Log.d("Checkup", result.getString("lat"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /*Should there be an error in the REQUEST, the code below will run. */
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
     *
     */
    public void connect() {
        String url = "https://tcss450-android-app.herokuapp.com/weather/hardcoded";
        JSONObject body = new JSONObject();
        try {
            body.put("lat", latitude);
            body.put("lon", longitude);
            body.put("exclude", new String("minutely, alerts"));
            body.put("units", new String("imperial"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Request request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                body, //we require a few things of the API call.
                this::handleResult, //mResponse::setValue
                this::handleError) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", UserInfoViewModel.getmJwt());
                Log.d("Check JWT",UserInfoViewModel.getmJwt());
                return headers;
            }
        };
        try {
            Log.d("Responsiveness", body.getString("lat"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        request.setRetryPolicy(new DefaultRetryPolicy(
                10_000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Instantiate the RequestQueue and add the request to the queue
        Volley.newRequestQueue(getApplication().getApplicationContext())
                .add(request);
    }


}

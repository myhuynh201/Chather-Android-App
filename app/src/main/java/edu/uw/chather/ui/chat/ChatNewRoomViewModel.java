package edu.uw.chather.ui.chat;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.Charset;
import java.security.Provider;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import edu.uw.chather.R;
import edu.uw.chather.io.RequestQueueSingleton;
import edu.uw.chather.ui.model.UserInfoViewModel;

public class ChatNewRoomViewModel extends AndroidViewModel {


    /**
     * mutable live data for response
     */
    private final MutableLiveData<JSONObject> mResponse;

    /**
     * Constructor for sent chat viewmodel
     * @param application current application
     */
    public ChatNewRoomViewModel(@NonNull Application application) {
        super(application);
        mResponse = new MutableLiveData<>();
//        mResponse.setValue(new JSONObject());
    }

    /**
     * Adds response observer
     * @param owner Lifecycle owner
     * @param observer Observer
     */
    public void addResponseObserver(@NonNull LifecycleOwner owner,
                                    @NonNull Observer<? super JSONObject> observer) {
        mResponse.observe(owner, observer);
    }

    /**
     * Create new chat
     * @param jwt jwt token for user
     * @param members members to be added
     */
    public void createNewChat(final String jwt, final List<String> members) {
        String url = getApplication().getResources().getString(R.string.base_url) +
                "chats/startChat/usernames";
        JSONObject body = new JSONObject();
        JSONArray arr = new JSONArray();
        for (String member : members) {
            arr.put(member);
        }
        try {
            body.put("members", arr);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Request request = new JsonObjectRequest(
                Request.Method.PUT,
                url,
                body, //push token found in the JSONObject body
                mResponse::setValue, // we get a response but do nothing with it
                this::handleError) {

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                // add headers <key,value>
                headers.put("Authorization", jwt);
                return headers;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(
                10_000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Instantiate the RequestQueue and add the request to the queue
        RequestQueueSingleton.getInstance(getApplication().getApplicationContext())
                .addToRequestQueue(request);
    }


    /**
     * Handles error by printing to log
     * @param error request error
     */
    private void handleError(final VolleyError error) {
        if (Objects.isNull(error.networkResponse)) {
            Log.e("NETWORK ERROR", error.getMessage());
        }
        else {
            String data = new String(error.networkResponse.data, Charset.defaultCharset());
            Log.e("CLIENT ERROR",
                    error.networkResponse.statusCode +
                            " " +
                            data);
        }
    }
}


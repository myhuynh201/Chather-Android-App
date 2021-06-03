package edu.uw.chather.ui.contact;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.uw.chather.io.RequestQueueSingleton;
import edu.uw.chather.ui.model.UserInfoViewModel;

public class ContactSearchViewModel extends AndroidViewModel {

    private final MutableLiveData<List<Contact>> mContactList;

    public ContactSearchViewModel(@NonNull Application application) {
        super(application);
        mContactList = new MutableLiveData<>();
        mContactList.setValue(new ArrayList<>());
    }

    public void addContactListObserver(@NonNull LifecycleOwner owner,
                                    @NonNull Observer<? super List<Contact>> observer) {
        mContactList.observe(owner, observer);
    }

    private void handleError(final VolleyError error) {
        //you should add much better error handling in a production release.
        //i.e. YOUR PROJECT

        Log.e("CONNECTION ERROR", "Block fatal error");
        error.getMessage();
    }

    /**Dismantles the JSONObject into its parts and builds a contact object from them.
     * Contact objects are placed in a list that is used elsewhere.
     * @param result The json result set returned from the network query.
     */
    private void handleResult(final JSONObject result) {

        try{
            //Stops issue where the contact list would duplicate itself on load.

            //Extracts the SQL query results from the result set as a JSONArray
            JSONArray jsonstring = result.getJSONArray("rows");
            int length = jsonstring.length();
            for(int x = 0; x < length; x++){
                JSONObject subOb = (JSONObject) jsonstring.get(x);
                //Add the important bits of this array into a Contact object and slap that onto the list.
                mContactList.getValue().add(
                        new Contact(subOb.getString("firstname"), subOb.getString("lastname"), subOb.getString("username"), subOb.getInt("memberid") ));

            }
            mContactList.setValue(mContactList.getValue());
        }
        catch (Exception e){
            Log.e("Error", e.getMessage());
        }

    }

    public void connectGet(String searchParam) {
        mContactList.getValue().clear();
        String url =
                "https://tcss450-android-app.herokuapp.com/contacts/search";


        Request request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null, //no body for this get request
                this::handleResult,
                this::handleError) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                // add headers <key,value>
                headers.put("Authorization", UserInfoViewModel.getmJwt());
                headers.put("searchP", searchParam);
                return headers;
            } };

        request.setRetryPolicy(new DefaultRetryPolicy(
                10_000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Instantiate the RequestQueue and add the request to the queue
        RequestQueueSingleton.getInstance(getApplication().getApplicationContext())
                .addToRequestQueue(request);
    }

    public void handleSend(final JSONObject object){
        Toast.makeText(getApplication(), "Contact request sent", Toast.LENGTH_LONG);
        mContactList.getValue().clear();
        mContactList.setValue(mContactList.getValue());

    }

    public void sendRequest(int memberID){
        String url =
                "https://tcss450-android-app.herokuapp.com/contacts/create";

        Request request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                null, //no body for this get request
                this::handleSend,
                this::handleError) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                // add headers <key,value>
                headers.put("Authorization", UserInfoViewModel.getmJwt());
                headers.put("memberid", memberID +"");
                return headers;
            } };
        request.setRetryPolicy(new DefaultRetryPolicy(
                10_000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Instantiate the RequestQueue and add the request to the queue
        RequestQueueSingleton.getInstance(getApplication().getApplicationContext())
                .addToRequestQueue(request);
    }





    public List<Contact> getContactList(){
        return mContactList.getValue();
    }
}

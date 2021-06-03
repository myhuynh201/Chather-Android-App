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
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.uw.chather.io.RequestQueueSingleton;
import edu.uw.chather.ui.model.UserInfoViewModel;

public class ContactRequestViewModel extends AndroidViewModel {

    private final MutableLiveData<List<Contact>> mContactList;

    public ContactRequestViewModel(@NonNull Application application) {
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

    public void connectGet() {
        mContactList.getValue().clear();
        String url =
                "https://tcss450-android-app.herokuapp.com/contacts/getrequests";


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

    public void handleVerify(JSONObject object){
        Toast.makeText(getApplication(), "Added contact!", Toast.LENGTH_LONG);
        connectGet();
    }

    public void verifyRequest(int memberID){
        String url =
                "https://tcss450-android-app.herokuapp.com/contacts/verify";


        Request request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                null, //no body for this get request
                this::handleVerify,
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

    public void contactDelete(int memberID){
        String url =
                "https://tcss450-android-app.herokuapp.com/contacts/delete?memberid="
                        +memberID;
        Request request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                null, //no body for this get request
                this::handleDelete,
                this::handleError) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                // add headers <key,value>
//                headers.put("Authorization", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJl" +
//                        "bWFpbCI6ImNmYjMxQGZha2UuZW1haWwuY29tIiwibWVtYmVyaWQiOjMsImlhdCI6MTY" +
//                        "xODI2ODY2OSwiZXhwIjoxNjIzNDUyNjY5fQ.Y5t-1ibUMChZPe9eiavwCA3XbHhGdiM" +
//                        "NEdpSmCxI1Ow");
                headers.put("Authorization", UserInfoViewModel.getmJwt());
                headers.put("memberid", memberID +"");
                Log.d("Check JWT",UserInfoViewModel.getmJwt());
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

    public void handleDelete(JSONObject object){
        connectGet();
    }
    public List<Contact> getContactList(){
        return mContactList.getValue();
    }
}

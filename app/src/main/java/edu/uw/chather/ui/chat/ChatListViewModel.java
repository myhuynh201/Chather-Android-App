package edu.uw.chather.ui.chat;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import edu.uw.chather.R;
import edu.uw.chather.io.RequestQueueSingleton;
import edu.uw.chather.ui.model.UserInfoViewModel;

/**
 * ChatListViewModel Stores information for the chat list
 *
 * @author Alec Mac
 */
public class ChatListViewModel extends AndroidViewModel {

    /**
     * Map of chatrooms
     */
//    private Map<Integer, MutableLiveData<List<Chatroom>>> mChatrooms;

    /**
     * List of chatrooms
     */
    private MutableLiveData<List<Chatroom>> mList;

    /**
     * constructor for view model
     *
     * @param application the current application
     */
    public ChatListViewModel(@NonNull Application application) {
        super(application);
        mList = new MutableLiveData<>(new ArrayList<Chatroom>());

    }

    /**
     * Register as an observer to listen to a specific chat room's list of messages.
     *
     * @param jwt      the jwt of the user
     * @param owner    the fragments lifecycle owner
     * @param observer the observer
     */
    public void addChatroomObserver(String jwt,
                                    @NonNull LifecycleOwner owner,
                                    @NonNull Observer<? super List<Chatroom>> observer) {
        mList.observe(owner, observer);
    }

//    /**
//     * Return a reference to the List<> associated with the chat room. If the View Model does
//     * not have a mapping for this chatID, it will be created.
//     *
//     * WARNING: While this method returns a reference to a mutable list, it should not be
//     * mutated externally in client code. Use public methods available in this class as
//     * needed.
//     *
//     * @param jwt the id of the member to retrieve chats for
//     * @return a reference to the list of chatrooms
//     */
//    public List<Chatroom> getChatroomListByMemberId(final String jwt) {
//        return getOrCreateMapEntry(jwt).getValue();
//    }

    /**
     * //     * Gets or creates map entry if does not exist
     * //     * @param jwt the JWT for the current user
     * //     * @return live data for chatrooms
     * //
     */
//    private MutableLiveData<List<Chatroom>> getOrCreateMapEntry(final String jwt) {
//        if (!mChatrooms.containsKey(jwt)) {
//            mChatrooms.put(jwt, new MutableLiveData<>(new ArrayList<>()));
//        }
//        return mChatrooms.get(jwt);
//    }
    public List<Chatroom> getChatroomList() {
        return mList.getValue();
    }


    /**
     * Makes a request to the web service to get the first batch of messages for a given Chat Room.
     * Parses the response and adds the ChatMessage object to the List associated with the
     * ChatRoom. Informs observers of the update.
     * <p>
     * Subsequent requests to the web service for a given chat room should be made from
     * getNextMessages()
     *
     * @param jwt the users signed JWT
     */
    public void getChatrooms(final String jwt) {
        String url = getApplication().getResources().getString(R.string.base_url) +
                "chats/member";

        Request request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null, //no body for this get request
                this::handleSuccess,
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
     * When a chat message is received externally to this ViewModel, add it
     * with this method.
     *
     * @param jwt
     * @param chatroom
     */
    public void addChatroom(final String jwt, final Chatroom chatroom) {
        List<Chatroom> list = getChatroomList();
        list.add(chatroom);
        mList.setValue(list);
    }




    private void handleSuccess(final JSONObject response) {
        List<Chatroom> list;
        if (!response.has("rows")) {
            throw new IllegalStateException("Unexpected response in ChatViewModel: " + response);
        }
        Log.d("RESPONSE LOOKS LIKE", "handleSuccess: " + response);
        try {

            list = getChatroomList();
            JSONArray chatrooms = response.getJSONArray("rows");
            for (int i = 0; i < chatrooms.length(); i++) {
                JSONObject chatroom = chatrooms.getJSONObject(i);
                int chatid = chatroom.getInt("chatid");
                String username = chatroom.getString("username");
                boolean containsChatId = false;
                for (Chatroom room : list) {
                    if (room.getChatId() == chatid) {
                        containsChatId = true;
                        if (!room.containsMember(username)) {
                            room.addChatMember(username);
                        }
                    }
                }
                if (!containsChatId) {
                    Chatroom newRoom = new Chatroom(chatid, new ArrayList<>(Arrays.asList(username)));
                    list.add(newRoom);
                }
            }
            //inform observers of the change (setValue)
            mList.setValue(list);

        } catch (JSONException e) {
            Log.e("JSON PARSE ERROR", "Found in handle Success ChatViewModel");
            Log.e("JSON PARSE ERROR", "Error: " + e.getMessage());
        }
    }

    /**
     * handles error
     *
     * @param error error from response
     */
    private void handleError(final VolleyError error) {
        if (Objects.isNull(error.networkResponse)) {
            Log.e("NETWORK ERROR", error.getMessage());
        } else {
            String data = new String(error.networkResponse.data, Charset.defaultCharset());
            Log.e("CLIENT ERROR",
                    error.networkResponse.statusCode +
                            " " +
                            data);
        }
    }

}

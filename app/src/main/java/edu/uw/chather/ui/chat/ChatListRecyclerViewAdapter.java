package edu.uw.chather.ui.chat;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.card.MaterialCardView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.uw.chather.R;
import edu.uw.chather.databinding.FragmentChatroomBinding;
import edu.uw.chather.io.RequestQueueSingleton;
import edu.uw.chather.ui.model.UserInfoViewModel;

/**
 * ChatListRecyclerViewAdapter creates recycler views for chatrooms
 * @author Alec Mac
 */
public class ChatListRecyclerViewAdapter extends RecyclerView.Adapter<ChatListRecyclerViewAdapter.ChatroomViewHolder> {


    private Context mContext;

    /**
     * list of chatrooms
     */
    private final List<Chatroom> mChatrooms;

    private int lastDeletedPosition;

    /**
     * constructor, initializing list of chatrooms
     * @param chatrooms list of chatrooms
     */
    public ChatListRecyclerViewAdapter(List<Chatroom> chatrooms, Context context) {
        this.mChatrooms = chatrooms;
        this.mContext = context;
        lastDeletedPosition = 0;
    }

    @NonNull
    @Override
    public ChatroomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChatroomViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.fragment_chatroom, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChatroomViewHolder holder, int position) {
        Chatroom cChatroom = mChatrooms.get(position);
        holder.setPreview(cChatroom);
        holder.mView.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(ChatListFragmentDirections.actionChatListFragmentToChatFragment(cChatroom.getChatId(), cChatroom.toString()));
        });
    }

    @Override
    public int getItemCount() {
        return mChatrooms.size();
    }


    public Context getContext() {
        return mContext;
    }

    public void deleteItem(int position) {
        int chatid = mChatrooms.get(position).getChatId();
        lastDeletedPosition = position;
        String url = "https://tcss450-android-app.herokuapp.com/chats/delete";
        JSONObject body = new JSONObject();
        try {
            body.put("chatid", chatid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Request request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                body,
                this::handleDeleteSuccess,
                this::handleDeleteError) {

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
        RequestQueueSingleton.getInstance(getContext())
                .addToRequestQueue(request);
    }

    private void handleDeleteSuccess(JSONObject jsonObject) {
        Log.d("CHAT DELETE", "SUCCESS" + jsonObject);
        mChatrooms.remove(lastDeletedPosition);
        notifyItemRemoved(lastDeletedPosition);
    }

    private void handleDeleteError(VolleyError volleyError) {
        Log.d("CHAT DELETE", "ERROR" + volleyError);
    }





    /**
     * Viewholder for a chatroom
     */
    public class ChatroomViewHolder extends RecyclerView.ViewHolder {

        //    public class ChatroomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        /**
         * the view associated with this viewholder
         */
        private final View mView;

        /**
         * binding stores the chatroom information
         */
        private final FragmentChatroomBinding binding;


        /**
         * constructs a chatroom view holder
         * @param itemView the view created for this viewholder
         */
        public ChatroomViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            binding = FragmentChatroomBinding.bind(itemView);
            mContext = mView.getContext();
        }

//        @Override
//        public void onClick(View v) {
//            ChatListFragmentDirections.ActionChatListFragmentToNavigationChats directions =
//                    ChatListFragmentDirections.actionChatListFragmentToNavigationChats(
//                            mChatrooms.get(getBindingAdapterPosition()).getChatId());
//            Navigation.findNavController(v).navigate(directions);
//        }

        /**
         * Set the preview for the chatroom
         * @param chatroom stores information about the chatroom
         */
        void setPreview(final Chatroom chatroom) {
            final Resources res = mView.getContext().getResources();
            final MaterialCardView card = binding.cardRoot;



            binding.textChatroom.setText(chatroom.getChatId() +
                    ": " + chatroom.toString());
            ViewGroup.MarginLayoutParams layoutParams =
                    (ViewGroup.MarginLayoutParams) card.getLayoutParams();




            card.requestLayout();

        }


    }
}

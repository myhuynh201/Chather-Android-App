package edu.uw.chather.ui.chat;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.List;

import edu.uw.chather.R;
import edu.uw.chather.databinding.FragmentChatroomBinding;

/**
 * ChatListRecyclerViewAdapter creates recycler views for chatrooms
 * @author Alec Mac
 */
public class ChatListRecyclerViewAdapter extends RecyclerView.Adapter<ChatListRecyclerViewAdapter.ChatroomViewHolder> {

    /**
     * list of chatrooms
     */
    private final List<Chatroom> mChatrooms;

    /**
     * constructor, initializing list of chatrooms
     * @param chatrooms list of chatrooms
     */
    public ChatListRecyclerViewAdapter(List<Chatroom> chatrooms) {
        this.mChatrooms = chatrooms;
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
        private FragmentChatroomBinding binding;

        /**
         * constructs a chatroom view holder
         * @param itemView the view created for this viewholder
         */
        public ChatroomViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            binding = FragmentChatroomBinding.bind(itemView);
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

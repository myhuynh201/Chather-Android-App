package edu.uw.chather.ui.chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.uw.chather.R;

public class ChatListRecyclerViewAdapter extends RecyclerView.Adapter<ChatListRecyclerViewAdapter.ChatroomViewHolder> {
    private final List<Chatroom> mChatrooms;

    public ChatListRecyclerViewAdapter(List<Chatroom> chatrooms) {
        this.mChatrooms = chatrooms;
    }

    @NonNull
    @Override
    public ChatroomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChatroomViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.fragment_chat_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChatroomViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ChatroomViewHolder extends RecyclerView.ViewHolder{
        public ChatroomViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

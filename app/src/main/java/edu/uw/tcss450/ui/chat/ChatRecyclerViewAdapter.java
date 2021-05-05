package edu.uw.tcss450.ui.chat;


import android.graphics.drawable.Icon;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import edu.uw.tcss450.R;
import edu.uw.tcss450.databinding.FragmentChatCardBinding;

public class ChatRecyclerViewAdapter extends RecyclerView.Adapter<ChatRecyclerViewAdapter.ChatViewHolder> {

    //Store all the expanded state for each List item, true -> expanded, false -> not
    private final Map<ChatRoom, Boolean> mExpandedFlags;
    //Store all of the chat to present
    private final List<ChatRoom> mChatRooms;

    public ChatRecyclerViewAdapter(List<ChatRoom> items) {
        this.mChatRooms = items;
        mExpandedFlags = mChatRooms.stream()
                .collect(Collectors.toMap(Function.identity(), blog -> false));
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChatViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_chat_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        holder.setChatRoom(mChatRooms.get(position));
    }

    @Override
    public int getItemCount() {
        return mChatRooms.size();
    }

    /**
     * Objects from this class represent an Individual row View from the List
     * of rows in the Blog Recycler View.
     */
    public class ChatViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public FragmentChatCardBinding binding;
        private ChatRoom mChatRoom;

        public ChatViewHolder(View view) {
            super(view);
            mView = view;
            binding = FragmentChatCardBinding.bind(view);
        }



        void setChatRoom(final ChatRoom chatRoom) {
            mChatRoom = chatRoom;
            binding.layoutInner.setOnClickListener(view -> {
                Navigation.findNavController(mView).navigate(
                        ChatListFragmentDirections
                                .actionNavigationChatsToChatRoomFragment(chatRoom));
            });
            binding.chatHeader.setText(chatRoom.getTitle());
            binding.chatDateTime.setText(chatRoom.getPubDate());
            //Use methods in the HTML class to format the HTML found in the text
            final String preview = Html.fromHtml(
                    chatRoom.getTeaser(),
                    Html.FROM_HTML_MODE_COMPACT)
                    .toString().substring(0, 40) //just a preview of the teaser
                    + "...";
            binding.chatPreview.setText(preview);

        }
    }
}

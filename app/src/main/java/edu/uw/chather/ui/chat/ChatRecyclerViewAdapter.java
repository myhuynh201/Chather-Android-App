package edu.uw.chather.ui.chat;


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

import edu.uw.chather.R;
import edu.uw.chather.databinding.FragmentChatCardBinding;
import edu.uw.chather.ui.chat.ChatListFragmentDirections;

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
        return new ChatViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_chat_card,parent,false));
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
            binding.buittonMore.setOnClickListener(this::handleMoreOrLess);
        }
        /**
         * When the button is clicked in the more state, expand the card to display
         * the chat preview and switch the icon to the less state. When the button
         * is clicked in the less state, shrink the card and switch the icon to the
         * more state.
         * @param button the button that was clicked
         */
        private void handleMoreOrLess(final View button) {
            mExpandedFlags.put(mChatRoom, !mExpandedFlags.get((mChatRoom)));
            displayPreview();
        }
        /**
         * Helper used to determine if the preview should be displayed or not.
         */
        private void displayPreview() {
            if (mExpandedFlags.get(mChatRoom)) {
                // if (binding.textPreview.getVisibility() == View.GONE) {
                binding.textPreview.setVisibility(View.VISIBLE);
                binding.buittonMore.setImageIcon(
                        Icon.createWithResource(
                                mView.getContext(),
                                R.drawable.ic_less_grey_24dp));

            } else {
                binding.textPreview.setVisibility(View.GONE);
                binding.buittonMore.setImageIcon(
                        Icon.createWithResource(
                                mView.getContext(),
                                R.drawable.ic_more_grey_24dp));
            }
        }
        void setChatRoom(final ChatRoom chatRoom) {
            mChatRoom = chatRoom;
            binding.buttonFullPost.setOnClickListener(view -> {
                Navigation.findNavController(mView).navigate(
                        ChatListFragmentDirections
                                .actionNavigationChatsToChatRoomFragment(chatRoom));
            });
            binding.textTitle.setText(chatRoom.getTitle());
            binding.textPubdate.setText(chatRoom.getPubDate());
            //Use methods in the HTML class to format the HTML found in the text
            final String preview = Html.fromHtml(
                    chatRoom.getTeaser(),
                    Html.FROM_HTML_MODE_COMPACT)
                    .toString().substring(0,100) //just a preview of the teaser
                    + "...";
            binding.textPreview.setText(preview);
            displayPreview();
        }
    }
}

package edu.uw.tcss450.ui.chat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uw.tcss450.R;
import edu.uw.tcss450.databinding.FragmentChatRoomBinding;


/**
 * This Fragment is used for representing a ChatRoom
 *
 * @author alecmac
 */
public class ChatRoomFragment extends Fragment {

    /**
     * When view is created, initialize content
     * @param view current view
     * @param savedInstanceState current state of instance
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ChatRoomFragmentArgs args = ChatRoomFragmentArgs.fromBundle(getArguments());
        FragmentChatRoomBinding binding = FragmentChatRoomBinding.bind(getView());
        binding.chatHeader.setText(args.getChatRoom().getPubDate());
        binding.chatPreview.setText(args.getChatRoom().getTitle());
        final String preview = Html.fromHtml(
                args.getChatRoom().getTeaser(),
                Html.FROM_HTML_MODE_COMPACT)
                .toString();
        binding.textPreview.setText(preview);
        //Note we are using an Intent here to start the default system web browser
        binding.buttonSendChat.setOnClickListener(button ->
                Log.d("CHAT MESSAGE", binding.chatMessageInput.getText().toString()));
    }

    /**
     * When View is created, inflate the view
     * @param inflater used to inflate view
     * @param container container in which view resides
     * @param savedInstanceState current state of instance
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat_room, container, false);
    }
}
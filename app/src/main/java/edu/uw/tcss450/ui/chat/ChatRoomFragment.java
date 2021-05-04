package edu.uw.tcss450.ui.chat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uw.tcss450.R;
import edu.uw.tcss450.databinding.FragmentChatRoomBinding;


public class ChatRoomFragment extends Fragment {

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ChatRoomFragmentArgs args = ChatRoomFragmentArgs.fromBundle(getArguments());
        FragmentChatRoomBinding binding = FragmentChatRoomBinding.bind(getView());
        binding.textPubdate.setText(args.getChatRoom().getPubDate());
        binding.textTitle.setText(args.getChatRoom().getTitle());
        final String preview = Html.fromHtml(
                args.getChatRoom().getTeaser(),
                Html.FROM_HTML_MODE_COMPACT)
                .toString();
        binding.textPreview.setText(preview);
        //Note we are using an Intent here to start the default system web browser
        binding.buttonUrl.setOnClickListener(button ->
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse(args.getChatRoom().getUrl()))));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat_room, container, false);
    }
}
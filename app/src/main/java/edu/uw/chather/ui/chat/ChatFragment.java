package edu.uw.chather.ui.chat;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import edu.uw.chather.R;
import edu.uw.chather.databinding.FragmentChatBinding;
import edu.uw.chather.ui.model.UserInfoViewModel;

/**
 * Chat Fragment represents a fragment of a Chat.
 * @author Alec Mac
 */
public class ChatFragment extends Fragment {

    /**
     * id for chat
     */
    private int mChatId;

    /**
     * view model to store chat information
     */
    private ChatViewModel mChatModel;

    /**
     * view model to store user information
     */
    private UserInfoViewModel mUserModel;

    /**
     * view model to store information about sent chat
     */
    private ChatSendViewModel mSendModel;

    /**
     * required constructores
     */
    public ChatFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        mChatId = getArguments().getInt("chatid");

        getActivity().setTitle(getArguments().getString("names"));
        ViewModelProvider provider = new ViewModelProvider(getActivity());
        mUserModel = provider.get(UserInfoViewModel.class);
        mChatModel = provider.get(ChatViewModel.class);
        mChatModel.getFirstMessages(mChatId, mUserModel.getmJwt());
        mSendModel = provider.get(ChatSendViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try {
            mChatId = Integer.parseInt(getArguments().toString());
        } catch (NumberFormatException e) {
            Log.d("Chat", "ChatID is not valid");
        }
        Log.d("Chat", "ChatID Found: " + mChatId);


        FragmentChatBinding binding = FragmentChatBinding.bind(getView());

        //SetRefreshing shows the internal Swiper view progress bar. Show this until messages load
        binding.swipeContainer.setRefreshing(true);

        final RecyclerView rv = binding.recyclerMessages;
        //Set the Adapter to hold a reference to the list FOR THIS chat ID that the ViewModel
        //holds.
        rv.setAdapter(new ChatRecyclerViewAdapter(
                        mChatModel.getMessageListByChatId(mChatId),
                        mUserModel.getEmail()));


        //When the user scrolls to the top of the RV, the swiper list will "refresh"
        //The user is out of messages, go out to the service and get more
        binding.swipeContainer.setOnRefreshListener(() -> {
            mChatModel.getNextMessages(mChatId, mUserModel.getmJwt());
        });

        mChatModel.addMessageObserver(mChatId, getViewLifecycleOwner(),
                list -> {
                    /*
                     * This solution needs work on the scroll position. As a group,
                     * you will need to come up with some solution to manage the
                     * recyclerview scroll position. You also should consider a
                     * solution for when the keyboard is on the screen.
                     */
                    //inform the RV that the underlying list has (possibly) changed
                    rv.getAdapter().notifyDataSetChanged();
                    rv.scrollToPosition(rv.getAdapter().getItemCount() - 1);
                    binding.swipeContainer.setRefreshing(false);
                });

        // Send button was clicked. Send the message via the SendViewModel
        binding.buttonSend.setOnClickListener(button -> {
            mSendModel.sendMessage(mChatId,
                    mUserModel.getmJwt(),
                    binding.editMessage.getText().toString());
        });
        // when we get the response back from the server, clear the edittext
        mSendModel.addResponseObserver(getViewLifecycleOwner(), response -> binding.editMessage.setText(""));
    }
}

package edu.uw.chather.ui.chat;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uw.chather.R;
import edu.uw.chather.ui.model.UserInfoViewModel;

public class ChatNewRoomFragment extends Fragment {

    private ChatNewRoomViewModel mNewChatModel;


    /**
     * view model to store user info
     */
    private UserInfoViewModel mUserModel;


    public static ChatNewRoomFragment newInstance() {
        return new ChatNewRoomFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewModelProvider provider = new ViewModelProvider(getActivity());
        mNewChatModel = provider.get(ChatNewRoomViewModel.class);
        mUserModel = provider.get(UserInfoViewModel.class);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.chat_new_room_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mNewChatModel = new ViewModelProvider(this).get(ChatNewRoomViewModel.class);
    }

}
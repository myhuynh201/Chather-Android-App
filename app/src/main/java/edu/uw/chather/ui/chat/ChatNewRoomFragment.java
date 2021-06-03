package edu.uw.chather.ui.chat;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import edu.uw.chather.R;
import edu.uw.chather.databinding.ChatNewRoomFragmentBinding;
import edu.uw.chather.ui.model.UserInfoViewModel;

import static android.content.ContentValues.TAG;

public class ChatNewRoomFragment extends Fragment {

    private ChatNewRoomViewModel mNewChatModel;

    private List<String> mMembers;
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
        mMembers = new ArrayList<>();
        mMembers.add(mUserModel.getUsername());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.chat_new_room_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ChatNewRoomFragmentBinding binding = ChatNewRoomFragmentBinding.bind(getView());

        updateDisplayMembers(binding);
        binding.buttonAddUserChat.setOnClickListener(button -> {
            String username = binding.editAddUsername.getText().toString();
            mMembers.add(username);
            updateDisplayMembers(binding);
            binding.editAddUsername.setText("");
        });

        binding.buttonAddNewChat.setOnClickListener(button -> {
            mNewChatModel.createNewChat(UserInfoViewModel.getmJwt(), mMembers);
            mMembers.clear();
            mMembers.add(mUserModel.getUsername());
            updateDisplayMembers(binding);
        });
        mNewChatModel.addResponseObserver(getViewLifecycleOwner(), response -> {
            Log.d(TAG, "onViewCreated: " + response);
            try {
                int chatid = response.getInt("chatID");
                String memberString = response.getJSONArray("members").toString();
                Log.d("New Chat", chatid + " " + memberString);
                Navigation.findNavController(view)
                        .navigate(ChatNewRoomFragmentDirections.actionChatNewRoomFragmentToChatFragment(
                                chatid, memberString));
            } catch (JSONException e) {
                e.printStackTrace();
                mMembers.clear();
                binding.textMemberName.setText("");
            }
        });


    }

    private void updateDisplayMembers(ChatNewRoomFragmentBinding binding) {
        binding.textMemberName.setText("");
        for (String member : mMembers) {
            binding.textMemberName.append(member + "\r\n");
        }
    }

}
package edu.uw.chather.ui.chat;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import edu.uw.chather.R;
import edu.uw.chather.databinding.FragmentChatListBinding;
import edu.uw.chather.ui.model.UserInfoViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatListFragment extends Fragment {

    private ChatListViewModel mChatListModel;
    private UserInfoViewModel mUserModel;


    public ChatListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewModelProvider provider = new ViewModelProvider(getActivity());
        mUserModel = provider.get(UserInfoViewModel.class);
        mChatListModel = provider.get(ChatListViewModel.class);
        mChatListModel.getChatrooms(UserInfoViewModel.getmJwt());
        Log.d("CHATLIST MODEL", "onCreate: " + mChatListModel);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String jwt = UserInfoViewModel.getmJwt();
        FragmentChatListBinding binding = FragmentChatListBinding.bind(getView());

        binding.swipeContainer.setRefreshing(true);




        final RecyclerView rv = binding.recyclerChatroom;
        Log.d("JWT HERE", "onViewCreated: " + jwt);
        rv.setAdapter(new ChatListRecyclerViewAdapter(
                mChatListModel.getChatroomListByMemberId(jwt)
        ));

        binding.swipeContainer.setOnRefreshListener(() -> {
            mChatListModel.getChatroomListByMemberId(jwt);
        });

        mChatListModel.addChatroomObserver(jwt, getViewLifecycleOwner(), list -> {
            rv.getAdapter().notifyDataSetChanged();
//            rv.scrollToPosition(rv.getAdapter().getItemCount() - 1);
            binding.swipeContainer.setRefreshing(false);
        });
    }
}
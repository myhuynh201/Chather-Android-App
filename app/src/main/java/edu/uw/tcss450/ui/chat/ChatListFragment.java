package edu.uw.tcss450.ui.chat;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uw.tcss450.R;
import edu.uw.tcss450.databinding.FragmentChatListBinding;

/**
 * This class is used to represent the list of chatrooms
 *
 * @author alecmac
 */
public class ChatListFragment extends Fragment {
    /**
     * View model for chat list
     */
    private ChatListViewModel mModel;

    /**
     * When ChatListFragment is created, get the view model
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mModel = new ViewModelProvider(getActivity()).get(ChatListViewModel.class);
//        mModel.connectGet();

    }


    /**
     * When view is created, create chats
     * @param inflater inflates this view
     * @param container holds this view
     * @param savedInstanceState represents the saved state
     * @return the view after being populated
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat_list, container, false);
        if (view instanceof RecyclerView) {
            //Try out a grid layout to achieve rows AND columns. Adjust the widths of the
            //cards on display
            //((RecyclerView) view).setLayoutManager(new GridLayoutManager(getContext(), 2));
            //Try out horizontal scrolling. Adjust the widths of the card so that it is
            //obvious that there are more cards in either direction. i.e. don't have the cards
            //span the entire witch of the screen. Also, when considering horizontal scroll
            //on recycler view, ensure that there is other content to fill the screen.
            // ((LinearLayoutManager)((RecyclerView) view).getLayoutManager())
            // .setOrientation(LinearLayoutManager.HORIZONTAL);
            ((RecyclerView) view).setAdapter(
                    new ChatRecyclerViewAdapter(ChatGenerator.getChatList()));
        }
        return view;

//        return inflater.inflate(R.layout.fragment_chat_list,container,false);
    }

//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        super.onViewCreated(view, savedInstanceState);
//        FragmentChatListBinding binding = FragmentChatListBinding.bind(getView());
//
//        mModel.addChatListObserver(getViewLifecycleOwner(), chatRoomList -> {
//            if (!chatRoomList.isEmpty()) {
//                binding.listRoot.setAdapter( new ChatRecyclerViewAdapter(chatRoomList)
//                );
//                binding.layoutWait.setVisibility(View.GONE);
//            }
//        });
//    }
}
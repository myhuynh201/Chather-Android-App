package edu.uw.chather.ui.contact;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONException;

import java.util.ArrayList;

import edu.uw.chather.R;
import edu.uw.chather.ui.chat.ChatNewRoomFragmentDirections;
import edu.uw.chather.ui.chat.ChatNewRoomViewModel;
import edu.uw.chather.ui.model.UserInfoViewModel;

/**
 * A fragment representing a list of Connections.
 */
public class ContactFragment extends Fragment {
    ContactListViewModel mModel;
    ChatNewRoomViewModel mNewMessageModel;

    /**
     * Number of columns
      */
    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private ArrayList<Contact> mContactList;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ContactFragment() {
    }

    /**
     * Creates new instance of connection fragment
     * @param columnCount initializes with count of columns
     * @return returns new Connection Fragment
     */
    public static ContactFragment newInstance(int columnCount) {
        ContactFragment fragment = new ContactFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * When this is created, set column count
     * @param savedInstanceState saved instance state
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mModel = new ViewModelProvider(getActivity()).get(ContactListViewModel.class);
        mNewMessageModel = new ViewModelProvider(getActivity()).get(ChatNewRoomViewModel.class);

        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    /**
     * Handle when view is created
     * @param inflater inflates the view
     * @param container container in which this view resides
     * @param savedInstanceState saved instance state
     * @return the inflated view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_list, container, false);
        return view;

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        mModel.connectGet();
        // Set the adapter
        Context context = view.getContext();
        RecyclerView recyclerView = (RecyclerView) view;
        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(new MyContactRecyclerViewAdapter(mModel, getActivity()));
        mModel.addContactListObserver(getViewLifecycleOwner(), list -> {
            recyclerView.getAdapter().notifyDataSetChanged();
        });
        mNewMessageModel.addResponseObserver(getViewLifecycleOwner(), response -> {
            try {
                    Navigation.findNavController(view).navigate(ContactFragmentDirections
                            .actionNavigationContactToChatFragment(response.getInt("chatID"), response.getJSONArray("members").toString()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }



    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater){
        menu.findItem(R.id.navigation_add).setVisible(true);
        menu.findItem(R.id.navigate_contact_request).setVisible(true);
        super.onCreateOptionsMenu(menu, inflater);
    }


}
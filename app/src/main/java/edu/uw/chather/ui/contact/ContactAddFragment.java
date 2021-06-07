package edu.uw.chather.ui.contact;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import edu.uw.chather.R;
import edu.uw.chather.databinding.FragmentContactSearchListBinding;

/**
 * A simple {@link Fragment} subclass.
 *
 * create an instance of this fragment.
 */
public class ContactAddFragment extends Fragment {

    private FragmentContactSearchListBinding mBinding;

    private ContactSearchRecyclerViewAdapter mSearchRecycler;
    private int mColumnCount = 1;
    private ContactSearchViewModel mSearchView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        ViewModelProvider provider = new ViewModelProvider(getActivity());

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentContactSearchListBinding.inflate(inflater);
        mSearchView = new ViewModelProvider(getActivity()).get(ContactSearchViewModel.class);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        mBinding.searchButton.setOnClickListener(this::searchForContact);
    }

    /**Extracts text from the search bar, sends request to web service, then builds a
     * recycler view from the result.
     *
     * @param view The view with the recycler view
     */
    public void searchForContact(View view){
        String searchString = mBinding.searchBar.getText().toString();
        if(searchString.isEmpty()){
            mBinding.searchBar.setError("Must enter in a username or email.");
        }
        else{
            Context context = view.getContext();
            RecyclerView recyclerView = mBinding.contactSearchDisplay;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            mSearchView.connectGet(searchString);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
            recyclerView.addItemDecoration(dividerItemDecoration);
            recyclerView.setAdapter(new ContactSearchRecyclerViewAdapter(mSearchView));
            mSearchView.addContactListObserver(getViewLifecycleOwner(), list -> {
                recyclerView.getAdapter().notifyDataSetChanged();
            });
        }
    }

}
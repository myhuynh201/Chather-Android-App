package edu.uw.chather.ui;
/**
 * Duy Nguyen
 * TCSS 450
 * Lab 1 Assignment
 */

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import edu.uw.chather.ui.model.UserInfoViewModel;
import edu.uw.chather.R;
import edu.uw.chather.databinding.FragmentSuccessBinding;

/**
 * The final fragment displayed once the register fragment or the sign in fragment advances successfully.
 * This fragment will simple show a simple text hello plus the user's email address.
 */
public class SuccessFragment extends Fragment {

    private FragmentSuccessBinding binding;
    EditText edEmail;              // A text field for user's email address

   // TextView edSuccess;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSuccessBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //SuccessFragmentArgs args = SuccessFragmentArgs.fromBundle(getArguments());
        FragmentSuccessBinding mBinding = FragmentSuccessBinding.bind(getView());
        UserInfoViewModel model = new ViewModelProvider(getActivity()).get(UserInfoViewModel.class);
        mBinding.success.setText("Hello " + model.getEmail().toString());
//        MainActivityArgs args = MainActivityArgs.fromBundle(getArguments());
//        String email = args.getJwt();
//        JWT jwt = new JWT(args.getJwt());
//
//        binding.success.setText("Hello" + args.getJwt());
    }
}
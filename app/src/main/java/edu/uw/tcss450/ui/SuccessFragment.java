package edu.uw.tcss450.ui;
/**
 * Duy Nguyen
 * TCSS 450
 * Lab 1 Assignment
 */

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import edu.uw.tcss450.R;
import edu.uw.tcss450.databinding.FragmentSuccessBinding;
import edu.uw.tcss450.ui.model.UserInfoViewModel;

/**
 * The final fragment displayed once the register fragment or the sign in fragment advances successfully.
 * This fragment will simple show a simple text hello plus the user's email address.
 */
public class SuccessFragment extends Fragment {

    private FragmentSuccessBinding binding;

    EditText edEmail;              // A text field for user's email address
    Switch colorModeSwitch;
    // TextView edSuccess;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSuccessBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //SuccessFragmentArgs args = SuccessFragmentArgs.fromBundle(getArguments());
        FragmentSuccessBinding mBinding = FragmentSuccessBinding.bind(getView());
        UserInfoViewModel model = new ViewModelProvider(getActivity()).get(UserInfoViewModel.class);
        mBinding.success.setText("Hello " + model.getEmail().toString());
        boolean isClick = false;
        mBinding.buttonDarkMode.setOnClickListener(button -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES));
        mBinding.buttonLightMode.setOnClickListener(button -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO));
//        mBinding.switchMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            if(isChecked == true) {
//                Toast.makeText(getContext(), "Dark Mode On", Toast.LENGTH_LONG);
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//            } else {
//                Toast.makeText(getContext(), "Dark Mode Off", Toast.LENGTH_LONG);
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//            }
//        });

    }
}
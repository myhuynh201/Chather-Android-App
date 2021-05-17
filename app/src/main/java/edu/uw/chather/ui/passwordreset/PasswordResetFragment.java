package edu.uw.chather.ui.passwordreset;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import edu.uw.chather.MainActivity;
import edu.uw.chather.R;
import edu.uw.chather.databinding.FragmentPasswordResetBinding;
import edu.uw.chather.databinding.FragmentSignInBinding;
import edu.uw.chather.ui.register.RegisterFragmentDirections;
import edu.uw.chather.ui.register.RegisterViewModel;
import edu.uw.chather.ui.signin.SignInFragmentArgs;
import edu.uw.chather.ui.signin.SignInFragmentDirections;
import edu.uw.chather.ui.signin.SignInViewModel;


public class PasswordResetFragment extends Fragment {


    private FragmentPasswordResetBinding binding;

    /*
     A view model to allow changes in the sign in fragment.
    */
    private PasswordResetViewModel mPasswordResetModel;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPasswordResetModel = new ViewModelProvider(getActivity())
                .get(PasswordResetViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPasswordResetBinding.inflate(inflater);
        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPasswordResetModel.addResponseObserver(getViewLifecycleOwner(),
                this::observeResponse);
        binding.buttonPasswordReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Convert to string and trim to remove white spaces.
                String userEmail = binding.etCurrentPass.getText().toString().trim();

                // Check if text field is empty.
                if(userEmail.equals("")) {
                    Toast.makeText(getActivity(),"Please enter your registered email", Toast.LENGTH_SHORT).show();
                } else {
                    // send password reset email, and add on complete listener.
                    // if the task is successful and complete, then
                    Toast.makeText(getActivity(),"Please check your email to follow reset instruction.", Toast.LENGTH_SHORT).show();
                    mPasswordResetModel.connect(userEmail);
                   // navigateToLogin();
                }
            }
        });

    }

    /**
     * An observer on the HTTP Response from the web server. This observer should be
     * attached to SignInViewModel.
     *
     * @param response the Response from the server
     */
    private void observeResponse(final JSONObject response) {
        if (response.length() > 0) {
            if (response.has("code")) {
                try {
                    binding.etCurrentPass.setError(
                            "Error Authenticating: " +
                                    response.getJSONObject("data").getString("message"));
                } catch (JSONException e) {
                    Log.e("JSON Parse Error", e.getMessage());
                }
            } else {
                navigateToLogin();
            }
        } else {
            Log.d("JSON Response", "No Response");
        }
    }

    /**
     * Private helper method for navigating to login if user registration is complete.
     */
    private void navigateToLogin() {
        RegisterFragmentDirections.ActionRegisterFragmentToSignInFragment directions =
                RegisterFragmentDirections.actionRegisterFragmentToSignInFragment();

        directions.setEmail(binding.etCurrentPass.getText().toString());
        Navigation.findNavController(getView()).navigate(directions);

    }
}
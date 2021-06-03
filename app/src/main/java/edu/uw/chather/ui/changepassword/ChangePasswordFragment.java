package edu.uw.chather.ui.changepassword;

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
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import edu.uw.chather.databinding.FragmentChangePasswordBinding;
import edu.uw.chather.ui.model.UserInfoViewModel;
import edu.uw.chather.utils.PasswordValidator;

import static edu.uw.chather.utils.PasswordValidator.checkClientPredicate;
import static edu.uw.chather.utils.PasswordValidator.checkExcludeWhiteSpace;
import static edu.uw.chather.utils.PasswordValidator.checkPwdDigit;
import static edu.uw.chather.utils.PasswordValidator.checkPwdLength;
import static edu.uw.chather.utils.PasswordValidator.checkPwdLowerCase;
import static edu.uw.chather.utils.PasswordValidator.checkPwdSpecialChar;
import static edu.uw.chather.utils.PasswordValidator.checkPwdUpperCase;

/**
 * A simple {@link Fragment} subclass.
 * @author Charles Bryan, Duy Nguyen, Demarco Best, Alec Mac, Alejandro Olono, My Duyen Huynh
 */
public class ChangePasswordFragment extends Fragment {

    /**
     * A binding for the change password fragment.
     */
    private FragmentChangePasswordBinding binding;

    /**
     * The change password view model.
     */
    private ChangePasswordViewModel mChangePasswordViewModel;

    /**
     * Method to validate the old password.
     */
    private final PasswordValidator mOldPasswordValidator = checkPwdLength(7)
            .and(checkPwdSpecialChar())
            .and(checkExcludeWhiteSpace())
            .and(checkPwdDigit())
            .and(checkPwdLowerCase().or(checkPwdUpperCase()));

    /**
     * Method to validate the new password.
     */
    private final PasswordValidator mNewPassWordValidator =
            checkClientPredicate(pwd -> pwd.equals(binding.etNewPass.getText().toString()))
                    .and(checkPwdLength(7))
                    .and(checkPwdSpecialChar())
                    .and(checkExcludeWhiteSpace())
                    .and(checkPwdDigit())
                    .and(checkPwdLowerCase().or(checkPwdUpperCase()));

    /**
     * The user's email.
     */
    private String mEmail;

    /**
     * An empty constructor.
     */
    public ChangePasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mChangePasswordViewModel = new ViewModelProvider(getActivity())
                .get(ChangePasswordViewModel.class);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.buttonPasswordReset.setOnClickListener(this::attemptChangePassword);
        mChangePasswordViewModel.addResponseObserver(getViewLifecycleOwner(),
                this::observeResponse);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentChangePasswordBinding.inflate(inflater, container,false);
        UserInfoViewModel model = new ViewModelProvider(getActivity()).get(UserInfoViewModel.class);
        mEmail = model.getEmail();
        Log.d("EMAIL", mEmail);
        return binding.getRoot();
    }

    /**
     * Private helper method for validation in register.
     * @param button
     */
    private void attemptChangePassword(final View button) {
        //Toast.makeText(getContext(), "Password Changed Successfully", Toast.LENGTH_LONG);
        validateOldPassword();
    }

    /**
     * Private helper method for checking current password.
     */
    private void validateOldPassword() {
        mOldPasswordValidator.processResult(
                mOldPasswordValidator.apply(binding.etCurrentPass.getText().toString()),
                this::validateNewPassword,
                result -> binding.etCurrentPass.setError("Please enter your current password."));
    }

    /**
     * Private helper method for password validation.
     */
    private void validateNewPassword() {
        mNewPassWordValidator.processResult(
                mNewPassWordValidator.apply(binding.etNewPass.getText().toString()),
                this::verifyAuthWithServer,
                result -> binding.etCurrentPass.setError("Please enter a valid password."));
    }

    /**
     * Private helper method for verification of the register with the server.
     */
    private void verifyAuthWithServer() {

        mChangePasswordViewModel.connect(
                mEmail,
                binding.etCurrentPass.getText().toString(),
                binding.etNewPass.getText().toString(),
                binding.etConfirmNewPass.getText().toString());
        //This is an Asynchronous call. No statements after should rely on the
        //result of connect().
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
                System.out.print("error");
                Log.e("Error", "Email Authentication Error");
//                    mUserViewModel.getEmail().Email.setError(
//                            "Error Authenticating: " +
//                                    response.getJSONObject("data").getString("message"));
                try {
                    binding.etNewPass.setError("Error Authenticating: " + response.getJSONObject("data").getString("message"));
                } catch (JSONException e) {
                    Log.e("JSON Parse Error", e.getMessage());
                }
            } else {
                navigateToHome();
            }
        } else {
            Log.d("JSON Response", "No Response");
        }
    }

    /**
     * Private helper method for navigation to home once password is successfully changed.
     */
    private void navigateToHome() {
        Navigation.findNavController(getView()).navigate(ChangePasswordFragmentDirections.actionChangePasswordFragmentToSuccess());

    }




}
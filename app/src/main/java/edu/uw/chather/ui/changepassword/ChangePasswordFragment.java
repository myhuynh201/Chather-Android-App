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

import edu.uw.chather.R;
import edu.uw.chather.databinding.FragmentChangePasswordBinding;
import edu.uw.chather.databinding.FragmentRegisterBinding;
import edu.uw.chather.ui.SuccessFragmentDirections;
import edu.uw.chather.ui.model.UserInfoViewModel;
import edu.uw.chather.ui.register.RegisterViewModel;
import edu.uw.chather.utils.PasswordValidator;

import static edu.uw.chather.utils.PasswordValidator.checkClientPredicate;
import static edu.uw.chather.utils.PasswordValidator.checkExcludeWhiteSpace;
import static edu.uw.chather.utils.PasswordValidator.checkPwdDigit;
import static edu.uw.chather.utils.PasswordValidator.checkPwdLength;
import static edu.uw.chather.utils.PasswordValidator.checkPwdLowerCase;
import static edu.uw.chather.utils.PasswordValidator.checkPwdSpecialChar;
import static edu.uw.chather.utils.PasswordValidator.checkPwdUpperCase;


public class ChangePasswordFragment extends Fragment {

    private FragmentChangePasswordBinding binding;

 //   private FragmentRegisterBinding bindingUsername;

    private UserInfoViewModel mUserViewModel;

    private ChangePasswordViewModel mChangePasswordViewModel;
    /*
        Password validator for the password field in register.
     */
    private PasswordValidator mPassWordValidator =
            checkClientPredicate(pwd -> pwd.equals(binding.etNewPass.toString()))
                    .and(checkPwdLength(7))
                    .and(checkPwdSpecialChar())
                    .and(checkExcludeWhiteSpace())
                    .and(checkPwdDigit())
                    .and(checkPwdLowerCase().or(checkPwdUpperCase()));

    /*
        Password validator for the email field in register.
    */
    private PasswordValidator mEmailValidator = checkPwdLength(2)
            .and(checkExcludeWhiteSpace())
            .and(checkPwdSpecialChar("@"));

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

        mUserViewModel = new ViewModelProvider(getActivity())
                .get(UserInfoViewModel.class);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.buttonPasswordChange.setOnClickListener(this::attemptChangePassword);
        mChangePasswordViewModel.addResponseObserver(getViewLifecycleOwner(),
                this::observeResponse);

        UserInfoViewModel model = new ViewModelProvider(getActivity()).get(UserInfoViewModel.class);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentChangePasswordBinding.inflate(inflater);

        return binding.getRoot();
    }

    /**
     * Private helper method for validation in register.
     * @param button
     */
    private void attemptChangePassword(final View button) {
        validateEmail();
        Toast.makeText(getContext(), "Password Changed Successfully", Toast.LENGTH_LONG);

    }

    /**
     * Private helper method for adding email address in the email field.
     */
    private void validateEmail() {

        mEmailValidator.processResult(
                mEmailValidator.apply(mUserViewModel.getEmail().trim()),
                this::validatePasswordsMatch,
                result -> System.out.println("Test"));
//
//        mEmailValidator.processResult(
//                mEmailValidator.apply(bindingUsername.Email.getText().toString().trim()),
//                this::validatePasswordsMatch,
//                result -> bindingUsername.Email.setError("Please enter a valid Email address."));
    }

    /**
     * Private helper method to check if the password matches the retyped password.
     */
    private void validatePasswordsMatch() {
        PasswordValidator matchValidator =
                checkClientPredicate(
                        pwd -> pwd.equals(binding.etConfirmNewPass.getText().toString().trim()));

        mEmailValidator.processResult(
                matchValidator.apply(binding.etNewPass.getText().toString().trim()),
                this::validatePassword,
                result -> binding.etNewPass.setError("Passwords must match."));
    }

    /**
     * Private helper method for password validation.
     */
    private void validatePassword() {
        mPassWordValidator.processResult(
                mPassWordValidator.apply(binding.etCurrentPass.getText().toString()),
                this::verifyAuthWithServer,
                result -> binding.etCurrentPass.setError("Please enter your current password."));
    }

    /**
     * Private helper method for verification of the register with the server.
     */
    private void verifyAuthWithServer() {

        mChangePasswordViewModel.connect(
                mUserViewModel.getEmail(),
                binding.etCurrentPass.getText().toString(),
                binding.etNewPass.getText().toString(),
                binding.etConfirmNewPass.getText().toString());
            //This is an Asynchronous call. No statements after should rely on the
            //result of connect().
        }

    /**
     * Private helper method for navigating to login if user registration is complete.
     */
    private void navigateToSuccess() {

        Navigation.findNavController(getView()).navigate(
                ChangePasswordFragmentDirections.actionChangePasswordFragmentToSuccess());


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
            } else {
                navigateToSuccess();
            }
        } else {
            Log.d("JSON Response", "No Response");
        }
    }

}
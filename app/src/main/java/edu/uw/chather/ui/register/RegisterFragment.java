package edu.uw.chather.ui.register;

/**
 * Duy Nguyen
 * TCSS 450
 * Lab 1 Assignment
 */

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

import org.json.JSONException;
import org.json.JSONObject;

import edu.uw.chather.utils.PasswordValidator;
import edu.uw.chather.R;
import edu.uw.chather.databinding.FragmentRegisterBinding;
import edu.uw.chather.ui.register.RegisterFragmentDirections;

///**
// * The register fragment class allows the user to register their login info.
// * This class contains 5 text fields, First name, Last name, Email Address, Password and Confirmation
// * of password.
// */
//public class RegisterFragment extends Fragment {
//
//
//     Button register;               // A button for register.
//     EditText edFirstName;          // A text field for user's first name
//     EditText edLastName;           // A text field for user's last name
//     EditText edEmail;              // A text field for user's email address
//     EditText edPassword;           // A text field for user's password
//     EditText edConfirmPassword;    // A text field to confirm the user's password
//
//     private FragmentRegisterBinding binding;
//     private RegisterViewModel mRegisterModel;
//
//    private PasswordValidator mNameValidator = checkPwdLength(1);
//
//    private PasswordValidator mEmailValidator = checkPwdLength(2)
//            .and(checkExcludeWhiteSpace())
//            .and(checkPwdSpecialChar("@"));
//
//    private PasswordValidator mPassWordValidator =
//            checkClientPredicate(pwd -> pwd.equals(binding.editPassword2.getText().toString()))
//                    .and(checkPwdLength(7))
//                    .and(checkPwdSpecialChar())
//                    .and(checkExcludeWhiteSpace())
//                    .and(checkPwdDigit())
//                    .and(checkPwdLowerCase().or(checkPwdUpperCase()));
//     //private PasswordValidator mNameValidator = checkPwdLength(1);
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        binding = FragmentRegisterBinding.inflate(inflater, container, false);
//
//        edFirstName = binding.FirstName;
//        edLastName = binding.LastName;
//        edEmail = binding.Email;
//        edPassword = binding.Password;
//        edConfirmPassword = binding.RetypePassword;
//        register = binding.Register;
//
//        register.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                checkValidRegister();
//            }
//        });
//
//        new OnBackPressedCallback(true) {
//            @Override
//            public void handleOnBackPressed() {
//                Navigation.findNavController(getView()).navigate(RegisterFragmentDirections.actionRegisterFragmentToSignInFragment());
//            }
//        };
//
//        return binding.getRoot();
//    }
//
//    /**
//     * A private helper method to check all the requirements that the user must complete before
//     * creating a sign in account.
//     */
//    private void checkValidRegister() {
//        String firstName = edFirstName.getText().toString();
//        String lastName = edLastName.getText().toString();
//        String checkEmail = edEmail.getText().toString();
//        String checkPassword = edPassword.getText().toString();
//        String confirmPass = edConfirmPassword.getText().toString();
//
//        if (firstName.isEmpty() || firstName.equals(lastName)) {
//            edFirstName.setError("This field cannot be empty and must not be the same as the last name");
//        } else if (lastName.isEmpty() || lastName.equals(firstName)) {
//            edLastName.setError("This field cannot be empty and must not be the same as the first name");
//        } else if (checkEmail.isEmpty() || !checkEmail.contains("@")) {
//            edEmail.setError("Please enter an appropriate email address");
//        } else if (checkPassword.length() < 6 || !checkPassword.equals(confirmPass)) {
//            edPassword.setError("Your password must match and must be at least 6 characters in length");
//        } else {
//           // RegisterFragmentDirections.ActionRegisterFragmentToSuccessFragment directions = RegisterFragmentDirections.actionRegisterFragmentToSuccessFragment(checkEmail,"");
//           // Navigation.findNavController(getView()).navigate(directions);
//            Navigation.findNavController(getView()).navigate(
//                    RegisterFragmentDirections
//                            .actionRegisterFragmentToMainActivity(
//                                    generateJwt(edEmail.getText().toString())  // if doesnt work, use edEmail.getText().toString
//                            ));
//
//            getActivity().finish();;
//        }
//    }
//    private String generateJwt(final String email) {
//        String token;
//        try {
//            Algorithm algorithm = Algorithm.HMAC256("secret key don't use a string literal in " +
//                    "production code!!!");
//            token = JWT.create()
//                    .withIssuer("auth0")
//                    .withClaim("email", email)
//                    .sign(algorithm);
//        } catch (JWTCreationException exception){
//            throw new RuntimeException("JWT Failed to Create.");
//        }
//        return token;
//    }
//
//    private void attemptRegister(final View button) {
//        validateFirst();
//    }
//
//    private void validateFirst() {
//        mNameValidator.processResult(
//                mNameValidator.apply(binding.editFirst.getText().toString().trim()),
//                this::validateLast,
//                result -> binding.editFirst.setError("Please enter a first name."));
//    }
//
//    private void validateLast() {
//        mNameValidator.processResult(
//                mNameValidator.apply(binding.editLast.getText().toString().trim()),
//                this::validateEmail,
//                result -> binding.editLast.setError("Please enter a last name."));
//    }
//
//    private void validateEmail() {
//        mEmailValidator.processResult(
//                mEmailValidator.apply(binding.editEmail.getText().toString().trim()),
//                this::validatePasswordsMatch,
//                result -> binding.editEmail.setError("Please enter a valid Email address."));
//    }
//
//    private void validatePasswordsMatch() {
//        PasswordValidator matchValidator =
//                checkClientPredicate(
//                        pwd -> pwd.equals(binding.editPassword2.getText().toString().trim()));
//
//        mEmailValidator.processResult(
//                matchValidator.apply(binding.editPassword1.getText().toString().trim()),
//                this::validatePassword,
//                result -> binding.editPassword1.setError("Passwords must match."));
//    }
//
//    private void validatePassword() {
//        mPassWordValidator.processResult(
//                mPassWordValidator.apply(binding.editPassword1.getText().toString()),
//                this::verifyAuthWithServer,
//                result -> binding.editPassword1.setError("Please enter a valid Password."));
//    }
//
//    private void verifyAuthWithServer() {
//        mRegisterModel.connect(
//                binding.editFirst.getText().toString(),
//                binding.editLast.getText().toString(),
//                binding.editEmail.getText().toString(), binding.editPassword1.getText().toString());
//        //This is an Asynchronous call. No statements after should rely on the
//        //result of connect().
//    }
//
//    private void verifyAuthWithServer() {
//        mRegisterModel.connect(
//                binding.FirstName.getText().toString(),
//                binding.LastName.getText().toString(),
//                binding.Email.getText().toString(), binding.Password.getText().toString());
//        //This is an Asynchronous call. No statements after should rely on the
//        //result of connect().
//    }
//
//    private void navigateToLogin() {
//        RegisterFragmentDirections.directions =
//                RegisterFragmentDirections.actionRegisterFragmentToSignInFragment();
//
//        directions.setEmail(binding.Email.getText().toString());
//        directions.setPassword(binding.Password.getText().toString());
//
//        Navigation.findNavController(getView()).navigate(directions);
//
//    }
//
//    private void observeResponse(final JSONObject response) {
//        if (response.length() > 0) {
//            if (response.has("code")) {
//                try {
//                    binding.Email.setError(
//                            "Error Authenticating: " +
//                                    response.getJSONObject("data").getString("message"));
//                } catch (JSONException e) {
//                    Log.e("JSON Parse Error", e.getMessage());
//                }
//            } else {
//                navigateToLogin();
//            }
//        } else {
//            Log.d("JSON Response", "No Response");
//        }
//    }
//}


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {

    private FragmentRegisterBinding binding;

    private RegisterViewModel mRegisterModel;

    private PasswordValidator mNameValidator = PasswordValidator.checkPwdLength(1);

    private PasswordValidator mEmailValidator = PasswordValidator.checkPwdLength(2)
            .and(PasswordValidator.checkExcludeWhiteSpace())
            .and(PasswordValidator.checkPwdSpecialChar("@"));

    private PasswordValidator mPassWordValidator =
            PasswordValidator.checkClientPredicate(pwd -> pwd.equals(binding.Password.getText().toString()))
                    .and(PasswordValidator.checkPwdLength(7))
                    .and(PasswordValidator.checkPwdSpecialChar())
                    .and(PasswordValidator.checkExcludeWhiteSpace())
                    .and(PasswordValidator.checkPwdDigit())
                    .and(PasswordValidator.checkPwdLowerCase().or(PasswordValidator.checkPwdUpperCase()));

    public RegisterFragment() {
        // Required empty public constructor
    }

        private String generateJwt(final String email) {
        String token;
        try {
            Algorithm algorithm = Algorithm.HMAC256("secret key don't use a string literal in " +
                    "production code!!!");
            token = JWT.create()
                    .withIssuer("auth0")
                    .withClaim("email", email)
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new RuntimeException("JWT Failed to Create.");
        }
        return token;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRegisterModel = new ViewModelProvider(getActivity())
                .get(RegisterViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.Register.setOnClickListener(this::attemptRegister);
        mRegisterModel.addResponseObserver(getViewLifecycleOwner(),
                this::observeResponse);
    }

    private void attemptRegister(final View button) {
        validateFirst();
    }

    private void validateFirst() {
        mNameValidator.processResult(
                mNameValidator.apply(binding.FirstName.getText().toString().trim()),
                this::validateLast,
                result -> binding.FirstName.setError("Please enter a first name."));
    }

    private void validateLast() {
        mNameValidator.processResult(
                mNameValidator.apply(binding.LastName.getText().toString().trim()),
                this::validateEmail,
                result -> binding.LastName.setError("Please enter a last name."));
    }

    private void validateEmail() {
        mEmailValidator.processResult(
                mEmailValidator.apply(binding.Email.getText().toString().trim()),
                this::validatePasswordsMatch,
                result -> binding.Email.setError("Please enter a valid Email address."));
    }

    private void validatePasswordsMatch() {
        PasswordValidator matchValidator =
                PasswordValidator.checkClientPredicate(
                        pwd -> pwd.equals(binding.RetypePassword.getText().toString().trim()));

        mEmailValidator.processResult(
                matchValidator.apply(binding.Password.getText().toString().trim()),
                this::validatePassword,
                result -> binding.Password.setError("Passwords must match."));
    }

    private void validatePassword() {
        mPassWordValidator.processResult(
                mPassWordValidator.apply(binding.Password.getText().toString()),
                this::verifyAuthWithServer,
                result -> binding.Password.setError("Please enter a valid Password."));
    }

    private void verifyAuthWithServer() {
        mRegisterModel.connect(
                binding.FirstName.getText().toString(),
                binding.LastName.getText().toString(),
                binding.Email.getText().toString(), binding.Password.getText().toString());
        //This is an Asynchronous call. No statements after should rely on the
        //result of connect().
    }

    private void navigateToLogin() {
        RegisterFragmentDirections.ActionRegisterFragmentToSignInFragment directions =
                RegisterFragmentDirections.actionRegisterFragmentToSignInFragment();

        directions.setEmail(binding.Email.getText().toString());
        directions.setPassword(binding.Password.getText().toString());

        Navigation.findNavController(getView()).navigate(directions);

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
                    binding.Email.setError(
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
}

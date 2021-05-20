package edu.uw.chather.ui.register;
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


import static edu.uw.chather.utils.PasswordValidator.*;
import static edu.uw.chather.utils.PasswordValidator.checkClientPredicate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import edu.uw.chather.databinding.FragmentRegisterBinding;

/**
 * A simple {@link Fragment} subclass.
 * @author Charles Bryan, Duy Nguyen, Demarco Best, Alec Mac, Alejandro Olono, My Duyen Huynh
 */
public class RegisterFragment extends Fragment {

    /*
    Binding for the register fragment.
     */
    private FragmentRegisterBinding binding;

    /*
    View model for the register fragment.
     */
    private RegisterViewModel mRegisterModel;

    /*
    Password validator for the email field in register.
     */
    private PasswordValidator mNameValidator = checkPwdLength(1);

    /*
    Password validator for the email field in register.
    */
    private PasswordValidator mEmailValidator = checkPwdLength(2)
            .and(checkExcludeWhiteSpace())
            .and(checkPwdSpecialChar("@"));
    /*
    Password validator for the password field in register.
     */
    private PasswordValidator mPassWordValidator =
            checkClientPredicate(pwd -> pwd.equals(binding.Password.getText().toString()))
                    .and(checkPwdLength(7))
                    .and(checkPwdSpecialChar())
                    .and(checkExcludeWhiteSpace())
                    .and(checkPwdDigit())
                    .and(checkPwdLowerCase().or(checkPwdUpperCase()));

    /**
     * An empty constructor.
     */
    public RegisterFragment() {
        // Required empty public constructor
    }

    /**
     * A private method to generate jwt.
     * @param email The email.
     * @return Returns a jwt token.
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRegisterModel = new ViewModelProvider(getActivity())
                .get(RegisterViewModel.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater);
        return binding.getRoot();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.Register.setOnClickListener(this::attemptRegister);
        mRegisterModel.addResponseObserver(getViewLifecycleOwner(),
                this::observeResponse);
    }

    /**
     * Private helper method for validation in register.
     * @param button
     */
    private void attemptRegister(final View button) {
        validateFirst();
    }

    /**
     * Private helper method for adding the first name in the first name field.
     */
    private void validateFirst() {
        mNameValidator.processResult(
                mNameValidator.apply(binding.FirstName.getText().toString().trim()),
                this::validateLast,
                result -> binding.FirstName.setError("Please enter a first name."));
    }

    /**
     * Private helper method for adding the last name in the last name field.
     */
    private void validateLast() {
        mNameValidator.processResult(
                mNameValidator.apply(binding.LastName.getText().toString().trim()),
                this::validateEmail,
                result -> binding.LastName.setError("Please enter a last name."));
    }

    /**
     * Private helper method for adding email address in the email field.
     */
    private void validateEmail() {
        mEmailValidator.processResult(
                mEmailValidator.apply(binding.Email.getText().toString().trim()),
                this::validatePasswordsMatch,
                result -> binding.Email.setError("Please enter a valid Email address."));
    }

    /**
     * Private helper method to check if the password matches the retyped password.
     */
    private void validatePasswordsMatch() {
        PasswordValidator matchValidator =
                checkClientPredicate(
                        pwd -> pwd.equals(binding.RetypePassword.getText().toString().trim()));

        mEmailValidator.processResult(
                matchValidator.apply(binding.Password.getText().toString().trim()),
                this::validatePassword,
                result -> binding.Password.setError("Passwords must match."));
    }

    /**
     * Private helper method for password validation.
     */
    private void validatePassword() {
        mPassWordValidator.processResult(
                mPassWordValidator.apply(binding.Password.getText().toString()),
                this::verifyAuthWithServer,
                result -> binding.Password.setError("Please enter a valid Password."));
    }

    /**
     * Private helper method for verification of the register with the server.
     */
    private void verifyAuthWithServer() {
        mRegisterModel.connect(
                binding.FirstName.getText().toString(),
                binding.LastName.getText().toString(),
                binding.Email.getText().toString(), binding.Password.getText().toString());
        //This is an Asynchronous call. No statements after should rely on the
        //result of connect().
    }

    /**
     * Private helper method for navigating to login if user registration is complete.
     */
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

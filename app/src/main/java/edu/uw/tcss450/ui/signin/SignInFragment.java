package edu.uw.tcss450.ui.signin;
//
///**
// * Duy Nguyen
// * TCSS 450
// * Lab 1 Assignment
// */
//
//import android.os.Bundle;
//
//import androidx.activity.OnBackPressedCallback;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentTransaction;
//import androidx.navigation.Navigation;
//import androidx.navigation.ui.NavigationUI;
//
//import android.text.TextUtils;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.EditText;
//
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.auth0.jwt.exceptions.JWTCreationException;
//
//import edu.uw.tcss450.R;
//import edu.uw.tcss450.databinding.FragmentSignInBinding;
//
///**
// * The sign in fragment class is the the first fragment that we see once starting the app. This fragment
// * will ask the user to login wih an email address and a password.
// */
//public class SignInFragment extends Fragment {
//
//
//    private FragmentSignInBinding binding; // A binding created for this fragment.
//
//    Button buttonSignIn;        // A button for signing in.
//    Button buttonRegister;      // A button for registering.
//    EditText edEmail;           // A text field for the user's email address.
//    EditText edPassword;        // A text field for the user's password info.
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        binding = FragmentSignInBinding.inflate(inflater,container,false);
//
//        edEmail = binding.Email;
//        edPassword = binding.Password;
//        buttonSignIn = binding.SignIn;
//        buttonRegister = binding.Register;
//
//        // Advance to the success fragment once the sign in button is clicked and the email check is valid.
//        buttonSignIn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                checkValidEmail(edEmail);
//            }
//        });
//
//        // Advance to the register fragment once the register button is clicked.
//        buttonRegister.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Navigation.findNavController(getView()).navigate(SignInFragmentDirections.actionSignInFragmentToRegisterFragment());
//
//            }
//        });
//
//
//        return binding.getRoot();
//    }
//
//    /**
//     * Private helper method to check if the login information is valid.
//     * @param edEmail One of the two Safe Args, this is the user's email address.
//     */
//    public void checkValidEmail(EditText edEmail) {
//        String emailLogin = edEmail.getText().toString();
//        String passwordLogin = edPassword.getText().toString();
//        if (TextUtils.isEmpty(emailLogin) || !emailLogin.contains("@")) {
//            edEmail.setError("Please enter an appropriate email address");
//        } else if (passwordLogin.isEmpty()) {
//            edPassword.setError("This field cannot be empty");
//        } else {
//            //SignInFragmentDirections.ActionSignInFragmentToSuccessFragment directions = SignInFragmentDirections.actionSignInFragmentToSuccessFragment(emailLogin,"");
//            //Navigation.findNavController(getView()).navigate(directions);
//
//            Navigation.findNavController(getView()).navigate(
//                    SignInFragmentDirections
//                            .actionSignInFragmentToMainActivity(
//                                    generateJwt(edEmail.getText().toString())  // if doesnt work, use edEmail.getText().toString
//                            ));
//
//            getActivity().finish();;
//        }
//    }
//
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
//}

import static edu.uw.tcss450.utils.PasswordValidator.*;

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

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

import org.json.JSONException;
import org.json.JSONObject;

import edu.uw.tcss450.databinding.FragmentSignInBinding;
import edu.uw.tcss450.utils.PasswordValidator;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignInFragment extends Fragment {

    private FragmentSignInBinding binding;
    private SignInViewModel mSignInModel;

    private PasswordValidator mEmailValidator = checkPwdLength(2)
            .and(checkExcludeWhiteSpace())
            .and(checkPwdSpecialChar("@"));

    private PasswordValidator mPassWordValidator = checkPwdLength(1)
            .and(checkExcludeWhiteSpace());

    public SignInFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSignInModel = new ViewModelProvider(getActivity())
                .get(SignInViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignInBinding.inflate(inflater);
        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonToRegister.setOnClickListener(button ->
                Navigation.findNavController(getView()).navigate(
                        SignInFragmentDirections.actionSignInFragmentToRegisterFragment()
                ));

        binding.buttonSignIn.setOnClickListener(this::attemptSignIn);

        mSignInModel.addResponseObserver(
                getViewLifecycleOwner(),
                this::observeResponse);

        SignInFragmentArgs args = SignInFragmentArgs.fromBundle(getArguments());
        binding.editEmail.setText(args.getEmail().equals("default") ? "" : args.getEmail());
        binding.editPassword.setText(args.getPassword().equals("default") ? "" : args.getPassword());
    }

    private void attemptSignIn(final View button) {
        validateEmail();
    }

    private void validateEmail() {
        mEmailValidator.processResult(
                mEmailValidator.apply(binding.editEmail.getText().toString().trim()),
                this::validatePassword,
                result -> binding.editPassword.setError("Please enter a valid Email address."));
    }

    private void validatePassword() {
        mPassWordValidator.processResult(
                mPassWordValidator.apply(binding.editPassword.getText().toString()),
                this::verifyAuthWithServer,
                result -> binding.editPassword.setError("Please enter a valid Password."));
    }

    private void verifyAuthWithServer() {
        mSignInModel.connect(
                binding.editEmail.getText().toString(),
                binding.editPassword.getText().toString());
        //This is an Asynchronous call. No statements after should rely on the
        //result of connect().
    }

    /**
     * Helper to abstract the navigation to the Activity past Authentication.
     * @param email users email
     * @param jwt the JSON Web Token supplied by the server
     */
    private void navigateToSuccess(final String email, final String jwt) {
        Navigation.findNavController(getView())
                .navigate(SignInFragmentDirections
                        .actionSignInFragmentToMainActivity(email, jwt));
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
                    binding.editEmail.setError(
                            "Error Authenticating: " +
                                    response.getJSONObject("data").getString("message"));
                } catch (JSONException e) {
                    Log.e("JSON Parse Error", e.getMessage());
                }
            } else {
                try {
                    navigateToSuccess(
                            binding.editEmail.getText().toString(),
                            response.getString("token")
                    );
                } catch (JSONException e) {
                    Log.e("JSON Parse Error", e.getMessage());
                }
            }
        } else {
            Log.d("JSON Response", "No Response");
        }
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

}

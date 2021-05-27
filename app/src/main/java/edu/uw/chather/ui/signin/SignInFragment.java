package edu.uw.chather.ui.signin;

import static edu.uw.chather.utils.PasswordValidator.*;

import android.content.Context;
import android.content.SharedPreferences;
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

import com.auth0.android.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

import org.json.JSONException;
import org.json.JSONObject;

import edu.uw.chather.R;
import edu.uw.chather.databinding.FragmentSignInBinding;
import edu.uw.chather.ui.model.PushyTokenViewModel;
import edu.uw.chather.ui.model.UserInfoViewModel;
import edu.uw.chather.utils.PasswordValidator;

/**
 * A simple {@link Fragment} subclass.
 *
 * @author Charles Bryan, Duy Nguyen, Demarco Best, Alec Mac, Alejandro Olono, My Duyen Huynh
 */
public class SignInFragment extends Fragment {

    /*
    A binding for the sign in fragment.
     */
    private FragmentSignInBinding binding;

    /*
    A view model to allow changes in the sign in fragment.
     */
    private SignInViewModel mSignInModel;

    /*
    A validator to check the email field.
     */
    private PasswordValidator mEmailValidator = checkPwdLength(2)
            .and(checkExcludeWhiteSpace())
            .and(checkPwdSpecialChar("@"));

    /*
    A validator to check the password field.
     */
    private PasswordValidator mPassWordValidator = checkPwdLength(1)
            .and(checkExcludeWhiteSpace());

    private PushyTokenViewModel mPushyTokenViewModel;
    private UserInfoViewModel mUserViewModel;

    /**
     * An empty constructor of the sign in fragment.
     */
    public SignInFragment() {
        // Required empty public constructor
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewModelProvider provider = new ViewModelProvider(getActivity());
        mSignInModel = provider.get(SignInViewModel.class);
        mPushyTokenViewModel = provider.get(PushyTokenViewModel.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignInBinding.inflate(inflater);
        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    /**
     * {@inheritDoc}
     */
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

        binding.btnForgotPassword.setOnClickListener(button ->
                Navigation.findNavController(getView()).navigate(
                        SignInFragmentDirections.actionSignInFragmentToPasswordResetFragment()));

        mPushyTokenViewModel.addTokenObserver(getViewLifecycleOwner(), token ->
                binding.buttonSignIn.setEnabled((!token.isEmpty())));

        mPushyTokenViewModel.addResponseObserver(getViewLifecycleOwner(), this::observePushyPutRequest);
    }

    /**
     * Calls the validate email password.
     *
     * @param button
     */
    private void attemptSignIn(final View button) {
        validateEmail();
    }

    /**
     * A private helper method to check for valid email.
     */
    private void validateEmail() {
        mEmailValidator.processResult(
                mEmailValidator.apply(binding.editEmail.getText().toString().trim()),
                this::validatePassword,
                result -> binding.editPassword.setError("Please enter a valid Email address."));
    }


    /**
     * A private helper method to check for valid password.
     */
    private void validatePassword() {
        mPassWordValidator.processResult(
                mPassWordValidator.apply(binding.editPassword.getText().toString()),
                this::verifyAuthWithServer,
                result -> binding.editPassword.setError("Please enter a valid Password."));
    }

    /**
     * A private helper method to connect sign in with the server.
     */
    private void verifyAuthWithServer() {
        mSignInModel.connect(
                binding.editEmail.getText().toString(),
                binding.editPassword.getText().toString());
        //This is an Asynchronous call. No statements after should rely on the
        //result of connect().
    }

    /**
     * Helper to abstract the navigation to the Activity past Authentication.
     *
     * @param email users email
     * @param jwt   the JSON Web Token supplied by the server
     */
    private void navigateToSuccess(final String email, final String jwt) {
        if (binding.switchSignin.isChecked()) {
            SharedPreferences prefs =
                getActivity().getSharedPreferences(
                        getString(R.string.keys_shared_prefs),
                        Context.MODE_PRIVATE);
            //Store the credentials in SharedPrefs
            prefs.edit().putString(getString(R.string.keys_prefs_jwt), jwt).apply();
        }

        Navigation.findNavController(getView())
                .navigate(SignInFragmentDirections
                        .actionSignInFragmentToMainActivity(email, jwt));

        //Remove THIS activity from the Task list. Pops off the backstack
        getActivity().finish();
    }

    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences prefs =
                getActivity().getSharedPreferences(
                        getString(R.string.keys_shared_prefs),
                        Context.MODE_PRIVATE);
        if (prefs.contains(getString(R.string.keys_prefs_jwt))) {
            String token = prefs.getString(getString(R.string.keys_prefs_jwt), "");
            com.auth0.android.jwt.JWT jwt = new JWT(token);
            // Check to see if the web token is still valid or not. To make a JWT expire after a
            // longer or shorter time period, change the expiration time when the JWT is
            // created on the web service.
            if(!jwt.isExpired(0)) {
                String email = jwt.getClaim("email").asString();
                navigateToSuccess(email, token);
                return;
            }
        }
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
                    mUserViewModel = new ViewModelProvider(getActivity(),
                            new UserInfoViewModel.UserInfoViewModelFactory(
                                    binding.editEmail.getText().toString(),
                                    response.getString("token")
                            )).get(UserInfoViewModel.class);
                    sendPushyToken();
                } catch (JSONException e) {
                    Log.e("JSON Parse Error", e.getMessage());
                }
            }
        } else {
            Log.d("JSON Response", "No Response");
        }
    }

    /**
     * Helper to abstract the request to send the pushy token to the web service
     */
    private void sendPushyToken() {
        mPushyTokenViewModel.sendTokenToWebservice(mUserViewModel.getmJwt());
    }

    /**
     * An observer on the HTTP Response from the web server. This observer should be attached to PushyTokenViewModl.
     *
     * @param response the Response from the server
     */
    private void observePushyPutRequest(final JSONObject response) {
        if (response.length() > 0) {
            if (response.has("code")) {
                // this error cannot be fixed by the user changing credentials...
                binding.editEmail.setError("Error Authenticating on Push Token. Please contact support");
            } else {
                navigateToSuccess(
                        binding.editEmail.getText().toString(),
                        mUserViewModel.getmJwt()
                );
            }
        }
    }
}

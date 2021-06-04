package edu.uw.chather.ui.model;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/**
 * The user info view model class.
 */
public class UserInfoViewModel extends ViewModel {
    /**
     * The user's current email.
     */
    private final String mEmail;

    /**
     * The current Jwt.
     */
    private static String mJwt;

    private final String mUsername;

    /**
     * Constructor for the user info view model.
     * @param email
     * @param jwt
     */
    private UserInfoViewModel(String email, String jwt, String username) {
        mEmail = email;
        mJwt = jwt;
        mUsername = username;
    }

    /**
     * Get the user's current email.
     * @return
     */
    public String getEmail() {
        return mEmail;
    }

    /**
     * Get the current Jwt.
     * @return
     */
    public static String getmJwt() {
        return mJwt;
    }

    public String getUsername() {
        return mUsername;
    }

    public static class UserInfoViewModelFactory implements ViewModelProvider.Factory {

        private final String email;
        private final String jwt;
        private final String username;

        public UserInfoViewModelFactory(String email, String jwt, String username) {
            this.email = email;
            this.jwt = jwt;
            this.username = username;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass == UserInfoViewModel.class) {
                return (T) new UserInfoViewModel(email, jwt, username);
            }
            throw new IllegalArgumentException(
                    "Argument must be: " + UserInfoViewModel.class);
        }
    }
}



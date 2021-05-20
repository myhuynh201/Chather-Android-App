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

    /**
     * Constructor for the user info view model.
     * @param email
     * @param jwt
     */
    private UserInfoViewModel(String email, String jwt) {
        mEmail = email;
        mJwt = jwt;
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

    public static class UserInfoViewModelFactory implements ViewModelProvider.Factory {

        private final String email;
        private final String jwt;

        public UserInfoViewModelFactory(String email, String jwt) {
            this.email = email;
            this.jwt = jwt;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass == UserInfoViewModel.class) {
                return (T) new UserInfoViewModel(email, jwt);
            }
            throw new IllegalArgumentException(
                    "Argument must be: " + UserInfoViewModel.class);
        }
    }
}



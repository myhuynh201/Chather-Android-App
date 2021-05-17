//package edu.uw.chather.ui.changepassword;
//
//import android.os.Bundle;
//
//import androidx.fragment.app.Fragment;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.EditText;
//
//import edu.uw.chather.R;
//import edu.uw.chather.databinding.FragmentChangePasswordBinding;
//import edu.uw.chather.databinding.FragmentPasswordResetBinding;
//import edu.uw.chather.utils.PasswordValidator;
//
//import static edu.uw.chather.utils.PasswordValidator.checkClientPredicate;
//import static edu.uw.chather.utils.PasswordValidator.checkExcludeWhiteSpace;
//import static edu.uw.chather.utils.PasswordValidator.checkPwdDigit;
//import static edu.uw.chather.utils.PasswordValidator.checkPwdLength;
//import static edu.uw.chather.utils.PasswordValidator.checkPwdLowerCase;
//import static edu.uw.chather.utils.PasswordValidator.checkPwdSpecialChar;
//import static edu.uw.chather.utils.PasswordValidator.checkPwdUpperCase;
//
//
//public class ChangePasswordFragment extends Fragment {
//
//    private FragmentChangePasswordBinding binding;
//
//    /*
//        Password validator for the password field in register.
//     */
//    private PasswordValidator mPassWordValidator =
//            checkClientPredicate(pwd -> pwd.equals(binding..getText().toString()))
//                    .and(checkPwdLength(7))
//                    .and(checkPwdSpecialChar())
//                    .and(checkExcludeWhiteSpace())
//                    .and(checkPwdDigit())
//                    .and(checkPwdLowerCase().or(checkPwdUpperCase()));
//
//    /*
//        Password validator for the email field in register.
//    */
//    private PasswordValidator mEmailValidator = checkPwdLength(2)
//            .and(checkExcludeWhiteSpace())
//            .and(checkPwdSpecialChar("@"));
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_change_password, container, false);
//    }
//
//
//    /**
//     * Private helper method to check if the password matches the retyped password.
//     */
//    private void validatePasswordsMatch() {
//        PasswordValidator matchValidator =
//                checkClientPredicate(
//                        pwd -> pwd.equals(binding.etNewPasswordConfirm.getText().toString().trim()));
//
//        mEmailValidator.processResult(
//                matchValidator.apply(binding.etNewPassword.getText().toString().trim()),
//                this::validatePassword,
//                result -> binding.etNewPassword.setError("Passwords must match."));
//    }
//
//    /**
//     * Private helper method for password validation.
//     */
//    private void validatePassword() {
//        mPassWordValidator.processResult(
//                mPassWordValidator.apply(binding.etOldPassword.getText().toString()),
//                this::verifyAuthWithServer,
//                result -> binding.etOldPassword.setError("Please enter your current password."));
//    }
//
//}
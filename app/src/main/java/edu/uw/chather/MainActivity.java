package edu.uw.chather;
/**
 * Main activity that runs in the background of the app. Sets up our bottom navigation.
 */

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;


import com.auth0.android.jwt.JWT;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import edu.uw.chather.databinding.ActivityMainBinding;
import edu.uw.chather.services.PushReceiver;
import edu.uw.chather.ui.chat.ChatMessage;
import edu.uw.chather.ui.chat.ChatViewModel;
import edu.uw.chather.ui.model.NewMessageCountViewModel;
import edu.uw.chather.ui.model.UserInfoViewModel;
import edu.uw.chather.ui.passwordreset.PasswordResetFragment;


public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private MainPushMessageReceiver mPushMessageReceiver;
    private NewMessageCountViewModel mNewMessageModel;
    private ActivityMainBinding binding;


    /**
     * Creates our bottom navigation menu from the menu elements we've given it before.
     * See onCreate parent method for more implementation details.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        MainActivityArgs args = MainActivityArgs.fromBundle(getIntent().getExtras());


        //  AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//        String email = args.getJwt();
//        //Import com.auth0.android.jwt.JWT
//        JWT jwt = new JWT(args.getJwt());

        // Check to see if the web token is still valid or not. To make a JWT expire after a
        // longer or shorter time period, change the expiration time when the JWT is
        // created on the web service.
//        if(!jwt.isExpired(0)) {
//            new ViewModelProvider(this, new UserInfoViewModel.UserInfoViewModelFactory(email, jwt)).get(UserInfoViewModel.class);
//        } else {
////            In production code, add in your own error handling/flow for when the JWT is expired
//            throw new IllegalStateException("JWT is expired!");
//        }

        new ViewModelProvider(this,
                new UserInfoViewModel.UserInfoViewModelFactory(args.getEmail(), args.getJwt())
        ).get(UserInfoViewModel.class);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.success, R.id.navigation_connections, R.id.chatListFragment, R.id.weatherFragment)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        mNewMessageModel = new ViewModelProvider(this).get(NewMessageCountViewModel.class);

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.chatListFragment) {
                // When the user navigates to the chats page, reset the new message count.
                // This will need some extra logic for your project as it should have
                // multiple chatrooms.
                mNewMessageModel.reset();
            }
        });

        mNewMessageModel.addMessageCountObserver(this, count -> {
            BadgeDrawable badge = binding.navView.getOrCreateBadge(R.id.chatListFragment);
            badge.setMaxCharacterCount(2);
            if (count > 0) {
                // new messages! update and show the notification badge.
                badge.setNumber(count);
                badge.setVisible(true);
            } else {
                // user did some action to clear the new messages, remove the badge
                badge.clearNumber();
                badge.setVisible(false);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPushMessageReceiver == null) {
            mPushMessageReceiver = new MainPushMessageReceiver();
        }
        IntentFilter iFilter = new IntentFilter(PushReceiver.RECEIVED_NEW_MESSAGE);
        registerReceiver(mPushMessageReceiver, iFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mPushMessageReceiver != null) {
            unregisterReceiver(mPushMessageReceiver);
        }
    }

    /**
     * A BroadcastReceiver that listens fro messages sent from PushReceiver
     */
    private class MainPushMessageReceiver extends BroadcastReceiver {

        private ChatViewModel mModel =
                new ViewModelProvider(MainActivity.this).get(ChatViewModel.class);

        @Override
        public void onReceive(Context context, Intent intent) {
            NavController nc = Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment);
            NavDestination nd = nc.getCurrentDestination();
            if (intent.hasExtra("chatMessage")) {
                ChatMessage cm = (ChatMessage) intent.getSerializableExtra("chatMessage");

                // If the user is not on the chat screen, update the
                // NewMessageCountView Model
                if (nd.getId() != R.id.chatListFragment) {
                    mNewMessageModel.increment();
                }

                // Inform the view model holding chatroom messages of the new message.
                mModel.addMessage(intent.getIntExtra("chatid", -1), cm);
            }
        }
    }
}
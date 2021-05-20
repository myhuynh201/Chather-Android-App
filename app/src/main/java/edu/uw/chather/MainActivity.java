package edu.uw.chather;
/**
 * Main activity that runs in the background of the app. Sets up our bottom navigation.
 */
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;


import com.google.android.material.bottomnavigation.BottomNavigationView;

import edu.uw.chather.ui.model.UserInfoViewModel;
import edu.uw.chather.ui.weather.WeatherFragment;
import edu.uw.chather.ui.passwordreset.PasswordResetFragment;
import edu.uw.chather.utils.Utils;


public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private NavController navController;
    /**
     * Creates our bottom navigation menu from the menu elements we've given it before.
     * See onCreate parent method for more implementation details.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_main);

        MainActivityArgs args = MainActivityArgs.fromBundle(getIntent().getExtras());


        //  AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        //String email = args.getJwt();
        //Import com.auth0.android.jwt.JWT
        //JWT jwt = new JWT(args.getJwt());

        // Check to see if the web token is still valid or not. To make a JWT expire after a
        // longer or shorter time period, change the expiration time when the JWT is
        // created on the web service.
        // if(!jwt.isExpired(0)) {
        //    new ViewModelProvider(this, new UserInfoViewModel.UserInfoViewModelFactory(jwt)).get(UserInfoViewModel.class);
        //} else {
        //In production code, add in your own error handling/flow for when the JWT is expired
        //    throw new IllegalStateException("JWT is expired!");
        //}

        new ViewModelProvider(this,
                new UserInfoViewModel.UserInfoViewModelFactory(args.getEmail(), args.getJwt())
        ).get(UserInfoViewModel.class);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.success, R.id.navigation_chats, R.id.navigation_connections, R.id.weatherFragment)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.drop_down, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.navigate_change_password) {
            navController.navigate(R.id.changePasswordFragment);
        }

        if (id == R.id.navigate_button_theme) {
            navController.navigate(R.id.changeThemeFragment);
        }

        if (id == R.id.navigate_sign_out) {
            //TODO SIGN OUT
        }
        return super.onOptionsItemSelected(item);
    }
}
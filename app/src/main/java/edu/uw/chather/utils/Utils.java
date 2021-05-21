package edu.uw.chather.utils;

import android.app.Activity;

import androidx.appcompat.app.AppCompatDelegate;

import edu.uw.chather.MainActivity;
import edu.uw.chather.R;

/**
 * A class to set the them of the activity
 * @author Duy Nguyen, Demarco Best, Alec Mac, Alejandro Olono, My Duyen Huynh
 */
public class Utils {
    private static int sTheme;
    public final static int BLUE_THEME = 0;
    public final static int GREEN_THEME  = 1;
    public final static int PINK_THEME  = 2;
    public final static int PURPLE_THEME  = 3;
    public final static int DARK_THEME  = 4;

    /**
     * Set the theme of the Activity, and restart it by creating a new Activity of the same type.
     *
     * @param activity
     * @param theme
     */
    public static void changeToTheme(Activity activity, int theme) {
        sTheme = theme;
        activity.recreate();
    }

    /**
     * Set the theme
     *
     * @param activity
     */
    public static void onActivityCreateSetTheme(MainActivity activity) {
        switch (sTheme) {
            default:
            case BLUE_THEME:
                activity.getTheme().applyStyle(R.style.AppTheme, true);
                break;
            case GREEN_THEME:
                activity.getTheme().applyStyle(R.style.GreenTheme, true);
                break;
            case PINK_THEME:
                activity.getTheme().applyStyle(R.style.PinkTheme, true);
                break;
            case PURPLE_THEME:
                activity.getTheme().applyStyle(R.style.PurpleTheme, true);
                break;
            case DARK_THEME:
               // AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                activity.getTheme().applyStyle(R.style.DarkTheme, true);
                break;
        }
    }
}

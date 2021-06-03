package edu.uw.chather.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * The sharedPreferences class
 * @author Duy Nguyen, Demarco Best, Alec Mac, Alejandro Olono, My Duyen Huynh
 *
 */
public class SharedPreferencesManager {
    /**
     * SharedPreferences to store the settings.
     */
    private final SharedPreferences sPreferences;

    /**
     * The context class
     */
    private final Context context;

    /**
     * An SharedPreferencesManager constructor.
     * @param context the context of the shared manager
     */
    public SharedPreferencesManager(Context context) {
        this.context = context;
        sPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }
    /**
     * Retrieve an int in sharedPreferences
     * @param tag identifies the value
     * @param defValue default value
     * @return the stored or default value
     */
    public int retrieveInt(String tag, int defValue) {

        return sPreferences.getInt(tag, defValue);
    }
}

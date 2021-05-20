package edu.uw.chather.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPreferencesManager {
    /**
     * SharedPreferences to store the settings.
     */
    private SharedPreferences sPreferences;

    private Context context;

    public SharedPreferencesManager(Context context) {
        this.context = context;
        sPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }
    /**
     * @param tag      identifies the value
     * @param defValue default value
     * @return the stored or default value
     */
    public int retrieveInt(String tag, int defValue) {

        return sPreferences.getInt(tag, defValue);
    }
}

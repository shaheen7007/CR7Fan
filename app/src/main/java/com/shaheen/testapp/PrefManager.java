package com.shaheen.testapp;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {
    SharedPreferences sharedPreferences;
    private static PrefManager instance;

    public PrefManager(Context context) {
        sharedPreferences = context.getSharedPreferences(Consts.PREF_NAME, Context.MODE_PRIVATE);
    }

    public static PrefManager getInstance(Context context) {
        if (instance == null) {
            instance = new PrefManager(context);
        }
        return instance;
    }

    private void putString(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    private void putInt(String key, int value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    private void putBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }


    public int getCountry() {
        return sharedPreferences.getInt(Consts.SELECTED_COUNTRY, 0);
    }

    public void setCountry(int country) {
        putInt(Consts.SELECTED_COUNTRY, country);
    }

    public boolean getIsLoggedIn() {
        return sharedPreferences.getBoolean(Consts.IS_LOGGED_IN, false);
    }

    public void setIsLoggedIn(boolean isLoggedIn) {
        putBoolean(Consts.IS_LOGGED_IN, isLoggedIn);
    }


}

package com.shaheen.testapp;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager{
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


    public String getCountry() {
        return sharedPreferences.getString(Consts.SELECTED_COUNTRY, "world");
    }

    public void setCountry(String country) {
        putString(Consts.SELECTED_COUNTRY, country);
    }


}

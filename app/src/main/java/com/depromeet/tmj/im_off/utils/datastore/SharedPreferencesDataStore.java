package com.depromeet.tmj.im_off.utils.datastore;

import android.content.Context;
import android.content.SharedPreferences;

public abstract class SharedPreferencesDataStore {
    private final SharedPreferences sharedPreferences;


    public SharedPreferencesDataStore(Context context) {
        this.sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    }

    protected String getString(String key, String defValue) {
        return sharedPreferences.getString(key, defValue);
    }

    protected void putString(String key, String value) {
        sharedPreferences.edit().putString(key, value).apply();
    }

    protected int getInt(String key, int defValue) {
        return sharedPreferences.getInt(key, defValue);
    }

    protected void putInt(String key, int value) {
        sharedPreferences.edit().putInt(key, value).apply();
    }

    protected void putBoolean(String key, boolean value) {
        sharedPreferences.edit().putBoolean(key, value).apply();
    }

    protected boolean getBoolean(String key, boolean defValue) {
        return sharedPreferences.getBoolean(key, defValue);
    }

}

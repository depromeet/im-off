package com.depromeet.tmj.im_off.utils.datastore;

import com.depromeet.tmj.im_off.ImOffApplication;

public class AppPreferencesDataStore extends SharedPreferencesDataStore {
    private static AppPreferencesDataStore INSTANCE = null;

    private static final String KEY_LEAVING_OFF_HOUR = "KEY_LEAVING_OFF_HOUR";
    private static final String KEY_LEAVING_OFF_MINUTE = "KEY_LEAVING_OFF_MINUTE";
    private static final String KEY_START_WORKING_HOUR = "KEY_START_WORKING_HOUR";
    private static final String KEY_START_WORKING_MINUTE = "KEY_START_WORKING_MINUTE";

    public AppPreferencesDataStore() {
        super(ImOffApplication.getApplication());
    }

    public static AppPreferencesDataStore getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new AppPreferencesDataStore();
        }
        return INSTANCE;
    }

    public int getLeavingOffHour() {
        return getInt(KEY_LEAVING_OFF_HOUR, 0);
    }

    public void putLeavingOffHour(int hour) {
        putInt(KEY_LEAVING_OFF_HOUR, hour);
    }

    public int getLeavingOffMinute() {
        return getInt(KEY_LEAVING_OFF_MINUTE, 0);
    }

    public void putLeavingOffMinute(int minute) {
        putInt(KEY_LEAVING_OFF_MINUTE, minute);
    }

    public int getStartWorkingHour() {
        return getInt(KEY_START_WORKING_HOUR, 0);
    }

    public void putStartWorkingHour(int hour) {
        putInt(KEY_START_WORKING_HOUR, hour);
    }

    public int getStartWorkingMinute() {
        return getInt(KEY_START_WORKING_MINUTE, 0);
    }

    public void putStartWorkingMinute(int minute) {
        putInt(KEY_START_WORKING_MINUTE, minute);
    }
}

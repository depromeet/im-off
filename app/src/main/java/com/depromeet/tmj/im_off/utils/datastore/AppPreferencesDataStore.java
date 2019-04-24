package com.depromeet.tmj.im_off.utils.datastore;

import com.depromeet.tmj.im_off.ImOffApplication;

import java.util.Calendar;

public class AppPreferencesDataStore extends SharedPreferencesDataStore {
    private static AppPreferencesDataStore INSTANCE = null;

    public static final String CHANNEL_NOTIFICATION = "CHANNEL_NOTIFICATION";

    private static final String KEY_LEAVING_OFF_HOUR = "KEY_LEAVING_OFF_HOUR";
    private static final String KEY_LEAVING_OFF_MINUTE = "KEY_LEAVING_OFF_MINUTE";
    private static final String KEY_START_WORKING_HOUR = "KEY_START_WORKING_HOUR";
    private static final String KEY_START_WORKING_MINUTE = "KEY_START_WORKING_MINUTE";
    private static final String KEY_JOB_POSITION = "KEY_JOB_POSITION";
    private static final String KEY_FIRST_LAUNCH = "KEY_FIRST_LAUNCH";
    private static final String KEY_NOTI_ENABLE = "KEY_NOTI_ENABLE";

    public AppPreferencesDataStore() {
        super(ImOffApplication.getApplication());
    }

    public static AppPreferencesDataStore getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AppPreferencesDataStore();
        }
        return INSTANCE;
    }

    public int getLeavingOffHour() {
        return getInt(KEY_LEAVING_OFF_HOUR, -1);
    }

    public void putLeavingOffHour(int hour) {
        putInt(KEY_LEAVING_OFF_HOUR, hour);
    }

    public int getLeavingOffMinute() {
        return getInt(KEY_LEAVING_OFF_MINUTE, -1);
    }

    public void putLeavingOffMinute(int minute) {
        putInt(KEY_LEAVING_OFF_MINUTE, minute);
    }

    public int getStartWorkingHour() {
        return getInt(KEY_START_WORKING_HOUR, -1);
    }

    public void putStartWorkingHour(int hour) {
        putInt(KEY_START_WORKING_HOUR, hour);
    }

    public int getStartWorkingMinute() {
        return getInt(KEY_START_WORKING_MINUTE, -1);
    }

    public void putStartWorkingMinute(int minute) {
        putInt(KEY_START_WORKING_MINUTE, minute);
    }

    public void putJobPosition(String job) {
        putString(KEY_JOB_POSITION, job);
    }

    public String getJobPosition() {
        return getString(KEY_JOB_POSITION, "나의 직군");
    }

    public void putFirstLaunch(boolean isFirst) {
        putBoolean(KEY_FIRST_LAUNCH, isFirst);
    }

    public boolean getFirstLaunch() {
        return getBoolean(KEY_FIRST_LAUNCH, true);
    }

    public void putNotiEnable(boolean isEnable) {
        putBoolean(KEY_NOTI_ENABLE, isEnable);
    }

    public boolean getNotiEnable() {
        return getBoolean(KEY_NOTI_ENABLE, true);
    }
}

package com.depromeet.tmj.im_off.utils;

import com.depromeet.tmj.im_off.utils.datastore.AppPreferencesDataStore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
    public static final String FORMAT_TIME = "yyyy-MM-dd'T'kk:mm'Z'";

    public static Date string2Date(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat(FORMAT_TIME, Locale.getDefault());

        try {
            return formatter.parse(date);
        } catch (ParseException e){
            e.printStackTrace();
            return new Date();
        }
    }

    public static Long now() {
        return new Date().getTime();
    }

    public static Date todayOffStartTime() {
        AppPreferencesDataStore dataStore = AppPreferencesDataStore.getInstance();
        Calendar offTime = Calendar.getInstance();

        offTime.set(Calendar.HOUR_OF_DAY,dataStore.getLeavingOffHour());
        offTime.set(Calendar.MINUTE, dataStore.getLeavingOffMinute());

        return offTime.getTime();
    }

    public static Date todayStartWorkingTime() {
        AppPreferencesDataStore dataStore = AppPreferencesDataStore.getInstance();
        Calendar startTime = Calendar.getInstance();

        startTime.set(Calendar.HOUR_OF_DAY,dataStore.getStartWorkingHour());
        startTime.set(Calendar.MINUTE, dataStore.getStartWorkingMinute());

        return startTime.getTime();
    }

    public static Date todayOffEndTime() {
        AppPreferencesDataStore dataStore = AppPreferencesDataStore.getInstance();
        Calendar offTime = Calendar.getInstance();

        offTime.set(Calendar.HOUR_OF_DAY,dataStore.getLeavingOffHour());
        offTime.set(Calendar.MINUTE, dataStore.getLeavingOffMinute());
        offTime.add(Calendar.MINUTE, 10);

        return offTime.getTime();
    }
}

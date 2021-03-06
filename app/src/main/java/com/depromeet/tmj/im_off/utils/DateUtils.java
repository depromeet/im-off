package com.depromeet.tmj.im_off.utils;

import com.depromeet.tmj.im_off.utils.datastore.AppPreferencesDataStore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
    public static final String FORMAT_TIME = "yyyy-MM-dd'T'kk:mm'Z'";
    private static final float ANGLE_HOUR = 30f;
    private static final float ANGLE_MINUTE = 0.5f;
    private static final float ANGLE_OFFSET = 90f;

    public static Date string2Date(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat(FORMAT_TIME, Locale.getDefault());

        try {
            return formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }

    public static Long nowTime() {
        return new Date().getTime();
    }

    public static Calendar nowCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(nowTime());

        return calendar;
    }

    public static String yesterday(Calendar calendar) {
        Calendar newCalendar = Calendar.getInstance();
        newCalendar.setTimeInMillis(calendar.getTimeInMillis());
        newCalendar.add(Calendar.DAY_OF_MONTH, -1);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
        return simpleDateFormat.format(newCalendar.getTime());
    }

    public static String calendar2String(Calendar calendar) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
        return simpleDateFormat.format(calendar.getTime());
    }

    public static String today() {
        Calendar calendar = nowCalendar();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);

        return simpleDateFormat.format(calendar.getTime());
    }

    public static Date todayOffStartTime() {
        AppPreferencesDataStore dataStore = AppPreferencesDataStore.getInstance();
        Calendar offTime = nowCalendar();

        offTime.set(Calendar.HOUR_OF_DAY, dataStore.getLeavingOffHour());
        offTime.set(Calendar.MINUTE, dataStore.getLeavingOffMinute());

        return offTime.getTime();
    }

    public static Date todayOffStartTime(Calendar calendar) {
        AppPreferencesDataStore dataStore = AppPreferencesDataStore.getInstance();
        Calendar offTime = Calendar.getInstance();
        offTime.setTimeInMillis(calendar.getTimeInMillis());

        if (offTime.get(Calendar.HOUR_OF_DAY) < dataStore.getLeavingOffHour()) {
            offTime.add(Calendar.DATE, -1);
        }

        offTime.set(Calendar.HOUR_OF_DAY, dataStore.getLeavingOffHour());
        offTime.set(Calendar.MINUTE, dataStore.getLeavingOffMinute());

        return offTime.getTime();
    }

    public static Calendar todayOffCalendar() {
        AppPreferencesDataStore dataStore = AppPreferencesDataStore.getInstance();
        Calendar offTime = Calendar.getInstance();

        offTime.set(Calendar.HOUR_OF_DAY, dataStore.getLeavingOffHour());
        offTime.set(Calendar.MINUTE, dataStore.getLeavingOffMinute());

        return offTime;
    }

    public static Date todayStartWorkingTime(Calendar calendar) {
        AppPreferencesDataStore dataStore = AppPreferencesDataStore.getInstance();
        Calendar startTime = Calendar.getInstance();
        startTime.setTimeInMillis(calendar.getTimeInMillis());

        startTime.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
        startTime.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH));
        startTime.set(Calendar.HOUR_OF_DAY, dataStore.getStartWorkingHour());
        startTime.set(Calendar.MINUTE, dataStore.getStartWorkingMinute());

        return startTime.getTime();
    }

    public static Date getStartWorkingTime(Calendar calendar) {
        AppPreferencesDataStore dataStore = AppPreferencesDataStore.getInstance();
        Calendar startTime = Calendar.getInstance();
        startTime.setTimeInMillis(calendar.getTimeInMillis());

        if (startTime.get(Calendar.HOUR_OF_DAY) < dataStore.getStartWorkingHour()) {
            startTime.add(Calendar.DATE, -1);
        }
        startTime.set(Calendar.HOUR_OF_DAY, dataStore.getStartWorkingHour());
        startTime.set(Calendar.MINUTE, dataStore.getStartWorkingMinute());

        return startTime.getTime();
    }


    public static Date todayOffEndTime(Calendar calendar) {
        AppPreferencesDataStore dataStore = AppPreferencesDataStore.getInstance();
        Calendar offTime = Calendar.getInstance();
        offTime.setTimeInMillis(calendar.getTimeInMillis());

        if (offTime.get(Calendar.HOUR_OF_DAY) < dataStore.getStartWorkingHour()) {
            offTime.add(Calendar.DATE, -1);
        }

        offTime.set(Calendar.HOUR_OF_DAY, dataStore.getLeavingOffHour());
        offTime.set(Calendar.MINUTE, dataStore.getLeavingOffMinute());
        offTime.add(Calendar.MINUTE, 10);

        return offTime.getTime();
    }

    public static float time2Angle(Date date) {
        long time = date.getTime();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);

        int hours = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);

        return (ANGLE_HOUR * hours) + (ANGLE_MINUTE * minute) - ANGLE_OFFSET;
    }

    public static float time2SweepAngle(Date startDate, Date endDate) {
        long diff = endDate.getTime() - startDate.getTime();
        int hours = (int) diff / (1000 * 60 * 60);
        int minute = (int) (diff / (1000 * 60)) % 60;

        return (ANGLE_HOUR * hours) + (ANGLE_MINUTE * minute);
    }

    public static String workingTime(Calendar calendar) {
        long diff = DateUtils.todayOffStartTime().getTime() - DateUtils.todayStartWorkingTime(calendar).getTime();
        int hours = (int) diff / (1000 * 60 * 60);
        int minute = (int) (diff / (1000 * 60)) % 60;

        return String.format(Locale.KOREA, "%d:%02d", hours, minute);
    }

    public static String remainingTime(Date offStartDate, Date current) {
        long diff = offStartDate.getTime() - current.getTime();
        int hours = (int) diff / (1000 * 60 * 60);
        int minute = (int) (diff / (1000 * 60)) % 60;

        return String.format(Locale.KOREA, "%d:%02d", hours, minute);
    }

    public static String nightWorkingTime(Calendar calendar) {
        long diff = calendar.getTimeInMillis() - todayOffStartTime(calendar).getTime();
        int hours = (int) diff / (1000 * 60 * 60);
        int minute = (int) (diff / (1000 * 60)) % 60;

        return String.format(Locale.KOREA, "+ %d:%02d", hours, minute);
    }

    public static String nightWorkingTimeTitle(Calendar calendar) {
        long diff = calendar.getTimeInMillis() - todayOffStartTime(calendar).getTime();
        int hours = (int) diff / (1000 * 60 * 60);
        int minute = (int) (diff / (1000 * 60)) % 60;

        return String.format(Locale.KOREA, "%d시간 %d분 더 일하는 중..", hours, minute);
    }

    public static int getDayOfWeek() {
        return Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1;
    }

    public static boolean isWeekend() {
        int dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        return dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY;
    }

    public static Long getThisWeekMonday() {
        // 최근 일주일 검색할때 사용
        Calendar calendar = nowCalendar();

        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        return calendar.getTime().getTime();
    }
}

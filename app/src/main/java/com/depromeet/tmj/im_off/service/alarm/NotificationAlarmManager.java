package com.depromeet.tmj.im_off.service.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.depromeet.tmj.im_off.utils.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class NotificationAlarmManager {
    private final Context context;
    private static AlarmManager alarmManager;

    public NotificationAlarmManager(Context context) {
        this.context = context;
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    private void register(Calendar calendar, NotificationType notificationType) {
        Intent intent = NotificationBroadcastReceiver.getCallingIntent(context, notificationType);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                notificationType.ordinal(), intent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

        // for debug
        Log.d(this.getClass().getSimpleName(), "register notification\n " +
                new SimpleDateFormat(DateUtils.FORMAT_TIME, Locale.getDefault()).format(calendar.getTime()));
    }

    private void cancel(NotificationType notificationType) {
        Intent intent = NotificationBroadcastReceiver.getCallingIntent(context);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                notificationType.ordinal(), intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (pendingIntent != null) {
            alarmManager.cancel(pendingIntent);
            pendingIntent.cancel();
        }
    }

    private void cancelAll() {
        for (NotificationType notificationType : NotificationType.values()) {
            cancel(notificationType);
        }
    }

    public void registerAll() {
        cancelAll();
        for (NotificationType notificationType : NotificationType.values()) {
            switch (notificationType) {
                case LEAVING:
                    Calendar calendar = DateUtils.todayOffCalendar();
                    calendar.add(Calendar.MINUTE, 5);
                    calendar.set(Calendar.SECOND, 0);
                    calendar.set(Calendar.MILLISECOND, 0);

                    if(DateUtils.nowCalendar().getTime().after(calendar.getTime())) {
                        calendar.add(Calendar.DATE, 1);
                    }
                    register(calendar, notificationType);
                    break;
                default:
            }
        }
    }
}

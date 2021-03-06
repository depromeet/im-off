package com.depromeet.tmj.im_off.service.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.depromeet.tmj.im_off.utils.DateUtils;
import com.depromeet.tmj.im_off.utils.datastore.AppPreferencesDataStore;

public class NotificationBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "NotificationBroadcastReceiver";
    private static final String EXTRA_NOTI_TYPE = "EXTRA_NOTI_TYPE";

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, NotificationBroadcastReceiver.class);
    }

    public static Intent getCallingIntent(Context context, NotificationType notificationType) {
        Intent intent = new Intent(context, NotificationBroadcastReceiver.class);

        intent.putExtra(EXTRA_NOTI_TYPE, notificationType.ordinal());
        return intent;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null && intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            NotificationAlarmManager alarmManager = new NotificationAlarmManager(context);
            alarmManager.registerAll();
        } else {
            int ordinal = intent.getIntExtra(EXTRA_NOTI_TYPE, -1);

            AppPreferencesDataStore dataStore = AppPreferencesDataStore.getInstance();
            if (ordinal != -1 && dataStore.getNotiEnable()) {
                NotificationType notificationType = NotificationType.values()[ordinal];
                NotificationNotify notificationNotify = new NotificationNotify(context);
                if (!DateUtils.isWeekend())
                    notificationNotify.sendPush(notificationType);
                new NotificationAlarmManager(context).registerAll();
            }
        }
    }
}

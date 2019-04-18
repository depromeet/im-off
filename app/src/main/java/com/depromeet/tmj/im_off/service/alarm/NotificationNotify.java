package com.depromeet.tmj.im_off.service.alarm;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.PowerManager;

import com.depromeet.tmj.im_off.MainActivity;
import com.depromeet.tmj.im_off.R;
import com.depromeet.tmj.im_off.utils.datastore.AppPreferencesDataStore;

import androidx.core.app.NotificationCompat;

public class NotificationNotify {
    private Context context;

    public NotificationNotify(Context context) {
        this.context = context;
    }

    public void sendPush(NotificationType notificationType) {
        long notificationId = System.currentTimeMillis();
        PendingIntent pendingIntent = createPendingIntent(notificationType);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        wakeUpDevice(notificationType.name());
        notificationManager.notify((int) notificationId,
                createNotificationBuilder(notificationType, pendingIntent).build());
    }

    private PendingIntent createPendingIntent(NotificationType notificationType) {
        Intent intent = new Intent(context, MainActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return PendingIntent.getActivity(context, notificationType.ordinal(), intent, PendingIntent.FLAG_ONE_SHOT);
    }

    private PendingIntent createLeavingIntent(int requestCode) {
        Intent intent = MainActivity.getCallingIntent(context, true);

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return PendingIntent.getActivity(context, requestCode, intent, PendingIntent.FLAG_ONE_SHOT);
    }

    private void wakeUpDevice(String tag) {
        PowerManager.WakeLock screenOn = ((PowerManager) context.getSystemService(Context.POWER_SERVICE))
                .newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, tag);

        screenOn.acquire(2000);
    }

    private NotificationCompat.Builder createNotificationBuilder(NotificationType notificationType,
                                                                 PendingIntent pendingIntent) {

        String title = context.getString(notificationType.getTitleRes());
        String message = context.getString(notificationType.getMessageRes());
        NotificationCompat.BigTextStyle style = new NotificationCompat.BigTextStyle();
        style.setBigContentTitle(title);
        style.bigText(message);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { // SDK version 26 이상인경우에는 알림 채널 생성
            NotificationChannel channelMessage = new NotificationChannel(AppPreferencesDataStore.CHANNEL_NOTIFICATION, "칼퇴요정", android.app.NotificationManager.IMPORTANCE_DEFAULT);

            android.app.NotificationManager notificationManager = (android.app.NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            channelMessage.setDescription("칼퇴요정");
            channelMessage.enableLights(true);
            channelMessage.enableVibration(true);
            channelMessage.setVibrationPattern(new long[]{100, 200, 100, 200});
            channelMessage.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channelMessage);
            }
        }

        PendingIntent actionIntent = createLeavingIntent(1);

        return new NotificationCompat.Builder(context, AppPreferencesDataStore.CHANNEL_NOTIFICATION)
                .setVisibility(NotificationCompat.VISIBILITY_PRIVATE)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(title)
                .addAction(R.drawable.ic_launcher_background, "퇴근!", actionIntent)
                .setContentText(message)
                .setStyle(style)
                .setColor(context.getResources().getColor(R.color.colorPrimary))
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);
    }
}

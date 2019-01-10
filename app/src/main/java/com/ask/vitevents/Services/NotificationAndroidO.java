package com.ask.vitevents.Services;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.ask.vitevents.Activities.MainActivity;
import com.ask.vitevents.R;
import com.onesignal.OneSignal;

public class NotificationAndroidO extends Service {
    public NotificationAndroidO() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //OneSignal Initialization
        OneSignal.startInit(getApplicationContext())
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

        createAndShowForegroundNotification(NotificationAndroidO.this,12);
    }

    /*@Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        onTaskRemoved(intent);
        // OneSignal Initialization
        OneSignal.startInit(getApplicationContext())
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
        Intent NotificationIntent = new Intent(this, StartActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,NotificationIntent,0);

        Notification notification = new NotificationCompat.Builder(this,CHANNEL_ID)
                .setContentTitle("TechnoVIT Updates")
                .setContentText("Check out what's new is happening this TechnoVIT")
                .setSmallIcon(R.drawable.ic_stat_onesignal_default)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(1,notification);
        return START_STICKY;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Intent restartServiceO = new Intent(getApplicationContext(),this.getClass());
        restartServiceO.setPackage(getPackageName());
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(restartServiceO);
        }
        super.onTaskRemoved(rootIntent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Intent broadcastIntent = new Intent("com.ask.atw.RestartService");
        sendBroadcast(broadcastIntent);
    }*/

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return  null;
    }

    private void createAndShowForegroundNotification(Service yourService, int notificationId) {

        Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent,0);

        final NotificationCompat.Builder builder = getNotificationBuilder(yourService,
                "CHANNEL_ID_FOREGROUND", // Channel id
                NotificationManagerCompat.IMPORTANCE_LOW); //Low importance prevent visual appearance for this notification channel on top
        builder.setOngoing(true)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentIntent(pendingIntent)
                .setContentTitle("TechnoVIT Updates")
                .setContentText("Check out what's new is happening this TechnoVIT")
                .setSmallIcon(R.drawable.ic_stat_onesignal_default)
                .build();

        Log.d("===service", "work");
        Notification notification = builder.build();

        yourService.startForeground(notificationId, notification);


    }

    public static NotificationCompat.Builder getNotificationBuilder(Context context, String channelId, int importance) {
        NotificationCompat.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            prepareChannel(context, channelId, importance);
            builder = new NotificationCompat.Builder(context, channelId);
        } else {
            builder = new NotificationCompat.Builder(context);
        }
        return builder;
    }

    @TargetApi(26)
    private static void prepareChannel(Context context, String id, int importance) {
        final String appName = context.getString(R.string.app_name);
        String description = "Keeping U Updated !";
        final NotificationManager nm = (NotificationManager) context.getSystemService(Activity.NOTIFICATION_SERVICE);

        if(nm != null) {
            NotificationChannel nChannel = nm.getNotificationChannel(id);

            if (nChannel == null) {
                nChannel = new NotificationChannel(id, appName, importance);
                nChannel.setDescription(description);
                nm.createNotificationChannel(nChannel);
            }
        }
    }

}

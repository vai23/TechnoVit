package com.ask.vitevents.Services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import com.onesignal.OneSignal;

public class NotificationService extends Service {
    public NotificationService() {
    }
    public NotificationService(Context applicationContext) {
        super();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        onTaskRemoved(intent);
        // OneSignal Initialization
        OneSignal.startInit(getApplicationContext())
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
        return START_STICKY;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Intent restartService = new Intent(getApplicationContext(),this.getClass());
        Intent restartServiceO = new Intent(getApplicationContext(),NotificationAndroidO.class);
        restartService.setPackage(getPackageName());
        restartServiceO.setPackage(getPackageName());
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            startForegroundService(restartServiceO);
        }
        else {
            startService(restartService);
        }
        super.onTaskRemoved(rootIntent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Intent broadcastIntent = new Intent("com.ask.atw.RestartService");
        sendBroadcast(broadcastIntent);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }
}

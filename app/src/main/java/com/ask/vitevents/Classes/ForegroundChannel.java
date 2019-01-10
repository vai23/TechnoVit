package com.ask.vitevents.Classes;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class ForegroundChannel extends Application {
    public static final String CHANNEL_ID = "NotificationServiceChannel";

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationchannel();
    }

    private void createNotificationchannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ){
            NotificationChannel servicechannel = new NotificationChannel(
                    CHANNEL_ID,
                    "TechnoVIT Updates",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(servicechannel);
        }
    }
}

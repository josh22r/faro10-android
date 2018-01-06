package com.kartum.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kartum.MainActivity;
import com.kartum.R;
import com.kartum.utils.ReminderServiceFunction;
import com.kartum.utils.Utils;

import java.util.Date;

public class ReminderBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        ReminderServiceFunction.AppointmentReminder res = new Gson().fromJson(intent.getStringExtra("data"), new TypeToken<ReminderServiceFunction.AppointmentReminder>() {
        }.getType());
//        intent.get
        generateNotification(context, new Intent(), "" + res.title, "" + res.message);

        Utils.restartReminders(context);
    }

    private void generateNotification(Context context, Intent notificationIntent, String title,
                                      String message) {

        int icon = R.mipmap.logo;
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        int id = Math.abs(new Date().hashCode());

        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//        PendingIntent intent = PendingIntent.getActivity(context, 0,
//                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//
        PendingIntent contentIntent =
                PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), 0);

        Notification notification = new NotificationCompat.Builder(context)
                .setContentTitle("" + title)
                .setContentText(message)
                .setSmallIcon(R.mipmap.logo)
                .setTicker("" + title)
                .setDefaults(Notification.DEFAULT_ALL)
                .setAutoCancel(true)
                .setContentIntent(contentIntent)
                .setStyle(new NotificationCompat.BigTextStyle().setBigContentTitle("" + title).bigText(message))
                .build();
        notificationManager.notify(id, notification);

    }

}

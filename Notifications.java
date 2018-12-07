package com.example.rkjc.news_app_2;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

public class Notifications {
    Context context;
    CharSequence mtitle;
    NotificationChannel notifications;
    Notification notification;

    public Notifications(Context context) {
        this.context = context;
    }

    public void showNotification(String title, String msg,Intent  intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mtitle = "News Updating";
            String mdesc = "News Updating";
            notifications = new NotificationChannel("News Update", mtitle, NotificationManager.IMPORTANCE_DEFAULT);
            notifications.setDescription(mdesc);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notifications);
        }

        PendingIntent pendingIntent = PendingIntent.getService(
                context, 0, intent, 0
        );
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        notification = builder.setSmallIcon(R.mipmap.ic_launcher).setTicker(title).
                setWhen(0).setAutoCancel(false)
                .setContentTitle(title)
                .setSmallIcon(R.drawable.ic_android)
                .setContentText(msg)
                .addAction(R.drawable.cancel, "Cancel", pendingIntent)
                .build();
        notification.flags |= Notification.FLAG_ONGOING_EVENT;
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(111, notification);
    }

    public void cancelNotification(){
        NotificationManagerCompat.from(context).cancel(111);
    }
}



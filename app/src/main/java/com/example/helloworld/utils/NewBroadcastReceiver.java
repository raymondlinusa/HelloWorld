package com.example.helloworld.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.helloworld.R;

import static android.content.Context.NOTIFICATION_SERVICE;

public class NewBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String status = getConnectivityStatus(context);
        onNotificationReceive(context, status);
        Toast.makeText(context, "Broadcast Receiver", Toast.LENGTH_LONG);
    }

    private void onNotificationReceive(Context context, String status) {
        String ID = "NOTIFIKASI";
        String judul = "notifikasi jaringan";
        NotificationChannel notificationChannel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel(ID, "My Channel", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager notifcationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            notifcationManager.createNotificationChannel(notificationChannel);
        }

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        Notification notification = new NotificationCompat.Builder(context, ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setAutoCancel(true)
                .setContentTitle(judul)
                .setContentText(status)
                .build();
        int notificationID = 1;
        notificationManager.notify(notificationID, notification);

    }

    private String getConnectivityStatus(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null){
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI){
//                return "WIFI hidup, mencari koneksi.....";
                return "WIFI mati";
            }
        }
        return "WIFI hidup, mencari koneksi.....";
//        return "WIFI mati";
    }


}

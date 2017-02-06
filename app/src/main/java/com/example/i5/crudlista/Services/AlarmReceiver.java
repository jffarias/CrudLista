package com.example.i5.crudlista.Services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.i5.crudlista.Recuerdame.MainActivity;

/**
 * Created by i5 on 11/12/2016.
 */

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // For our recurring task, we'll just display a message
        //Toast.makeText(context, "I'm running", Toast.LENGTH_SHORT).show();
        //Log.d("Eventos", "I'm running");
        // Se construye la notificación
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context.getApplicationContext())
                .setSmallIcon(android.R.drawable.stat_sys_download_done)
                .setContentTitle("Recuerdame")
                .setContentText("Tienes recuerdos pendientes");

// Crear Intent para iniciar una actividad al presionar la notificación
        Intent notificationIntent = new Intent(context.getApplicationContext(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context.getApplicationContext(), 0, notificationIntent, 0);
        builder.setContentIntent(pendingIntent);

        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(10,builder.build());
    }
}

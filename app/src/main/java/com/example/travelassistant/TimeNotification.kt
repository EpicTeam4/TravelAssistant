package com.example.travelassistant

import android.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.travelassistant.R.string

class TimeNotification : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        createNotificationChannel(context)
    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                CHANNEL_ID,
                context.getString(string.notification),
                NotificationManager.IMPORTANCE_HIGH
            )
            NotificationManagerCompat
                .from(context)
                .createNotificationChannel(notificationChannel)
            notifyNotification(context)
        } else {
            notifyNotification(context)
        }
    }

    private fun notifyNotification(context: Context) {
        with(NotificationManagerCompat.from(context)) {
            val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val notification = NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle(context.getString(string.notification))
                .setContentText(context.getString(string.notification_title))
                .setSmallIcon(R.drawable.btn_star)
                .setColor(Color.BLUE)
                .setSound(alarmSound)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build()

            notify(System.currentTimeMillis().toInt(), notification)
        }
    }

    companion object {
        const val CHANNEL_ID = "channelID"
    }
}
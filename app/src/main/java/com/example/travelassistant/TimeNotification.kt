package com.example.travelassistant

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.travelassistant.core.Constants.NOTIFICATION_ID
import javax.inject.Inject

class TimeNotification @Inject constructor(
    context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        val id = inputData.getInt(NOTIFICATION_ID, DEFAULT_VALUE)
        createNotificationChannel(id)

        return Result.success()
    }

    private fun createNotificationChannel(id: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                CHANNEL_ID,
                applicationContext.getString(R.string.notification),
                NotificationManager.IMPORTANCE_HIGH
            )
            NotificationManagerCompat
                .from(applicationContext)
                .createNotificationChannel(notificationChannel)
            notifyNotification(id)
        } else {
            notifyNotification(id)
        }
    }

    private fun notifyNotification(id: Int) {
        with(NotificationManagerCompat.from(applicationContext)) {
            val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
                .setContentTitle(applicationContext.getString(R.string.notification))
                .setContentText(applicationContext.getString(R.string.notification_title))
                .setSmallIcon(R.drawable.star_filled)
                .setColor(Color.BLUE)
                .setSound(alarmSound)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build()

            notify(id, notification)
        }
    }

    companion object {
        const val CHANNEL_ID = "channelID"
        const val DEFAULT_VALUE = 0
    }
}
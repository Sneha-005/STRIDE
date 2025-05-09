package com.example.stride.utility.composeUtility

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import androidx.core.app.NotificationCompat
import com.example.stride.R
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationHelper @Inject constructor(private val notificationManager: NotificationManager) {

    companion object {
        const val NOTIFICATION_ID = 11
        const val NOTIFICATION_CHANNEL_ID = "StepsUpdates"
    }

    fun createNotificationChannel(context: Context) {
        val notificationChannel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            context.getString(R.string.notification_channel_name),
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(notificationChannel)
    }

    fun buildNotification(context: Context, steps: Int?): Notification {
        // Tapping the notification opens the app.
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            context.packageManager.getLaunchIntentForPackage(context.packageName),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val contentText = if (steps != null) {
            context.getString(R.string.steps_count, steps)
        } else {
            context.getString(R.string.no_record)
        }

        return NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setContentTitle("")
            .setContentText(contentText)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setOngoing(true)
            .setCategory(NotificationCompat.CATEGORY_SERVICE)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setVisibility(NotificationCompat.VISIBILITY_PRIVATE)
            .setSilent(true)
            .build()
    }

    fun updateNotification(context: Context, steps: Int) {
        notificationManager.notify(NOTIFICATION_ID, buildNotification(context, steps))
    }
}

package com.example.stride.utility.broadcastReceiver


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.stride.utility.notification.NotificationService

class MyBroadcastReceiver: BroadcastReceiver() {
    companion object {
        const val NOTIFICATION_ID = "NOTIFICATION_ID"
        const val NOTIFICATION = "NOTIFICATION"
    }

    override fun onReceive(context: Context, intent: Intent?) {
        Log.e("NOTIFICATION", "BROADCAST_RECEIVER")
        val service = NotificationService(context)
        service.showNotification(false)
    }
}
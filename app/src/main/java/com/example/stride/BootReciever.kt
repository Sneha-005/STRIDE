package com.example.stride

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.stride.utility.stepCounter.StepCounterService

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            Log.d("BootCompletedReceiver", "Device rebooted, restarting StepCounterService.")
            context?.startService(Intent(context, StepCounterService::class.java))
        }
    }
}

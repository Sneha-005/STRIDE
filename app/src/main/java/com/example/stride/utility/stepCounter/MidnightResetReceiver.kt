package com.example.stride.utility.stepCounter

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.stride.data.persistence.entity.StepEntity
import com.example.stride.data.repository.StepRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class StepResetReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val sharedPreferences = context.getSharedPreferences("stepCounterPrefs", Context.MODE_PRIVATE)
        val totalSteps = sharedPreferences.getInt("totalSteps", 0)
        val previousTotalSteps = sharedPreferences.getInt("previousTotalSteps", 0)
        val lastResetDate = sharedPreferences.getString("lastSavedDate", "")

        val currentDate = getCurrentDate()
        if (lastResetDate == currentDate) {
            Log.d("StepResetReceiver", "Midnight reset already performed for today.")
            return
        }

        val previousDaySteps = totalSteps - previousTotalSteps
        Log.d("StepResetReceiver", "Previous Day Steps: $previousDaySteps")

        val repository = StepRepository(context)
        CoroutineScope(Dispatchers.IO).launch {
            repository.insertSteps(StepEntity(date = currentDate, stepCount = previousDaySteps))
            sharedPreferences.edit()
                .putInt("previousTotalSteps", totalSteps)
                .putString("lastSavedDate", currentDate)
                .apply()

            Log.d(
                "StepResetReceiver",
                "Step counter reset at midnight. New previousTotalSteps: $totalSteps"
            )
        }
        val updateIntent = Intent("com.example.stride.STEP_RESET")
        context.sendBroadcast(updateIntent)
    }

    private fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return sdf.format(Date())
    }
}

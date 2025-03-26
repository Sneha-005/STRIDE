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
import java.util.*

class MidnightResetReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null) {
            Log.e("MidnightResetReceiver", "Context is null. Midnight reset failed.")
            return
        }

        Log.d("MidnightResetReceiver", "Midnight reset triggered.")

        val sharedPreferences = context.getSharedPreferences("stepCounterPrefs", Context.MODE_PRIVATE)
        val totalSteps = sharedPreferences.getInt("totalSteps", 0)
        val previousTotalSteps = sharedPreferences.getInt("previousTotalSteps", 0)

        val previousDaySteps = totalSteps - previousTotalSteps
        Log.d("MidnightResetReceiver", "Previous Day Steps: $previousDaySteps")

        val repository = StepRepository(context)
        CoroutineScope(Dispatchers.IO).launch {
            repository.insertSteps(StepEntity(date = getCurrentDate(), stepCount = previousDaySteps))
        }

        sharedPreferences.edit()
            .putInt("previousTotalSteps", totalSteps)
            .putString("lastSavedDate", getCurrentDate())
            .apply()

        Log.d("MidnightResetReceiver", "Steps reset at midnight.")
    }

    private fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return sdf.format(Date())
    }
}

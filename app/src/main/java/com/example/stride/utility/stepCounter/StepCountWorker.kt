package com.example.stride.utility.stepCounter

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class StepCountWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {

    override fun doWork(): Result {
        val sharedPreferences =
            applicationContext.getSharedPreferences("stepCounterPrefs", Context.MODE_PRIVATE)
        val totalSteps = sharedPreferences.getInt("previousTotalSteps", 0)
        sharedPreferences.edit().putInt("previousTotalSteps", totalSteps).apply()
        Log.d("ResetStepsWorker", "Steps reset at midnight")
        return Result.success()
    }
}

package com.example.stride.utility.stepCounter

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import com.example.stride.data.persistence.entity.StepEntity
import com.example.stride.data.repository.StepRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.stride.R

class StepCounterService : Service(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var stepSensor: Sensor? = null
    private var totalSteps = 0
    private var previousTotalSteps = 0
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate() {
        super.onCreate()
        Log.d("StepCounterService", "StepCounterService started.")

        sensorManager = getSystemService(SensorManager::class.java)
        stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        if (stepSensor == null) {
            Log.e("StepCounterService", "Step Counter sensor NOT available!")
        } else {
            Log.d("StepCounterService", "Step Counter sensor registered.")
            sensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI)
        }
        startForegroundService()
        previousTotalSteps = loadPreviousSteps()
        Log.d("StepCounterService", "Previous total steps: $previousTotalSteps")
        startStepUpdates()
        scheduleMidnightReset()
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_STEP_COUNTER) {
            totalSteps = event.values[0].toInt()
            saveTotalSteps()
            Log.d("StepCounterService", "Total steps: $totalSteps")
            val dailySteps = totalSteps - previousTotalSteps
            Log.d("StepCounterService", "Daily Steps: $dailySteps (Total: $totalSteps)")

            saveStepsToDatabase(dailySteps)
        }
    }

    private fun startForegroundService() {
        val channelId = "step_counter_channel"
        val channelName = "Step Counter Service"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_LOW)
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }

        val notification: Notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("Stride Step Counter")
            .setContentText("Counting your steps...")
            .setSmallIcon(R.drawable.walk)
            .build()

        startForeground(1, notification)
    }

    private fun startStepUpdates() {
        handler.post(object : Runnable {
            override fun run() {
                saveStepsToDatabase(totalSteps)

                handler.postDelayed(this, 1000)
            }
        })
    }

    private fun saveStepsToDatabase(steps: Int) {
        val repository = StepRepository(this)

        CoroutineScope(Dispatchers.IO).launch {
            repository.insertSteps(StepEntity(date = getCurrentDate(), stepCount = steps))
        }
    }

    private fun saveTotalSteps() {
        val sharedPreferences = getSharedPreferences("stepCounterPrefs", MODE_PRIVATE)
        sharedPreferences.edit().putInt("totalSteps", totalSteps).apply()
    }

    private fun loadPreviousSteps(): Int {
        val sharedPreferences = getSharedPreferences("stepCounterPrefs", MODE_PRIVATE)
        return sharedPreferences.getInt("previousTotalSteps", totalSteps)
    }

    @SuppressLint("ScheduleExactAlarm")
    private fun scheduleMidnightReset() {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, MidnightResetReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 18)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)

            if (timeInMillis <= System.currentTimeMillis()) {
                add(Calendar.DAY_OF_MONTH, 1)
            }
        }

        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)

        Log.d("StepCounterService", "Midnight reset scheduled at ${calendar.time}")
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        super.onDestroy()
        Log.d("StepCounterService", "StepCounterService stopped.")
    }
}
fun getCurrentDate(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return sdf.format(Date())
}
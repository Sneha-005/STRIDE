package com.example.stride.data.persistence.sensor

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SensorListener @Inject constructor(
    private val sensorManager: SensorManager
) : SensorEventListener {

    private val _sensorSteps = MutableStateFlow(-1)
    val sensorSteps = _sensorSteps.asStateFlow()

    private val _isReceivingUpdates = MutableStateFlow(false)
    val isReceivingUpdates = _isReceivingUpdates.asStateFlow()

    private var lastUpdate: Long = 0
    private var delay = 1000

    fun start() {
        val sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        if (sensor == null) {
            Log.d("", "${Sensor.TYPE_STEP_COUNTER} sensor not available")
            return
        }
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        _isReceivingUpdates.value = true
    }

    fun stop() {
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            val curTime = System.currentTimeMillis()
            if (curTime - lastUpdate > delay) {
                _sensorSteps.value = event.values[0].toInt()
                lastUpdate = curTime
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }
}
package com.example.stride

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.stride.ui.theme.StrideTheme
import com.example.stride.utility.navigation.graph.RootNavGraph
import com.example.stride.utility.stepCounter.StepCounterService
import dagger.hilt.android.AndroidEntryPoint


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val requestActivityRecognitionPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            isGranted: Boolean ->
            if (isGranted) {
                Log.d("MainActivity", "ACTIVITY_RECOGNITION permission granted!")
                startStepCounterService()
            } else {
                Log.e("MainActivity", "Permission denied!")
            }
        }

    private fun checkAndRequestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            when {
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.SCHEDULE_EXACT_ALARM
                ) == PackageManager.PERMISSION_GRANTED -> {
                }
                else -> {
                    requestActivityRecognitionPermission.launch(Manifest.permission.SCHEDULE_EXACT_ALARM)
                }
            }
        }
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkAndRequestPermission()
        setContent {
            StrideTheme {
                requestActivityRecognitionPermission()
                RootNavGraph()

            }
        }
    }
    private fun requestActivityRecognitionPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACTIVITY_RECOGNITION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestActivityRecognitionPermission.launch(Manifest.permission.ACTIVITY_RECOGNITION)
            } else {
                Log.d("MainActivity", "Permission already granted!")
                startStepCounterService()
            }
        } else {
            startStepCounterService()
        }
    }

    private fun startStepCounterService() {
        val serviceIntent = Intent(this, StepCounterService::class.java)
        startService(serviceIntent)
    }

}

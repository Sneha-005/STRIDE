package com.example.stride.presentation.dashboard

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun Dashboard(
    navController: NavHostController
){
    BackHandler {
        (navController.context as? Activity)?.finishAffinity()
    }
    Text("Dashboard")
}
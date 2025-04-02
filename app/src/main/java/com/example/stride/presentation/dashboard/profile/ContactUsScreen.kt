package com.example.stride.presentation.dashboard.profile

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.stride.utility.navigation.TopBar

@Composable
fun ContactUsScreen(navController: NavController) {
    TopBar(
        onBackClick = {
            navController.navigateUp()
        },
        title = "Contact Us"
    )
}
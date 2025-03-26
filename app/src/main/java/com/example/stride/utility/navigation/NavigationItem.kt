package com.example.stride.utility.navigation

import androidx.compose.runtime.Composable

data class NavigationItem(
    val title: String,
    val route: String,
    val selectedIcon: @Composable () -> Any,
    val unSelectedIcon: @Composable () -> Any
)
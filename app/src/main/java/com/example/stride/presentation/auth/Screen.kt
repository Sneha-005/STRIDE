package com.example.stride.presentation.auth

sealed class Screen(val route: String){
    object GetStartedScreen : Screen("get_started_screen")
    object SplashScreen : Screen("splash_screen")
    object LoginScreen : Screen("login_screen")
    object SignUpScreen : Screen("signUp_screen")
}
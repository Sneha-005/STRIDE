package com.example.stride.presentation.auth

sealed class Screen(val route: String){
    object SplashLogoScreen : Screen("splash_logo_screen")
    object GetStartedScreen : Screen("get_started_screen")
    object SplashScreen : Screen("splash_screen")
    object LoginScreen : Screen("login_screen/{email}")
    object Dashboard : Screen("dashboard")
    object ResetPasswordScreen : Screen("reset_pass_screen/{email}")
    object SignUpScreen : Screen("signUp_screen")
    object OtpLoginScreen : Screen("otp_login_screen")
    object NewPasswordScreen : Screen("new_password_screen")
    object OtpSignUpScreen: Screen("otp_sign_up_screen")

}
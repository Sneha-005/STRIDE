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
    object LevelSelectorScreen : Screen("level_selector_screen")
    object GoalSelectionScreen : Screen("goal_selection_screen")
    object Questions : Screen("questions")
    object RecipeScreen : Screen("recipe_screen")
    object GoalUpdateScreen : Screen("goal_update_screen")
    object EndDashboard : Screen("end_dashboard")
    object ProfileScreen : Screen("profile_screen")
    object UpdateProfileScreen : Screen("update_profile_screen")
    object DailyGoal : Screen("daily_goal")
    object HomeScreen : Screen("home_screen")
    object DailyGoalsScreen : Screen("daily_goals_screen")
    object EmptyDietScreen : Screen("empty_diet_screen")
    object CategoryScreen : Screen("category_screen")

}
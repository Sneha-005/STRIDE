package com.example.stride.utility.navigation

object Graph {
    const val RootGraph = "root_graph"
    const val AuthGraph = "auth_graph"
    const val MainGraph = "main_graph"
    const val HomeGraph = "home_graph"
    const val ProfileGraph = "profile_graph"
    const val FitnessGraph = "fitness_graph"
    const val DietGraph = "diet_graph"
}

sealed class AuthRouteScreen(val route: String) {
    object SplashLogoScreen : AuthRouteScreen("splash_logo_screen")
    object GetStartedScreen : AuthRouteScreen("get_started_screen")
    object SplashScreen : AuthRouteScreen("splash_screen")
    object LoginScreen : AuthRouteScreen("login_screen/{email}")
    object Dashboard : AuthRouteScreen("dashboard")
    object ResetPasswordScreen : AuthRouteScreen("reset_pass_screen/{email}")
    object SignUpScreen : AuthRouteScreen("signUp_screen")
    object OtpLoginScreen : AuthRouteScreen("otp_login_screen")
    object NewPasswordScreen : AuthRouteScreen("new_password_screen")
    object OtpSignUpScreen: AuthRouteScreen("otp_sign_up_screen")
    object LevelSelectorScreen : AuthRouteScreen("level_selector_screen")
    object GoalSelectionScreen : AuthRouteScreen("goal_selection_screen")
    object Questions : AuthRouteScreen("questions")
    object EndDashboard : AuthRouteScreen("end_dashboard")
    object DailyGoalsScreen : AuthRouteScreen("daily_goals_screen")
}

sealed class MainRouteScreen(val route: String) {
    object HomeScreen : MainRouteScreen("home_screen")
    object ProfileScreen : MainRouteScreen("profile_screen")
    object EmptyDietScreen : MainRouteScreen("empty_diet_screen")
    object CategoryScreen : MainRouteScreen("category_screen")
}

sealed class FitnessRouteScreen(val route: String) {
    object CategoryScreen : FitnessRouteScreen("category_screen")
    object StrengthTrainingScreen : FitnessRouteScreen("strength_training_screen")
    object CardioScreen : FitnessRouteScreen("cardio_screen")
    object YogaScreen : FitnessRouteScreen("yoga_screen")
    object FullBodyWarmUpScreen : FitnessRouteScreen("full_body_warm_up_screen/{exerciseJson}")
    object MeditationScreen : FitnessRouteScreen("meditation_screen")
    object PerformAsanaScreen : FitnessRouteScreen("perform_asana_screen")
    object ExerciseScreen : FitnessRouteScreen("exercise_screen")
}

sealed class DietRouteScreen(val route: String) {
    object EmptyDietScreen : DietRouteScreen("empty_diet_screen")
    object DailyGoal : DietRouteScreen("daily_goal")
    object LogFoodScreen : DietRouteScreen("log_food_screen")
    object RecipeScreen : DietRouteScreen("recipe_screen")
    object RecipeDetailScreen : DietRouteScreen("recipe_detail_screen")
    object DietRecordScreen : DietRouteScreen("diet_record_screen")
}

sealed class ProfileRouteScreen(val route: String) {
    object ProfileScreen : ProfileRouteScreen("profile_screen")
    object UpdateProfileScreen : ProfileRouteScreen("update_profile_screen")
    object GoalUpdateScreen : ProfileRouteScreen("goal_update_screen")
    object LevelUpdateScreen : ProfileRouteScreen("level_update_screen")
    object AchievementsScreen : ProfileRouteScreen("achievements_screen")
    object PrivacyPolicyScreen : ProfileRouteScreen("privacy_policy_screen")
    object ContactUsScreen : ProfileRouteScreen("contact_us_screen")
}

sealed class HomeRouteScreen(val route: String) {
    object HomeScreen : HomeRouteScreen("home_screen")
    object StepsIntoActionScreen : HomeRouteScreen("steps_into_action_screen")
    object FullBodyWarmUpScreen : HomeRouteScreen("full_body_warm_up_screen")
}
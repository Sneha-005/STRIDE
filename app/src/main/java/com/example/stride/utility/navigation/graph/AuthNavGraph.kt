package com.example.stride.utility.navigation.graph

import android.content.res.Configuration
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.stride.R
import com.example.stride.data.local.DataStoreRepository
import com.example.stride.utility.navigation.TopBar
import com.example.stride.presentation.auth.getStarted.GetStartedScreen
import com.example.stride.presentation.auth.getStarted.GetStartedViewModel
import com.example.stride.presentation.auth.loginScreen.LoginScreen
import com.example.stride.presentation.auth.loginScreen.LoginViewModel
import com.example.stride.presentation.auth.newPassword.NewPasswordScreen
import com.example.stride.presentation.auth.newPassword.NewPasswordViewModel
import com.example.stride.presentation.auth.otpLoginScreen.OtpLoginScreen
import com.example.stride.presentation.auth.otpLoginScreen.OtpLoginViewModel
import com.example.stride.presentation.auth.otpSignUpScreen.OtpSignUpScreen
import com.example.stride.presentation.auth.otpSignUpScreen.OtpSignUpViewModel
import com.example.stride.presentation.auth.resetPassword.ResetPasswordScreen
import com.example.stride.presentation.auth.resetPassword.ResetPasswordViewModel
import com.example.stride.presentation.auth.signUpScreen.SignUpViewModel
import com.example.stride.presentation.auth.signUpScreen.SignupScreen
import com.example.stride.presentation.auth.splashScreen.SplashScreen
import com.example.stride.presentation.auth.splashScreen.SplashScreenLandscape
import com.example.stride.presentation.dashboard.landingPage.DailyGoalsScreen
import com.example.stride.presentation.dashboard.landingPage.Dashboard
import com.example.stride.presentation.dashboard.landingPage.EndDashboard
import com.example.stride.presentation.dashboard.landingPage.LandingViewModel
import com.example.stride.presentation.dashboard.landingPage.LevelSelectorScreen
import com.example.stride.presentation.dashboard.landingPage.Questions
import com.example.stride.presentation.dashboard.landingPage.goalSelection.GoalSelectionScreen
import com.example.stride.utility.navigation.AuthRouteScreen
import com.example.stride.utility.navigation.Graph
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import kotlinx.coroutines.delay


fun NavGraphBuilder.authNavGraph(rootNavController: NavHostController){

    navigation(
        route = Graph.AuthGraph,
        startDestination = AuthRouteScreen.SplashLogoScreen.route
    ){

        composable(AuthRouteScreen.SplashLogoScreen.route) {
            val context = LocalContext.current
            val dataStoreRepository = DataStoreRepository(context)
            Box(
                modifier = Modifier
                    .background(colorResource(id = R.color.background_color))
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo_name),
                    contentDescription = "Splash Logo"
                )
            }

            LaunchedEffect(true) {
                delay(3000)
                Log.d("NavigationCheck", "LaunchedEffect started!")
                val token = dataStoreRepository.readToken()
                val account = GoogleSignIn.getLastSignedInAccount(context)

                Log.d("token", "Token: $token, Google Account: $account")

                if (token.isNullOrEmpty() && account == null) {
                    rootNavController.navigate("splash_screen")
                } else {
                    rootNavController.navigate(Graph.MainGraph) {
                        popUpTo(AuthRouteScreen.SplashLogoScreen.route) {
                            inclusive = true
                        }
                    }
                }
            }

        }

        composable(AuthRouteScreen.SplashScreen.route) {
            val configuration = LocalConfiguration.current
            Column {
                if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    SplashScreenLandscape(rootNavController)
                } else {
                    SplashScreen(rootNavController)
                }
            }
        }
        composable(AuthRouteScreen.GetStartedScreen.route) {
            val context = LocalContext.current
            val viewModel: GetStartedViewModel = hiltViewModel()
            val state by viewModel.uiStates.collectAsStateWithLifecycle()

            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(@string/server_client_id))
                .build()

            val googleSignInClient = GoogleSignIn.getClient(context, gso)

            fun handleSignInResult(task: com.google.android.gms.tasks.Task<GoogleSignInAccount>) {
                try {
                    val account = task.getResult(ApiException::class.java)
                    val idToken = account?.idToken

                    if (idToken != null) {
                        Log.d("GoogleSignIn", "ID Token: $idToken")
                        viewModel.onContinueWithGoogle(account, rootNavController)
                    } else {
                        Log.e("GoogleSignIn", "ID Token is null")
                    }
                } catch (e: ApiException) {
                    Log.e("GoogleSignIn", "Sign-In failed with error code: ${e.statusCode}")
                }
            }

            val signInLauncher =
                rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                    Log.d("GoogleSignIn", "Result received: ${result.resultCode}")

                    val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                    handleSignInResult(task)
                }
            LaunchedEffect(state.isSignInSuccessful) {
                if (state.isSignInSuccessful) {
                    Toast.makeText(context, "Sign-in successful", Toast.LENGTH_LONG).show()
                    rootNavController.navigate("dashboard")
                    viewModel.resetState()
                }
            }
            Column {
                GetStartedScreen(
                    uiStates = state,
                    onContinueClick = { viewModel.onContinueClick(rootNavController) },
                    onContinueWithGoogleClick = {
                        signInLauncher.launch(googleSignInClient.signInIntent)
                    },
                    setEmail = viewModel::setEmail,
                    navController = rootNavController
                )
            }
        }

        composable("login_screen/{email}") { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            val viewModel: LoginViewModel = hiltViewModel()
            val state by viewModel.uiStates.collectAsStateWithLifecycle()
            Column {
                TopBar(onBackClick = { rootNavController.popBackStack() }, title = "Back")
                LoginScreen(
                    uiStates = state,
                    onLoginClick = { viewModel.onLoginClick(rootNavController) },
                    setEmail = viewModel::setEmail,
                    setPassword = viewModel::setPassword,
                    loginSuccess = {},
                    onForgotPasswordClick = { viewModel.onForgotPasswordClick(rootNavController) },
                    initialEmail = email
                )
            }
        }
        composable("reset_pass_screen/{email}") { backStackEntry ->
            val resetPasswordViewModel: ResetPasswordViewModel = hiltViewModel()
            val email = backStackEntry.arguments?.getString("email") ?: ""
            val state by resetPasswordViewModel.uiStates.collectAsStateWithLifecycle()
            Column {
                TopBar(onBackClick = { rootNavController.popBackStack() }, title = "Back")
                ResetPasswordScreen(
                    uiStates = state,
                    onVerificationCodeClick = {
                        resetPasswordViewModel.onVerificationCodeClick(
                            rootNavController
                        )
                    },
                    setEmail = resetPasswordViewModel::setEmail,
                    stateSuccess = {},
                    setForgotPasswordState = resetPasswordViewModel::setForgotPasswordState,
                    initialEmail = email
                )
            }
        }

        composable("otp_login_screen") {
            val resetPasswordViewModel: ResetPasswordViewModel = hiltViewModel()
            val viewModel: OtpLoginViewModel = hiltViewModel()
            val state by viewModel.uiStates.collectAsStateWithLifecycle()
            Column {
                TopBar(onBackClick = { rootNavController.popBackStack() }, title = "Back")
                OtpLoginScreen(
                    uiStates = state,
                    onOtpChange = {},
                    onVerifyClick = {
                        viewModel.onVerifyClick(
                            viewModel.uiStates.value.otp,
                            rootNavController
                        )
                    },
                    onResendOtpClick = {
                        resetPasswordViewModel.onVerificationCodeClick(
                            rootNavController
                        )
                    },
                    setOtp = viewModel::setOtp
                )
            }
        }

        composable("new_password_screen") {
            val viewModel: NewPasswordViewModel = hiltViewModel()
            val state by viewModel.uiStates.collectAsStateWithLifecycle()
            Column {
                TopBar(onBackClick = { rootNavController.popBackStack() }, title = "Back")
                NewPasswordScreen(
                    uiStates = state,
                    setConfirmPassword = viewModel::setConfirmPassword,
                    setPassword = viewModel::setPassword,
                    onSavePasswordClick = { viewModel.onSavePasswordCLick(rootNavController) },
                    calculatePasswordStrength = viewModel::calculatePasswordStrength
                )
            }
        }

        composable("sign_up_screen/{email}") { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            val viewModel: SignUpViewModel = hiltViewModel()
            val state by viewModel.uiStates.collectAsStateWithLifecycle()
            Column {
                TopBar(onBackClick = { rootNavController.popBackStack() }, title = "Back")
                SignupScreen(
                    uiStates = state,
                    onSignUpClick = { viewModel.onSignupClick(rootNavController) },
                    setEmail = viewModel::setEmail,
                    setName = viewModel::setName,
                    setPassword = viewModel::setPassword,
                    setConfirmPassword = viewModel::setConfirmPassword,
                    onSignupComplete = {},
                    setRegistrationState = {},
                    initialEmail = email,
                    calculatePasswordStrength = viewModel::calculatePasswordStrength
                )
            }
        }

        composable("otp_sign_up_screen") {
            val resetPasswordViewModel: ResetPasswordViewModel = hiltViewModel()
            val viewModel: OtpSignUpViewModel = hiltViewModel()
            val state by viewModel.uiStates.collectAsStateWithLifecycle()
            Column {
                TopBar(onBackClick = { rootNavController.popBackStack() }, title = "Back")
                OtpSignUpScreen(
                    uiStates = state,
                    onOtpChange = {},
                    onVerifyClick = {
                        viewModel.onVerifyClick(
                            viewModel.uiStates.value.otp,
                            rootNavController
                        )
                    },
                    onResendOtpClick = {
                        resetPasswordViewModel.onVerificationCodeClick(
                            rootNavController
                        )
                    },
                    setOtp = viewModel::setOtp
                )
            }

        }
        composable("dashboard") {
            Dashboard(
                navController = rootNavController,
            )
        }
        composable("goal_selection_screen") {
            val viewModel: LandingViewModel =
                hiltViewModel(rootNavController.getBackStackEntry("questions"))

            Column {
                TopBar(onBackClick = { rootNavController.popBackStack() }, title = "Back")
                GoalSelectionScreen(
                    setFitnessGoal = viewModel::setFitnessGoal,
                    navController = rootNavController
                )
            }
        }
        composable("level_selector_screen") {
            val viewModel: LandingViewModel =
                hiltViewModel(rootNavController.getBackStackEntry("questions"))

            Column {
                TopBar(onBackClick = { rootNavController.popBackStack() }, title = "Back")
                LevelSelectorScreen(
                    setLevelGoal = viewModel::setActivityLevel,
                    navController = rootNavController
                )
            }
        }
        composable("questions") {
            val viewModel: LandingViewModel = hiltViewModel()
            Column {
                TopBar(onBackClick = { rootNavController.popBackStack() }, title = "Back")
                Questions(
                    setGender = viewModel::setGender,
                    setAge = viewModel::setAge,
                    setHeight = viewModel::setHeight,
                    setWeight = viewModel::setWeight,
                    setDietType = viewModel::setDietType,
                    navController = rootNavController
                )
            }
        }
        composable("daily_goals_screen") {
            val viewModel: LandingViewModel =
                hiltViewModel(rootNavController.getBackStackEntry("questions"))

            Column {
                TopBar(onBackClick = { rootNavController.popBackStack() }, title = "Back")
                DailyGoalsScreen(
                    setStepsGoals = viewModel::setStepGoals,
                    setCalorieGoals = viewModel::setCalorieGoals,
                    setWaterIntakeGoals = viewModel::setWaterIntakeGoals,
                    navController = rootNavController
                )
            }
        }
        composable("end_dashboard") {
            val context = rootNavController.context
            val viewModel: LandingViewModel =
                hiltViewModel(rootNavController.getBackStackEntry("questions"))

            Column {
                TopBar(onBackClick = { rootNavController.popBackStack() }, title = "Back")
                EndDashboard(
                    navController = rootNavController,
                    onLandingClick = { viewModel.onLandingClick(rootNavController,context) }
                )
            }
        }

    }
}
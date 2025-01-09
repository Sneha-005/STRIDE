package com.example.stride.utility.navigation

import android.app.Activity.RESULT_OK
import android.content.res.Configuration
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.identity.Identity
import com.example.stride.presentation.auth.Screen
import com.example.stride.presentation.auth.getStarted.GetStartedScreen
import com.example.stride.presentation.auth.getStarted.GetStartedViewModel
import com.example.stride.presentation.auth.loginScreen.LoginScreen
import com.example.stride.presentation.auth.loginScreen.LoginViewModel
import com.example.stride.presentation.auth.newPassword.NewPasswordScreen
import com.example.stride.presentation.auth.newPassword.NewPasswordViewModel
import com.example.stride.presentation.auth.otpLoginScreen.OtpLoginScreen
import com.example.stride.presentation.auth.otpLoginScreen.OtpLoginViewModel
import com.example.stride.presentation.auth.resetPassword.ResetPasswordScreen
import com.example.stride.presentation.auth.resetPassword.ResetPasswordViewModel
import com.example.stride.presentation.auth.splashScreen.SplashScreen
import com.example.stride.presentation.auth.splashScreen.SplashScreenLandscape
import com.example.stride.presentation.dashboard.Dashboard
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import com.example.stride.R
import com.example.stride.presentation.auth.getStarted.GoogleAuthUiClient
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val configuration = LocalConfiguration.current
    val context = LocalContext.current
    val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = context,
            oneTapClient = Identity.getSignInClient(context)
        )
    }
    val coroutineScope = rememberCoroutineScope()

    NavHost(navController = navController, startDestination = "splash_logo_screen") {
        composable("splash_logo_screen"){
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
            LaunchedEffect(Unit) {
                delay(3000)
                if (googleAuthUiClient.getSignedInUser() != null) {
                    navController.navigate("dashboard")
                }else {
                    navController.navigate("splash_screen")
                }
            }
        }
        composable("splash_screen") {

            if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                SplashScreenLandscape(navController)
            } else {
                SplashScreen(navController)
            }
        }
        composable("get_started_screen") {
            val viewModel: GetStartedViewModel = hiltViewModel()
            val state by viewModel.uiStates.collectAsStateWithLifecycle()

            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartIntentSenderForResult(),
                onResult = { result ->
                    if (result.resultCode == RESULT_OK) {
                        coroutineScope.launch {
                            val signInResult = googleAuthUiClient.signInWithIntent(
                                intent = result.data ?: return@launch
                            )
                            viewModel.onSignInResult(signInResult, navController)
                        }
                    }
                }
            )

            LaunchedEffect(key1 = state.isSignInSuccessful) {
                if (state.isSignInSuccessful) {
                    Toast.makeText(
                        context,
                        "Sign in successful",
                        Toast.LENGTH_LONG
                    ).show()

                    navController.navigate("dashboard")
                    viewModel.resetState()
                }
            }

            GetStartedScreen(
                uiStates = state,
                onContinueClick = { viewModel.onContinueClick(navController) },
                onContinueWithGoogleClick = { viewModel.onContinueWithGoogleClick(launcher) },
                setEmail = viewModel::setEmail,
                navController = navController
            )
        }
        composable("login_screen/{email}") { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            val viewModel: LoginViewModel = hiltViewModel()
            val state by viewModel.uiStates.collectAsStateWithLifecycle()
            LoginScreen(
                uiStates = state,
                onLoginClick = { viewModel.onLoginClick(navController) },
                setEmail = viewModel::setEmail,
                setPassword = viewModel::setPassword,
                loginSuccess = {},
                onForgotPasswordClick = { viewModel.onForgotPasswordClick(navController) },
                initialEmail = email
            )
        }
        composable("reset_pass_screen/{email}") { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            val viewModel: ResetPasswordViewModel = hiltViewModel()
            val state by viewModel.uiStates.collectAsStateWithLifecycle()
            ResetPasswordScreen(
                uiStates = state,
                onVerificationCodeClick = { viewModel.onVerificationCodeClick(navController) },
                setEmail = viewModel::setEmail,
                stateSuccess = {},
                setForgotPasswordState = viewModel::setForgotPasswordState,
                initialEmail = email
            )
        }

        composable("otp_login_screen") {
            val resetPasswordViewModel: ResetPasswordViewModel = hiltViewModel()
            val viewModel: OtpLoginViewModel = hiltViewModel()
            val state by viewModel.uiStates.collectAsStateWithLifecycle()
            OtpLoginScreen(
                uiStates = state,
                onOtpChange = {},
                onVerifyClick = { viewModel.onVerifyClick(viewModel.uiStates.value.otp, navController) },
                onResendOtpClick = { resetPasswordViewModel.onVerificationCodeClick(navController) },
                setOtp = viewModel::setOtp
            )
        }

        composable("new_password_screen") {
            val viewModel: NewPasswordViewModel = hiltViewModel()
            val state by viewModel.uiStates.collectAsStateWithLifecycle()
            NewPasswordScreen(
                uiStates = state,
                setConfirmPassword = viewModel::setConfirmPassword,
                setPassword = viewModel::setPassword,
                onSavePasswordClick = { viewModel.onSavePasswordCLick(navController) },
                calculatePasswordStrength = viewModel::calculatePasswordStrength
            )
        }
        composable("dashboard") {
            Dashboard(navController)
        }
    }
}
package com.example.stride.utility.navigation

import android.app.Activity
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
import androidx.core.app.ActivityCompat.startActivityForResult
import com.example.stride.R
import com.example.stride.data.local.DataStoreRepository
import com.example.stride.presentation.auth.TopBar
import com.example.stride.presentation.auth.otpSignUpScreen.OtpSignUpScreen
import com.example.stride.presentation.auth.otpSignUpScreen.OtpSignUpViewModel
import com.example.stride.presentation.auth.signUpScreen.SignUpViewModel
import com.example.stride.presentation.auth.signUpScreen.SignupScreen
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import kotlinx.coroutines.delay

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val configuration = LocalConfiguration.current
    val context = LocalContext.current
    val dataStoreRepository = DataStoreRepository(context)

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
                val token = dataStoreRepository.readToken()
                if (token.isNullOrEmpty()) {
                    navController.navigate("splash_screen")
                } else {
                    navController.navigate("dashboard")
                }
            }
        }
        composable("splash_screen") {

            Column {
                if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    SplashScreenLandscape(navController)
                } else {
                    SplashScreen(navController)
                }
            }
        }
        composable("get_started_screen") {
            val viewModel: GetStartedViewModel = hiltViewModel()
            val state by viewModel.uiStates.collectAsStateWithLifecycle()

            val googleSignInClient = remember {
                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .requestProfile()
                    .requestIdToken("1003392708014-8g4i9b96kdq475anvkui9oru8nuktopk.apps.googleusercontent.com")
                    .build()
              GoogleSignIn.getClient(context, gso)
            }


            val signInLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                Log.d("GoogleSignIn", "Result received: ${result.resultCode}")

                if (result.resultCode == Activity.RESULT_OK) {
                    val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                    try {
                        val account = task.getResult(ApiException::class.java)
                        Log.d("GoogleSignIn", "Account selected: ${account.email}")
                        Log.d("GoogleSignIn", "Account selected: ${account.id}")
                        Log.d("GoogleSignIn", "Account selected: ${account.getIdToken()}")
                        Log.d("GoogleSignIn", "Account selected: ${account.displayName}")
                        Log.d("GoogleSignIn", "Account selected: ${account.photoUrl}")
                        viewModel.onContinueWithGoogle(account,navController)
                    } catch (e: ApiException) {
                        Log.w("GoogleSignIn", "signInResult:failed code=" + e.statusCode)
                    }
                }
            }

            LaunchedEffect(state.isSignInSuccessful) {
                if (state.isSignInSuccessful) {
                    Toast.makeText(context, "Sign-in successful", Toast.LENGTH_LONG).show()
                    navController.navigate("dashboard")
                    viewModel.resetState()
                }
            }
            Column {
                GetStartedScreen(
                    uiStates = state,
                    onContinueClick = { viewModel.onContinueClick(navController) },
                    onContinueWithGoogleClick = {
                        signInLauncher.launch(googleSignInClient.signInIntent)
                    },
                    setEmail = viewModel::setEmail,
                    navController = navController
                )
            }

        }
        composable("login_screen/{email}") { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            val viewModel: LoginViewModel = hiltViewModel()
            val state by viewModel.uiStates.collectAsStateWithLifecycle()
            Column {
                TopBar (onBackClick = { navController.popBackStack() })
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

        }
        composable("reset_pass_screen/{email}") { backStackEntry ->
            val resetPasswordViewModel: ResetPasswordViewModel = hiltViewModel()
            val email = backStackEntry.arguments?.getString("email") ?: ""
            val state by resetPasswordViewModel.uiStates.collectAsStateWithLifecycle()
            Column {
                TopBar { navController.popBackStack() }
                ResetPasswordScreen(
                    uiStates = state,
                    onVerificationCodeClick = { resetPasswordViewModel.onVerificationCodeClick(navController) },
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
                TopBar { navController.popBackStack() }
                OtpLoginScreen(
                    uiStates = state,
                    onOtpChange = {},
                    onVerifyClick = { viewModel.onVerifyClick(viewModel.uiStates.value.otp, navController) },
                    onResendOtpClick = { resetPasswordViewModel.onVerificationCodeClick(navController) },
                    setOtp = viewModel::setOtp
                )
            }
        }

        composable("new_password_screen") {
            val viewModel: NewPasswordViewModel = hiltViewModel()
            val state by viewModel.uiStates.collectAsStateWithLifecycle()
            Column {
                TopBar (onBackClick = { navController.popBackStack() })
                NewPasswordScreen(
                    uiStates = state,
                    setConfirmPassword = viewModel::setConfirmPassword,
                    setPassword = viewModel::setPassword,
                    onSavePasswordClick = { viewModel.onSavePasswordCLick(navController) },
                    calculatePasswordStrength = viewModel::calculatePasswordStrength
                )
            }
        }

        composable("sign_up_screen/{email}") { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            val viewModel: SignUpViewModel = hiltViewModel()
            val state by viewModel.uiStates.collectAsStateWithLifecycle()
            Column {
                TopBar (onBackClick = { navController.popBackStack() })
                SignupScreen(
                    uiStates = state,
                    onSignUpClick = {viewModel.onSignupClick(navController)},
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
                TopBar { navController.popBackStack() }
                OtpSignUpScreen(
                    uiStates = state,
                    onOtpChange = {},
                    onVerifyClick = { viewModel.onVerifyClick(viewModel.uiStates.value.otp, navController) },
                    onResendOtpClick = { resetPasswordViewModel.onVerificationCodeClick(navController) },
                    setOtp = viewModel::setOtp
                )
            }
        }
        composable("dashboard") {
            Dashboard(navController)
        }
    }
}
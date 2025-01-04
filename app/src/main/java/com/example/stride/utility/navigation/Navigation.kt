package com.example.stride.utility.navigation

import android.app.Activity.RESULT_OK
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.stride.presentation.auth.Screen
import com.example.stride.presentation.auth.getStarted.GetStartedScreen
import com.example.stride.presentation.auth.getStarted.GetStartedViewModel
import com.example.stride.presentation.auth.loginScreen.LoginScreen
import com.example.stride.presentation.auth.splashScreen.SplashScreen
import kotlinx.coroutines.launch

@Composable
fun Navigation(
    //googleAuthUiClient: GoogleAuthUiClient
    ) {
    val viewModel: GetStartedViewModel = hiltViewModel()
    val state by viewModel.uiStates.collectAsStateWithLifecycle()
    val navController = rememberNavController()
   /* val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = { result ->
            if (result.resultCode == RESULT_OK) {
                viewModel.viewModelScope.launch {
                    val signInResult = googleAuthUiClient.signInWithIntent(
                        intent = result.data ?: return@launch
                    )
                    viewModel.onSignInResult(signInResult)
                }
            }
        }
    )*/

  /*  LaunchedEffect(key1 = state.isSignInSuccessful) {
        if (state.isSignInSuccessful) {
            Toast.makeText(
                googleAuthUiClient.context,
                "Sign in successful",
                Toast.LENGTH_LONG
            ).show()

            navController.navigate("profile")
            viewModel.resetState()
        }
    }
*/
    NavHost(navController = navController, startDestination = Screen.SplashScreen.route) {
        composable(Screen.SplashScreen.route) {
            SplashScreen(navController)
        }
        composable(Screen.GetStartedScreen.route) {
            GetStartedScreen(
                uiStates = state,
                onContinueClick = {viewModel.onContinueClick(navController)},
                onContinueWithGoogleClick = {},
               // onContinueWithGoogleClick = { viewModel.onContinueWithGoogleClick(launcher) },
                setEmail = viewModel::setEmail,
                navController = navController
            )
        }
        composable(Screen.LoginScreen.route) {
            LoginScreen()
        }
    }
}
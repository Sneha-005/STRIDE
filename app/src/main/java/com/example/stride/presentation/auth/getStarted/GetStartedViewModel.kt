package com.example.stride.presentation.auth.getStarted

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.stride.data.local.DataStoreRepository
import com.example.stride.data.remote.dto.OauthDto
import com.example.stride.data.remote.dto.isRegistered
import com.example.stride.domain.models.OauthRequest
import com.example.stride.domain.models.UserModel
import com.example.stride.domain.repository.ApiServicesRepository
import com.example.stride.domain.sharedModels.UserRepository
import com.example.stride.domain.usecases.PostRegisterUser
import com.example.stride.presentation.auth.Screen
import com.example.stride.utility.composeUtility.isValidEmail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

data class GetStartedStates(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null,
    val email: String? = "",
    val isLoading: Boolean = false,
    val isEmailValid: Boolean? = true,
    val errorEmailMessage: String = "",
    val registrationState: Boolean = false
)

@HiltViewModel
class GetStartedViewModel @Inject constructor(
    private val googleAuthUiClient: GoogleAuthUiClient,
    private val dataStoreRepository: DataStoreRepository,
    private val postRegisterUser: PostRegisterUser,
    private val apiServicesRepository: ApiServicesRepository,
    private val apiKey: String,
    private val userRepository: UserRepository
): ViewModel() {
    private val _uiStates = MutableStateFlow(GetStartedStates())
    val uiStates: StateFlow<GetStartedStates> = _uiStates.asStateFlow()

    fun setEmail(email: String) {
        _uiStates.value = _uiStates.value.copy(email = email)
    }

    fun setRegistrationState(registrationState: Boolean) {
        _uiStates.value = _uiStates.value.copy(registrationState = registrationState)
    }

    fun onContinueClick(navController: NavHostController) {
        if (_uiStates.value.email?.isValidEmail() == true) {
            _uiStates.value = _uiStates.value.copy(isEmailValid = true, isLoading = true)

            viewModelScope.launch(Dispatchers.IO) {
                Log.d("API_CALL", "Making API call with email: ${_uiStates.value.email}")

                val call = apiServicesRepository.registerUser(_uiStates.value.email ?: "")
                call.enqueue(object : Callback<isRegistered> {
                    @SuppressLint("TimberArgCount")
                    override fun onResponse(call: Call<isRegistered>, response: Response<isRegistered>) {
                        _uiStates.value = _uiStates.value.copy(isLoading = false)
                        if (response.isSuccessful) {
                            val message = response.body()?.message
                            userRepository.setValues(UserModel(email = _uiStates.value.email))
                            _uiStates.value = _uiStates.value.copy(registrationState = true)
                            when (message) {
                                "Go to login" -> navController.navigate("login_screen/${_uiStates.value.email}")
                                "Go to SignUp" -> navController.navigate("signUp_screen")
                                else -> _uiStates.value = _uiStates.value.copy(isEmailValid = false, errorEmailMessage = "Invalid Email")
                            }
                        } else {
                            Log.e("API_CALL", "Error: Response Code: ${response.code()}")
                            try {
                                val errorBody = response.errorBody()?.string()
                                Log.e("API_CALL", "Error: $errorBody")
                            } catch (e: Exception) {
                                Log.e("API_CALL", "Error: Unable to parse error body")
                            }
                            _uiStates.value = _uiStates.value.copy(isEmailValid = false, errorEmailMessage = "Registration failed")
                        }
                    }

                    override fun onFailure(call: Call<isRegistered>, t: Throwable) {
                        _uiStates.value = _uiStates.value.copy(isLoading = false)
                        Log.e("API_CALL", "Error: ${t.localizedMessage}")
                    }
                })
            }
        } else {
            _uiStates.value = _uiStates.value.copy(isEmailValid = false, errorEmailMessage = "Invalid Email")
        }
    }

    fun onContinueWithGoogleClick(launcher: ActivityResultLauncher<IntentSenderRequest>) {
        viewModelScope.launch {
            val signInIntentSender = googleAuthUiClient.signIn()
            launcher.launch(IntentSenderRequest.Builder(signInIntentSender ?: return@launch).build())
        }
    }

    fun onSignInResult(result: SignInResult, navController: NavHostController) {
        if (result.data != null) {
            val email = result.data?.email
            _uiStates.update { it.copy(isLoading = true) }

            viewModelScope.launch(Dispatchers.IO) {
                val call = apiServicesRepository.oauth(OauthRequest(email = email ?: "").toString())
                Log.d("email", "$email")
                call.enqueue(object : Callback<OauthDto> {
                    override fun onResponse(
                        call: Call<OauthDto>,
                        response: Response<OauthDto>
                    ) {
                        _uiStates.update { it.copy(isLoading = false) }

                        if (response.isSuccessful) {
                            val message = response.body()?.message
                            userRepository.setValues(UserModel(email = response.body()?.user?.email ?: ""))
                            navController.navigate("dashboard")
                        } else {
                            Log.e("GOOGLE_API", "Error: Response Code: ${response.code()}")
                            Log.d("resp", "${response.body()}")

                            _uiStates.update { it.copy(isEmailValid = false, errorEmailMessage = "Google sign-in failed") }
                        }
                    }

                    override fun onFailure(call: Call<OauthDto>, t: Throwable) {
                        Log.e("GOOGLE_API", "Failure: ${t.localizedMessage}")
                        _uiStates.update { it.copy(isLoading = false, isEmailValid = false, errorEmailMessage = "Google sign-in error") }
                    }
                })
            }
        } else {
            _uiStates.update { it.copy(isSignInSuccessful = false, signInError = result.errorMessage) }
        }
    }

    fun resetState() {
        _uiStates.update { GetStartedStates() }
    }
}
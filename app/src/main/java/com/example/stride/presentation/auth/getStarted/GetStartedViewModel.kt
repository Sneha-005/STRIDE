package com.example.stride.presentation.auth.getStarted

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.stride.data.local.DataStoreRepository
import com.example.stride.data.remote.dto.OauthDto
import com.example.stride.data.remote.dto.isRegistered
import com.example.stride.domain.models.UserModel
import com.example.stride.domain.repository.ApiServicesRepository
import com.example.stride.domain.sharedModels.UserRepository
import com.example.stride.utility.composeUtility.isValidEmail
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
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
    private val dataStoreRepository: DataStoreRepository,
    private val apiServicesRepository: ApiServicesRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiStates = MutableStateFlow(GetStartedStates())
    val uiStates: StateFlow<GetStartedStates> = _uiStates.asStateFlow()

    fun setEmail(email: String) {
        _uiStates.value = _uiStates.value.copy(email = email)
    }

    fun setRegistrationState(registrationState: Boolean) {
        _uiStates.value = _uiStates.value.copy(registrationState = registrationState)
    }

    fun onContinueClick(navController: NavHostController) {
        val email = _uiStates.value.email

        if (email?.isValidEmail() == true) {
            _uiStates.value = _uiStates.value.copy(isEmailValid = true, isLoading = true)
            viewModelScope.launch(Dispatchers.IO) {
                val call = apiServicesRepository.registerUser(email)
                call.enqueue(object : Callback<isRegistered> {
                    override fun onResponse(call: Call<isRegistered>, response: Response<isRegistered>) {
                        _uiStates.value = _uiStates.value.copy(isLoading = false)

                        if (response.isSuccessful) {
                            val message = response.body()?.message
                            userRepository.setValues(UserModel(email = email))

                            when (message) {
                                "Go to login" -> navController.navigate("login_screen/$email")
                                "Go to SignUP" -> navController.navigate("sign_up_screen/$email")
                                else -> _uiStates.value = _uiStates.value.copy(
                                    isEmailValid = false,
                                    errorEmailMessage = "Invalid Email"
                                )
                            }
                        } else {
                            Log.e("API_CALL", "Error: Response Code: ${response.code()}")
                            _uiStates.value = _uiStates.value.copy(
                                isEmailValid = false,
                                errorEmailMessage = "Registration failed"
                            )
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

    fun onContinueWithGoogle(account: GoogleSignInAccount, navController: NavHostController) {
        val idToken = account.idToken
        Log.d("GoogleSignIn", "ID Token: $idToken")
        if (idToken != null) {
            _uiStates.value = _uiStates.value.copy(isLoading = true)

            viewModelScope.launch(Dispatchers.IO) {
                val call = apiServicesRepository.oauth(idToken)
                call.enqueue(object : Callback<OauthDto> {
                    override fun onResponse(call: Call<OauthDto>, response: Response<OauthDto>) {
                        _uiStates.value = _uiStates.value.copy(isLoading = false)

                        if (response.isSuccessful) {
                            val message = response.body()?.message
                            val token = response.body()?.token
                            viewModelScope.launch {
                                dataStoreRepository.saveOauth(oauth = token.toString())
                            }
                            token?.let { UserModel(email = account.email, token = it) }
                                ?.let { userRepository.setValues(it) }
                            Log.d("API_CALL", "Token: $token")
                            Log.d("API_CALL", "Response: $message")
                            navController.navigate("dashboard")
                        } else {
                            Log.e("API_CALL", "Error: Response Code: ${response.code()}")
                            _uiStates.value = _uiStates.value.copy(
                                isEmailValid = false,
                                errorEmailMessage = "Google Sign-In authentication failed"
                            )
                        }
                    }

                    override fun onFailure(call: Call<OauthDto>, t: Throwable) {
                        _uiStates.value = _uiStates.value.copy(isLoading = false)
                        Log.e("API_CALL", "Error: ${t.localizedMessage}")
                    }
                })
            }
        } else {
            Log.e("GoogleSignIn", "Error: No ID Token found.")
            _uiStates.value = _uiStates.value.copy(
                isEmailValid = false,
                errorEmailMessage = "Google Sign-In failed"
            )
        }
    }


    fun resetState() {
        _uiStates.update { GetStartedStates() }
    }
}
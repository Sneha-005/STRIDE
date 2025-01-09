package com.example.stride.presentation.auth.resetPassword

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.stride.data.remote.dto.ResetPasswordDto
import com.example.stride.domain.models.UserModel
import com.example.stride.domain.repository.ApiServicesRepository
import com.example.stride.domain.sharedModels.UserRepository
import com.example.stride.utility.composeUtility.isValidEmail
import com.example.stride.utility.composeUtility.toMultipart
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

data class ResetPasswordStates(
    val isLoading:Boolean = false,
    val forgotPasswordState : Boolean = false,
    val email : String? = "",
    val isEmailValid: Boolean? = true,
    val errorEmailMessage : String = ""
)

@HiltViewModel
class ResetPasswordViewModel@Inject constructor(
    private val apiServicesRepository: ApiServicesRepository,
    private val apiKey: String,
    private val userRepository: UserRepository
): ViewModel() {
    private var _uiStates = MutableStateFlow(ResetPasswordStates())
    val uiStates: StateFlow<ResetPasswordStates> = _uiStates.asStateFlow()

    fun setForgotPasswordState(forgotPasswordState: Boolean) {
        userRepository.setValues(
            UserModel(
                email = _uiStates.value.email
            )
        )
        _uiStates.value = _uiStates.value.copy(
            forgotPasswordState = forgotPasswordState
        )
    }

    fun setEmail(email: String) {
        _uiStates.value = _uiStates.value.copy(
            email = email
        )
    }

    fun onVerificationCodeClick(navHostController: NavHostController) {
        if (_uiStates.value.email?.isValidEmail() == true) {
            _uiStates.value = _uiStates.value.copy(isEmailValid = true)
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    _uiStates.value = _uiStates.value.copy(isLoading = true)
                    val call = apiServicesRepository.resetPassword(
                        authorization = apiKey,
                        email = _uiStates.value.email!!
                    )
                    call.enqueue(object : Callback<ResetPasswordDto> {
                        override fun onResponse(
                            call: Call<ResetPasswordDto>,
                            response: Response<ResetPasswordDto>
                        ) {
                            if (response.isSuccessful) {
                                _uiStates.value = _uiStates.value.copy(
                                    isLoading = false,
                                    forgotPasswordState = true
                                )

                                navHostController.navigate("otp_login_screen")
                            } else {
                                _uiStates.value = _uiStates.value.copy(isLoading = false)
                            }
                        }

                        override fun onFailure(call: Call<ResetPasswordDto>, t: Throwable) {
                            _uiStates.value = _uiStates.value.copy(isLoading = false)
                        }
                    })
                } catch (e: Exception) {
                    _uiStates.value = _uiStates.value.copy(isLoading = false)
                }
            }
        } else {
            _uiStates.value = _uiStates.value.copy(
                isEmailValid = false,
                errorEmailMessage = "Invalid Email"
            )
        }
    }
}
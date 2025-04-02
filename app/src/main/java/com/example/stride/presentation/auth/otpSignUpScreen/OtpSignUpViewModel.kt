package com.example.stride.presentation.auth.otpSignUpScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.stride.data.local.DataStoreRepository
import com.example.stride.data.remote.dto.OtpSignUpDto
import com.example.stride.domain.repository.ApiServicesRepository
import com.example.stride.domain.sharedModels.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class OtpSignUpStates(
    val otp: String = "",
    val isOtpValid: Boolean? = true,
    val isOtpSent: Boolean = false,
    val isOtpVerified: Boolean = false,
    val isOtpResend: Boolean = false,
    val isOtpError: Boolean = false,
    val isOtpLoading: Boolean = false,
    val isOtpResendLoading: Boolean = false,
    val errorOtpMessage: String = "",
    val registrationState: Boolean = false,
    val isLoading:Boolean = false

)

@HiltViewModel
class OtpSignUpViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    private val apiKey: String,
    private val apiServicesRepository: ApiServicesRepository,
    private val user: UserRepository
) : ViewModel() {
    private val _uiStates = MutableStateFlow(OtpSignUpStates())
    val uiStates: StateFlow<OtpSignUpStates> = _uiStates

    fun setOtp(otp: String) {
        _uiStates.value = _uiStates.value.copy(otp = otp)
    }
    fun onVerifyClick(otp: String, navHostController: NavHostController) {
        if (otp.isEmpty()) {
            _uiStates.value = _uiStates.value.copy(
                isOtpValid = false,
                errorOtpMessage = "Code cannot be empty"
            )
            return
        }

        Log.d("OtpLoginViewModel", "OTP Verification started with OTP: $otp")
        Log.d("OtpLoginScreen", "OTP Length: ${otp.length}")

        if (otp.length == 4) {
            setOtpValid(true)
            Log.d("OtpLoginViewModel", "OTP is valid")
        } else {
            setOtpValid(false)
            Log.d("OtpLoginViewModel", "OTP is invalid")
        }

        if (_uiStates.value.isOtpValid == true) {
            Log.d("OtpLoginViewModel", "Calling OTP Login API...")
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val call = apiServicesRepository.otpSignUp(
                        email = user.getValues().email ?: "",
                        otp = otp
                    )
                    call.enqueue(object : retrofit2.Callback<OtpSignUpDto> {
                        override fun onResponse(
                            call: retrofit2.Call<OtpSignUpDto>,
                            response: retrofit2.Response<OtpSignUpDto>
                        ) {
                            if (response.isSuccessful) {
                                Log.d("OtpLoginViewModel", "OTP Verified Successfully")
                                setOtpVerified(true)
                                viewModelScope.launch {
                                    dataStoreRepository.saveToken(response.body()?.token ?: "")
                                    Log.d("rt","${response.body()?.token}")
                                }
                                navHostController.navigate("dashboard")
                            } else {
                                Log.e("OtpLoginViewModel", "Error: ${response.message()}")
                                setOtpError(true)
                            }
                        }

                        override fun onFailure(
                            call: retrofit2.Call<OtpSignUpDto>,
                            t: Throwable
                        ) {
                            Log.e("OtpLoginViewModel", "API Call Failed: ${t.message}")
                            setOtpError(true)
                        }
                    })
                } catch (e: Exception) {
                    Log.e("OtpLoginViewModel", "Exception: ${e.message}")
                    setOtpError(true)
                }
            }
        } else {
            Log.d("OtpLoginViewModel", "OTP is invalid, aborting API call")
            setOtpError(true)
        }
    }

    fun setOtpValid(isOtpValid: Boolean) {
        _uiStates.value = _uiStates.value.copy(isOtpValid = isOtpValid)
    }

    fun setOtpSent(isOtpSent: Boolean) {
        _uiStates.value = _uiStates.value.copy(isOtpSent = isOtpSent)
    }

    fun setOtpVerified(isOtpVerified: Boolean) {
        _uiStates.value = _uiStates.value.copy(isOtpVerified = isOtpVerified)
    }

    fun setOtpResend(isOtpResend: Boolean) {
        _uiStates.value = _uiStates.value.copy(isOtpResend = isOtpResend)
    }

    fun setOtpError(isOtpError: Boolean) {
        _uiStates.value = _uiStates.value.copy(isOtpError = isOtpError)
    }

    fun setOtpLoading(isOtpLoading: Boolean) {
        _uiStates.value = _uiStates.value.copy(isOtpLoading = isOtpLoading)
    }

    fun setOtpResendLoading(isOtpResendLoading: Boolean) {
        _uiStates.value = _uiStates.value.copy(isOtpResendLoading = isOtpResendLoading)
    }

    fun setRegistrationState(registrationState: Boolean) {
        _uiStates.value = _uiStates.value.copy(
            registrationState = registrationState
        )
    }
}
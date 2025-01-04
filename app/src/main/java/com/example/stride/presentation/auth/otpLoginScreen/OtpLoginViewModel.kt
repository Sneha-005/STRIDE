package com.example.stride.presentation.auth.otpLoginScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class OtpStates(
    val otp: String = "",
    val isOtpValid: Boolean? = true,
    val isOtpSent: Boolean = false,
    val isOtpVerified: Boolean = false,
    val isOtpResend: Boolean = false,
    val isOtpError: Boolean = false,
    val isOtpLoading: Boolean = false,
    val isOtpResendLoading: Boolean = false,
    val errorOtpMessage: String = "",
    val registrationState: Boolean = false

)

@HiltViewModel
class OtpViewmodel @Inject constructor(
  //  private val dataStoreRepository: DataStoreRepository,
    private val apiKey: String,
   // private val apiServicesRepository: ApiServicesRepository,
  //  private val user: UserRepository
) : ViewModel() {
    private val _otpState = MutableStateFlow(OtpStates())
    val otpState: StateFlow<OtpStates> = _otpState

    fun setOtp(otp: String) {
        _otpState.value = _otpState.value.copy(otp = otp)
    }
    fun onOtpClick(otp:String) {
        if (otp.length== 6) {
            setOtpValid(true)
        } else {
            setOtpValid(false)
        }
        if(_otpState.value.isOtpValid==true)
        {

        }
        else{
            setOtpError(true)
        }

    }

    fun setOtpValid(isOtpValid: Boolean) {
        _otpState.value = _otpState.value.copy(isOtpValid = isOtpValid)
    }

    fun setOtpSent(isOtpSent: Boolean) {
        _otpState.value = _otpState.value.copy(isOtpSent = isOtpSent)
    }

    fun setOtpVerified(isOtpVerified: Boolean) {
        _otpState.value = _otpState.value.copy(isOtpVerified = isOtpVerified)
    }

    fun setOtpResend(isOtpResend: Boolean) {
        _otpState.value = _otpState.value.copy(isOtpResend = isOtpResend)
    }

    fun setOtpError(isOtpError: Boolean) {
        _otpState.value = _otpState.value.copy(isOtpError = isOtpError)
    }

    fun setOtpLoading(isOtpLoading: Boolean) {
        _otpState.value = _otpState.value.copy(isOtpLoading = isOtpLoading)
    }

    fun setOtpResendLoading(isOtpResendLoading: Boolean) {
        _otpState.value = _otpState.value.copy(isOtpResendLoading = isOtpResendLoading)
    }

    fun setRegistrationState(registrationState: Boolean) {
        _otpState.value = _otpState.value.copy(
            registrationState = registrationState
        )
    }
}
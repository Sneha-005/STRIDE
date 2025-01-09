package com.example.stride.presentation.auth.newPassword

import androidx.lifecycle.ViewModel
import com.example.stride.data.local.DataStoreRepository
import com.example.stride.data.remote.dto.NewPasswordDto
import kotlinx.coroutines.Dispatchers
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.example.stride.data.local.ResetDataStore
import com.example.stride.domain.repository.ApiServicesRepository
import com.example.stride.domain.sharedModels.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

data class NewPasswordStates(

    val password: String? = "",
    val confirmPassword: String? = "",
    val isLoading:Boolean = false,
    val isPasswordValid: Boolean? = true,
    val isConfirmPasswordValid: Boolean? = true,
    val errorPasswordMessage: String = "",
    val errorConfirmPasswordMessage: String = "",
    val registrationState: Boolean = false,
    val isPasswordSaved: Boolean = false

)

@HiltViewModel
class NewPasswordViewModel@Inject constructor(
    private val resetDataStore: ResetDataStore,
    private val apiKey: String,
    private val apiServicesRepository: ApiServicesRepository,
    private val user: UserRepository
): ViewModel() {

    private val _uiStates = MutableStateFlow(NewPasswordStates())
    val uiStates: StateFlow<NewPasswordStates> = _uiStates

    fun onSavePasswordCLick(navHostController: NavHostController) {

        if (_uiStates.value.password != _uiStates.value.confirmPassword) {
            _uiStates.value = _uiStates.value.copy(
                isPasswordValid = false,
                isConfirmPasswordValid = false
            )
            return
        }
            viewModelScope.launch(Dispatchers.IO){
                try{
                    val resetPasswordToken = resetDataStore.getResetPasswordToken()
                    Log.d("reset_t","onSavePasswordCLick: ${resetPasswordToken}")
                    val call = apiServicesRepository.newPassword(
                        authorization = "Bearer ${resetPasswordToken}",
                        password =( _uiStates.value.password?:""),
                        email = (user.getValues().email?:"")
                    )
                    call.enqueue(object : retrofit2.Callback<NewPasswordDto> {
                        override fun onResponse(
                            call: retrofit2.Call<NewPasswordDto>,
                            response: retrofit2.Response<NewPasswordDto>
                        ) {
                            Log.d("res","${response.code()}")
                            if (response.isSuccessful) {
                                Log.d("response", response.body().toString())
                                setRegistrationState(true)
                                _uiStates.value = _uiStates.value.copy(
                                    isPasswordSaved = true
                                )
                                navHostController.navigate("dashboard")
                            }
                        }

                        override fun onFailure(
                            call: retrofit2.Call<NewPasswordDto>,
                            t: Throwable
                        ) {
                            setRegistrationState(false)
                        }
                    })

                }catch(e:Exception){
                    setRegistrationState(false)
                }
            }
        }

    fun calculatePasswordStrength(password: String): Int {
        var hasUpperCase = false
        var hasDigit = false
        var hasSpecialChar = false
        val specialCharacters = "!@#$%^&*()_+-=[]{}|;:'\",.<>?/"

        for (char in password) {
            when {
                char.isUpperCase() -> hasUpperCase = true
                char.isDigit() -> hasDigit = true
                specialCharacters.contains(char) -> hasSpecialChar = true
            }
            if (hasUpperCase && hasDigit && hasSpecialChar) break
        }

        val lengthStrength = if (password.length >= 8) 1 else 0
        val caseStrength = if (hasUpperCase) 1 else 0
        val digitStrength = if (hasDigit) 1 else 0
        val specialCharStrength = if (hasSpecialChar) 1 else 0

        return lengthStrength + caseStrength + digitStrength + specialCharStrength
    }


    fun setConfirmPassword(confirmPassword: String) {
        _uiStates.value = _uiStates.value.copy(
            confirmPassword = confirmPassword
        )
    }
    fun setPassword(password: String) {
        _uiStates.value = _uiStates.value.copy(
            password = password
        )
    }
    fun setRegistrationState(registrationState: Boolean) {
        _uiStates.value = _uiStates.value.copy(
            registrationState = registrationState
        )
    }
}
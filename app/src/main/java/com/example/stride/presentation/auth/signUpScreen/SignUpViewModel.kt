package com.example.stride.presentation.auth.signUpScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
<<<<<<< HEAD
import com.example.stride.data.remote.dto.SignUpDto
=======
>>>>>>> dev
import com.example.stride.domain.models.UserModel
import com.example.stride.domain.repository.ApiServicesRepository
import com.example.stride.domain.sharedModels.UserRepository
import com.example.stride.utility.composeUtility.isValidEmail
import com.example.stride.utility.composeUtility.isValidPassword
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
<<<<<<< HEAD
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
=======
>>>>>>> dev
import javax.inject.Inject

data class SignUpStates(
    val email: String? = "",
    val name: String? = "",
    val password: String? = "",
    val confirmPassword: String? = "",
    val isLoading: Boolean = false,
    val isEmailValid: Boolean? = true,
    val isNameValid: Boolean? = true,
    val isPasswordValid: Boolean? = true,
    val isConfirmPasswordValid: Boolean? = true,
    val errorNameMessage: String = "",
    val errorEmailMessage: String = "",
    val errorPasswordMessage: String = "",
    val errorConfirmPasswordMessage: String = "",
    val registrationState: Boolean = false,
    val hasAttemptedSignUp: Boolean = false
)

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val apiServicesRepository: ApiServicesRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiStates = MutableStateFlow(SignUpStates())
    val uiStates: StateFlow<SignUpStates> = _uiStates

    companion object {
        const val INVALID_EMAIL = "Invalid email format"
        const val INVALID_PASSWORD =
            "Password must be at least 8 characters with a mix of upper case, numbers, and special characters"
        const val PASSWORDS_NOT_MATCH = "Passwords do not match"
        const val INVALID_NAME = "Name should be at least 3 characters"
    }

    fun setEmail(email: String) {
        _uiStates.value = _uiStates.value.copy(email = email)
    }

    fun setName(name: String) {
        _uiStates.value = _uiStates.value.copy(name = name)
    }

    fun setPassword(password: String) {
        _uiStates.value = _uiStates.value.copy(password = password)
    }

    fun setConfirmPassword(confirmPassword: String) {
        _uiStates.value = _uiStates.value.copy(confirmPassword = confirmPassword)
    }

    fun setRegistrationState(registrationState: Boolean) {
        _uiStates.value = _uiStates.value.copy(registrationState = registrationState)
    }

    private fun validateInput(): Boolean {
        val emailValid = _uiStates.value.email?.isValidEmail() == true
        val passwordValid = _uiStates.value.password?.isValidPassword() == true
        val confirmPasswordValid = _uiStates.value.confirmPassword == _uiStates.value.password
        val nameValid = (_uiStates.value.name?.length ?: 0) >= 3

        _uiStates.value = _uiStates.value.copy(
            isEmailValid = emailValid,
            errorEmailMessage = if (!emailValid) INVALID_EMAIL else "",
            isPasswordValid = passwordValid,
            errorPasswordMessage = if (!passwordValid) INVALID_PASSWORD else "",
            isConfirmPasswordValid = confirmPasswordValid,
            errorConfirmPasswordMessage = if (!confirmPasswordValid) PASSWORDS_NOT_MATCH else "",
            isNameValid = nameValid,
            errorNameMessage = if (!nameValid) "Name must be at least 4 characters" else ""
        )
        return emailValid && passwordValid && confirmPasswordValid && nameValid
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

    fun onSignupClick(navHostController: NavHostController) {
        _uiStates.value = _uiStates.value.copy(hasAttemptedSignUp = true)

        if (!validateInput()) {
            Log.e("Validation", "Input validation failed")
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            _uiStates.value = _uiStates.value.copy(isLoading = true)
            try {
                val response = apiServicesRepository.signUp(
                    userName = _uiStates.value.name.orEmpty(),
                    password = _uiStates.value.password.orEmpty(),
                    email = _uiStates.value.email.orEmpty()
                ).execute()

                if (response.isSuccessful) {
                    response.body()?.let {
                        userRepository.setValues(
                            UserModel(
                                name = _uiStates.value.name.orEmpty(),
                                email = _uiStates.value.email.orEmpty(),
                                password = _uiStates.value.password.orEmpty()
                            )
                        )
                    }
                    _uiStates.value = _uiStates.value.copy(
                        isLoading = false,
                        registrationState = true
                    )
                    navHostController.navigate("otp_sign_up_screen")
                } else {
                    Log.e("API", "Signup failed: ${response.errorBody()?.string()}")
                    _uiStates.value = _uiStates.value.copy(isLoading = false)
                }
            } catch (e: Exception) {
                Log.e("API", "Signup API call failed: ${e.message}")
                _uiStates.value = _uiStates.value.copy(isLoading = false)
            }
        }
    }
}

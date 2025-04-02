package com.example.stride.presentation.auth.loginScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.stride.data.local.DataStoreRepository
import com.example.stride.data.remote.dto.LoginResponseDto
import com.example.stride.domain.repository.ApiServicesRepository
import com.example.stride.domain.sharedModels.UserRepository
import com.example.stride.utility.composeUtility.isValidEmail
import com.example.stride.utility.composeUtility.isValidPassword
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

data class LoginStates(
    val email:String? = "",
    val password:String? = "",
    val isLoading:Boolean = false,
    val isEmailValid : Boolean? = true,
    val isPasswordValid : Boolean? = true,
    val errorEmailMessage : String = "",
    val errorPasswordMessage : String = "",
    val loginState : Boolean = false
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    private val apiServicesRepository: ApiServicesRepository,
    private val apiKey: String,
    private val userRepository: UserRepository
):ViewModel() {


    private var _uiStates = MutableStateFlow(LoginStates())
    val uiStates : StateFlow<LoginStates> = _uiStates.asStateFlow()

    fun onLoginClick(navHostController: NavHostController)
    {
        if(_uiStates.value.email?.isValidEmail()==true)
        {
            _uiStates.value=_uiStates.value.copy(
                isEmailValid = true
            )
        }
        else{
            _uiStates.value=_uiStates.value.copy(
                isEmailValid = false,
                errorEmailMessage = "Invalid Email"
            )
        }
        if(_uiStates.value.password?.isValidPassword()?.not() == false)
        {
            _uiStates.value=_uiStates.value.copy(
                isPasswordValid = true
            )
        }
        else{
            _uiStates.value=_uiStates.value.copy(
                isPasswordValid = false,
                errorPasswordMessage = "Not a valid password"
            )
        }

        if(_uiStates.value.isEmailValid == true && _uiStates.value.isPasswordValid == true)
        {
            viewModelScope.launch(Dispatchers.IO) {

                _uiStates.value = _uiStates.value.copy(
                    isLoading = true
                )
                val call = apiServicesRepository.loginUser(
                    email = (_uiStates.value.email?: ""),
                    password = (_uiStates.value.password ?: "")
                )
                call.enqueue(object : Callback<LoginResponseDto> {
                    override fun onResponse(
                        call: Call<LoginResponseDto>,
                        response: Response<LoginResponseDto>
                    ) {
                        Log.d("LoginViewModel", "Request: email=${_uiStates.value.email}, password=${_uiStates.value.password}")
                        Log.d("LoginViewModel", "Response Code: ${response.code()}")
                        Log.d("LoginViewModel", "Response Message: ${response.message()}")
                        Log.d("LoginViewModel", "Response Body: ${response.body()}")

                        if (response.isSuccessful) {
                            _uiStates.value = _uiStates.value.copy(
                                isLoading = false,
                                loginState = true
                            )
                            viewModelScope.launch {
                                dataStoreRepository.saveToken(response.body()?.token ?: "")
                                Log.d("LoginViewModel", "Response: ${response.body()}")
                                navHostController.navigate("dashboard")
                            }
                            Log.d("LoginViewModel", "Response: ${response.body()}")
                        } else {
                            _uiStates.value = _uiStates.value.copy(
                                isLoading = false,
                                loginState = false
                            )
                        }
                    }

                    override fun onFailure(call: Call<LoginResponseDto>, t: Throwable) {
                        _uiStates.value = _uiStates.value.copy(
                            isLoading = false,
                            loginState = false
                        )
                    }

                })
            }
        }


    }

    fun onForgotPasswordClick(navHostController: NavHostController){
        navHostController.navigate("reset_pass_screen/${uiStates.value.email}")
    }

    fun setEmail(email:String)
    {
        _uiStates.value=_uiStates.value.copy(
            email = email
        )
    }
    fun setPassword(password:String)
    {
        _uiStates.value=_uiStates.value.copy(
            password = password
        )
    }

}
package com.example.stride.data.repository

import com.example.stride.data.remote.ApiServices
import com.example.stride.data.remote.dto.LoginResponseDto
import com.example.stride.data.remote.dto.NewPasswordDto
import com.example.stride.data.remote.dto.OauthDto
import com.example.stride.data.remote.dto.OtpLoginDto
import com.example.stride.data.remote.dto.ResetPasswordDto
import com.example.stride.data.remote.dto.isRegistered
import com.example.stride.domain.models.LoginRequest
import com.example.stride.domain.models.NewPasswordRequest
import com.example.stride.domain.models.OauthRequest
import com.example.stride.domain.models.OtpLoginRequest
import com.example.stride.domain.models.RegisterRequest
import com.example.stride.domain.models.ResetPasswordRequest
import com.example.stride.domain.repository.ApiServicesRepository
import retrofit2.Call
import javax.inject.Inject

class ApiServicesRepositoryImpl @Inject constructor(
    private val apiService: ApiServices
) : ApiServicesRepository {
    override suspend fun registerUser(email: String): Call<isRegistered> {
        val registerRequest = RegisterRequest(email)
        return apiService.isRegistered(registerRequest)
    }

    override suspend fun loginUser(
        authorization: String,
        email: String,
        password: String
    ): Call<LoginResponseDto> {
        val loginRequest = LoginRequest(email, password)
        return apiService.login(authorization, loginRequest)
    }

    override suspend fun resetPassword(
        authorization: String,
        email: String
    ): Call<ResetPasswordDto> {
        val resetPasswordRequest = ResetPasswordRequest(email)
        return apiService.resetPassword(authorization, resetPasswordRequest)
    }

    override suspend fun otpLogin(
        authorization: String,
        email: String,
        otp: String
    ): Call<OtpLoginDto> {
        val otpLoginRequest = OtpLoginRequest(email, otp)
        return apiService.otpLogin(authorization, otpLoginRequest)
    }

    override suspend fun newPassword(
        authorization: String,
        password: String,
        email: String
    ): Call<NewPasswordDto> {
        val newPasswordRequest = NewPasswordRequest(password,email)
        return apiService.newPassword(authorization, newPasswordRequest)
    }

    override suspend fun oauth(token: String): Call<OauthDto> {
        val request = OauthRequest(token)
        return apiService.oauth(request)
    }

}

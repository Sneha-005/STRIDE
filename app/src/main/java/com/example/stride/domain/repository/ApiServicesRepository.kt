package com.example.stride.domain.repository

import com.example.stride.data.remote.dto.LoginResponseDto
import com.example.stride.data.remote.dto.NewPasswordDto
import com.example.stride.data.remote.dto.OauthDto
import com.example.stride.data.remote.dto.OtpLoginDto
import com.example.stride.data.remote.dto.ResetPasswordDto
import com.example.stride.data.remote.dto.isRegistered
import retrofit2.Call

interface ApiServicesRepository {
    suspend fun registerUser(
        email: String
    ): Call<isRegistered>

    suspend fun loginUser(
        authorization: String,
        email: String,
        password: String
    ): Call<LoginResponseDto>

    suspend fun resetPassword(
        authorization: String,
        email: String
    ):Call<ResetPasswordDto>

    suspend fun otpLogin(
        authorization: String,
        email: String,
        otp: String
    ):Call<OtpLoginDto>

    suspend fun newPassword(
        authorization: String,
        password: String,
        email: String
    ): Call<NewPasswordDto>

    suspend fun oauth(
        token: String
    ): Call<OauthDto>
}

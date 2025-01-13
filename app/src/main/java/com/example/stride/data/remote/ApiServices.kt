package com.example.stride.data.remote

import com.example.stride.data.remote.dto.LoginResponseDto
import com.example.stride.data.remote.dto.NewPasswordDto
import com.example.stride.data.remote.dto.OauthDto
import com.example.stride.data.remote.dto.OtpLoginDto
import com.example.stride.data.remote.dto.OtpSignUpDto
import com.example.stride.data.remote.dto.ResetPasswordDto
import com.example.stride.data.remote.dto.SignUpDto
import com.example.stride.data.remote.dto.isRegistered
import com.example.stride.domain.models.LoginRequest
import com.example.stride.domain.models.NewPasswordRequest
import com.example.stride.domain.models.OauthRequest
import com.example.stride.domain.models.OtpLoginRequest
import com.example.stride.domain.models.OtpSignUpRequest
import com.example.stride.domain.models.RegisterRequest
import com.example.stride.domain.models.ResetPasswordRequest
import com.example.stride.domain.models.SignUpRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface ApiServices {

    @POST("ifRegistered")
    fun isRegistered(
        @Body registerRequest: RegisterRequest
    ): Call<isRegistered>

    @POST("authenticate")
    fun login(
        @Body loginRequest: LoginRequest
    ): Call<LoginResponseDto>

    @POST("forgot-password")
    fun resetPassword(
        @Body resetPasswordRequest: ResetPasswordRequest
    ): Call<ResetPasswordDto>

    @POST("verify-Email")
    fun otpLogin(
        @Body otpLoginRequest: OtpLoginRequest
    ): Call<OtpLoginDto>

    @PUT("reset-password")
    fun newPassword(
        @Body newPasswordRequest: NewPasswordRequest
    ): Call<NewPasswordDto>

    @POST("oauth/success")
    fun oauth(
        @Body oauthRequest: OauthRequest
    ): Call<OauthDto>

    @POST("register")
    fun signUp(
        @Body signUpRequest: SignUpRequest
    ): Call<SignUpDto>

    @POST("validate")
    fun otpSignUp(
        @Body otpSignUpRequest : OtpSignUpRequest
    ): Call<OtpSignUpDto>
}
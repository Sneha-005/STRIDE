package com.example.stride.domain.models

data class OtpLoginRequest(
    val email: String,
    val otp: String
)

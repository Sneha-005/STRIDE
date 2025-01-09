package com.example.stride.data.remote.dto

data class OtpLoginDto(
    val token: String,
    val message: String,
    val user: OtpUser
)

data class OtpUser(
    val username: String,
    val email: String
)

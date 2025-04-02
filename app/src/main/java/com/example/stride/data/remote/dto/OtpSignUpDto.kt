package com.example.stride.data.remote.dto

data class OtpSignUpDto(
    val token: String,
    val message: String,
    val user: OtpSignUpUser
)

data class OtpSignUpUser(
    val username: String,
    val email: String
)

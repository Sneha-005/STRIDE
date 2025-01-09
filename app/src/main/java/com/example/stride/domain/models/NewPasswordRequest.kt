package com.example.stride.domain.models

data class NewPasswordRequest(
    val password: String,
    val email: String
)

package com.example.stride.domain.models

data class SignUpRequest(
    val userName: String,
    val email: String,
    val password: String
)

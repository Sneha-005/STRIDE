package com.example.stride.domain.models

data class UserModel(
    val token: String? = "",
    val email: String? = "",
    val name: String? = "",
    val password: String = ""
)

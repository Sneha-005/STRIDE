package com.example.stride.data.remote.dto

data class OauthDto(
    val token: String,
    val message: String,
    val user: User
)

data class User(
    val username: String,
    val email: String
)

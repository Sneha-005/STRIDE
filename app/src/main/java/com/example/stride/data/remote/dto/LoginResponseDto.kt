package com.example.stride.data.remote.dto

data class LoginResponseDto(
    val token : String,
    val message : String?="",
    val user : LoginUser
)

data class LoginUser(
    val username: String,
    val email : String
)
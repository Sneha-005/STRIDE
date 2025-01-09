package com.example.stride.presentation.auth.getStarted

data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null
)

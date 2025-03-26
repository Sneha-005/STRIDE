package com.example.stride.domain.models

data class LandingRequest(
    val gender: String,
    val age: Int,
    val height: Float,
    val weight: Float,
    val dietType: String,
    val fitnessGoal: String,
    val activityLevel: String,
    val waterGoal: Float,
    val stepCount: Float,
    val calorieCount: Float
)

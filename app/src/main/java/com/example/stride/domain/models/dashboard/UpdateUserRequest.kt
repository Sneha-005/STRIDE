package com.example.stride.domain.models.dashboard

data class UpdateUserRequest(
    val gender: String? = null,
    val age: Int? = null,
    val height: Float? = null,
    val weight: Float? = null,
    val dietType: String? = null,
    val fitnessGoal: String? = null,
    val activityLevel: String? = null,
    val waterGoal: Float? = null,
    val stepCount: Float? = null,
    val calorieCount: Float? = null,
    val name: String? = null

)



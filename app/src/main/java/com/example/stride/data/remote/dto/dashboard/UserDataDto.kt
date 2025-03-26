package com.example.stride.data.remote.dto.dashboard

data class UserDataDto(
    val userid: Int,
    val user: User,
    val gender: String,
    val age: Int,
    val height: Int,
    val weight: Int,
    val dietType: String,
    val fitnessGoal: String,
    val activityLevel: String,
    val waterGoal: Int?,
    val calorieGoal: Int?,
    val stepGoal: Int?,
    val coins: Int?
)
data class User(
    val userId: Int,
    val name: String,
    val email: String
)

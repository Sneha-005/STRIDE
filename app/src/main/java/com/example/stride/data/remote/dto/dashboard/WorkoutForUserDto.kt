package com.example.stride.data.remote.dto.dashboard

data class WorkoutForUserDto(
    val id: Int?,
    val workoutId: Int,
    val name: String,
    val description: String,
    val goal: String,
    val targetBodyPart: String?,
    val numberOfExercises: Int,
    val numberOfExercisesCompleted: Int
)
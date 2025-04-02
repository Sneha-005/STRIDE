package com.example.stride.data.remote.dto.dashboard

data class WorkoutExerciseDto(
    val exercises: List<Exercise>
)
data class Exercise(
    val id: Int,
    val name: String,
    val duration: String?,
    val reps: Int?,
    val restTime: Int,
    val targetBody: String,
    val calories: Int,
    val image: String,
    val status: String
)

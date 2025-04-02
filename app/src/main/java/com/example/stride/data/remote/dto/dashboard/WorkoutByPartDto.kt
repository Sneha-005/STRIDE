package com.example.stride.data.remote.dto.dashboard

data class WorkoutByPartDto(
    val exercises: List<ExerciseDto>
)

data class ExerciseDto(
    val id: Int,
    val name: String,
    val gifUrl: String,
    val reps: Int?,
    val duration: Int?,
    val restTime: Int?,
    val targetBody: String,
    val calories: Int
)

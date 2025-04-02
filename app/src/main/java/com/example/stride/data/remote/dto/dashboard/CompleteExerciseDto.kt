package com.example.stride.data.remote.dto.dashboard

data class CompleteExerciseDto(
    val id: Int,
    val name: String,
    val gifUrl: String,
    val reps: Int?,
    val duration: Int?,
    val restTime: Int,
    val targetBody: String,
    val calories: Int
)

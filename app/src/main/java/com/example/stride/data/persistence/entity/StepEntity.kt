package com.example.stride.data.persistence.entity

import androidx.room.Entity
import androidx.room.PrimaryKey



@Entity(tableName = "steps")
data class StepEntity(
    @PrimaryKey val date: String,
    val stepCount: Int
)
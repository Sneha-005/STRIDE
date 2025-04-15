package com.example.stride.data.repository

import android.content.Context
import com.example.stride.data.persistence.StepDatabase
import com.example.stride.data.persistence.entity.StepEntity

class StepRepository(context: Context) {
    private val stepDao = StepDatabase.getDatabase(context).stepDao()

    suspend fun insertSteps(step: StepEntity) {
        stepDao.insertStep(step)
    }

    suspend fun getStepsForDate(date: String): StepEntity? {
        return stepDao.getStepsByDate(date)
    }
}

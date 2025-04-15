package com.example.stride.data.persistence.dao


import com.example.stride.data.persistence.entity.StepEntity
import androidx.room.*

@Dao
interface StepDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStep(step: StepEntity)

    @Query("SELECT * FROM steps WHERE date = :date LIMIT 1")
    suspend fun getStepsByDate(date: String): StepEntity?
}

package com.example.stride.presentation.dashboard.fitness

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stride.domain.repository.ApiServicesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.stride.data.local.DataStoreRepository
import com.example.stride.data.remote.dto.dashboard.CompleteExerciseDto
import com.example.stride.data.remote.dto.dashboard.WorkoutByPartDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

data class Exercise(
    val id: Int,
    val name: String,
    val duration: String?,
    val reps: Int?,
    val image: String = "",
    val calories: Int
)

data class ExerciseState(
    val calories: Int? = 0
)

@HiltViewModel
class StrengthViewModel @Inject constructor(
    private val apiServicesRepository: ApiServicesRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    private val _exercises = MutableStateFlow<List<Exercise>>(emptyList())
    val exercises: StateFlow<List<Exercise>> = _exercises

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private var _uiStates = MutableStateFlow(ExerciseState())
    val uiStates: StateFlow<ExerciseState> = _uiStates.asStateFlow()

    init {
        fetchExercises("legs")
    }

    fun fetchExercises(bodyPart: String) {
        _isLoading.value = true
        viewModelScope.launch {
            val token = dataStoreRepository.readToken()
            if (token.isNullOrEmpty()) {
                Log.e("StrengthViewModel", "Token is null or empty")
                _isLoading.value = false
                return@launch
            }

            apiServicesRepository.workoutByPart("Bearer $token", bodyPart).enqueue(object : Callback<WorkoutByPartDto> {
                override fun onResponse(call: Call<WorkoutByPartDto>, response: Response<WorkoutByPartDto>) {
                    if (response.isSuccessful) {
                        Log.d("StrengthViewModel", "Fetched exercises")
                        response.body()?.let { workoutByPartDto ->
                            _exercises.value = workoutByPartDto.exercises.map { dto ->
                                Exercise(
                                    id = dto.id,
                                    name = dto.name,
                                    duration = dto.duration?.let { "${it} min" },
                                    reps = dto.reps,
                                    image = dto.gifUrl ?: "" ,
                                    calories = dto.calories
                                )
                            }

                        }
                    } else {
                        Log.e("StrengthViewModel", "Failed to fetch exercises: ${response.message()}")
                    }
                    _isLoading.value = false
                }

                override fun onFailure(call: Call<WorkoutByPartDto>, t: Throwable) {
                    Log.e("StrengthViewModel", "Error fetching exercises: ${t.message}", t)
                    _isLoading.value = false
                }
            })
        }
    }

    fun completeExercise(exerciseId: String,context: android.content.Context) {
        viewModelScope.launch {
            val token = dataStoreRepository.readToken()
            Log.d("HomeScreenViewModel", "Exercise ID: $exerciseId")
            Log.d("HomeScreenViewModel", "Token: $token")
            try {
                val call =
                    apiServicesRepository.completeExercise("Bearer $token",  exerciseId = exerciseId)
                call.enqueue(object : Callback<CompleteExerciseDto> {
                    override fun onResponse(
                        call: Call<CompleteExerciseDto>,
                        response: Response<CompleteExerciseDto>
                    ) {
                        Log.d("API Response", "Code: ${response.code()}")
                        if (response.isSuccessful) {
                            Log.d("API Response", "Exercise completed successfully!")
                            Toast.makeText(
                                context,
                                "Exercise completed successfully!",
                                Toast.LENGTH_SHORT
                            ).show()
                            _uiStates.value = _uiStates.value.copy(response.body()?.calories)
                            println("Exercise completed successfully!")
                        } else {
                            println("Error: ${response.message()}")
                        }
                    }

                    override fun onFailure(call: Call<CompleteExerciseDto>, t: Throwable) {
                        println("Error: ${t.message}")
                    }

                })
            }catch(e: Exception){
                println("Error: ${e.message}")
            }
        }
    }
}
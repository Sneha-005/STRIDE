package com.example.stride.presentation.dashboard.homePage

import android.app.Application
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.stride.data.local.DataStoreRepository
import com.example.stride.data.remote.dto.dashboard.CaloriesBurntResponse
import com.example.stride.data.remote.dto.dashboard.CaloriesConsumedResponse
import com.example.stride.data.remote.dto.dashboard.CompleteExerciseDto
import com.example.stride.data.remote.dto.dashboard.StepCounterResponse
import com.example.stride.data.remote.dto.dashboard.UserDataDto
import com.example.stride.data.remote.dto.dashboard.WorkoutExerciseDto
import com.example.stride.data.remote.dto.dashboard.WorkoutForUserDto
import com.example.stride.domain.repository.ApiServicesRepository
import com.example.stride.domain.sharedModels.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalTime
import javax.inject.Inject


data class ExerciseDetails(
    val id: Int,
    val name: String,
    val duration: Int,
    val reps: Int,
    val image: String,
    val restTime: Int
)

data class HomeScreenStates(
    var stepCount: Int = 0,
    val caloriesConsumed: Int = 0,
    val caloriesBurnt: Int = 0,
    val stepGoal: Int = 10000,
    val waterGoal: Int = 8,
    val calorieGoal: Int = 8,
    val coins: Int? = 0,
    val age: Int? = 0,
    val height: Int? = 0,
    val weight: Int? = 0,
    val name: String? = "Username",
    val exerciseCalorieBurnt: Int = 0,
    val totalExercise: Int = 0
)

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    application: Application,
    private val dataStoreRepository: DataStoreRepository,
    private val apiServicesRepository: ApiServicesRepository,
    private val userRepository: UserRepository
) : AndroidViewModel(application), SensorEventListener {


    private var sensorManager: SensorManager? = null
    private var stepSensor: Sensor? = null

    private var totalSteps = 0f
    private var previousTotalSteps = 0f

    private val _stepCount = MutableStateFlow(0)
    val stepCount: StateFlow<Int> = _stepCount.asStateFlow()

    private var _uiStates = MutableStateFlow(HomeScreenStates())
    val uiStates: StateFlow<HomeScreenStates> = _uiStates.asStateFlow()

    private val _exerciseDetails = MutableStateFlow<List<ExerciseDetails>>(emptyList())
    val exerciseDetails: StateFlow<List<ExerciseDetails>> = _exerciseDetails

    private val _exerciseNames = MutableStateFlow<List<String>>(emptyList())
    val exerciseNames: StateFlow<List<String>> = _exerciseNames

    private val _totalDuration = MutableStateFlow(0)
    val totalDuration: StateFlow<Int> = _totalDuration

    private val _workouts = MutableStateFlow<List<WorkoutForUserDto>>(emptyList())
    val workouts: StateFlow<List<WorkoutForUserDto>> = _workouts

    init {
        sensorManager = application.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        stepSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        stepSensor?.let {
            sensorManager?.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        }
        getCalorieConsumed(0)
        checkTimeAndResetSteps()
    }

    private fun checkTimeAndResetSteps() {
        val currentTime = LocalTime.now()
        if (currentTime.hour == 0 && currentTime.minute == 0) {
            resetSteps()
        }
    }

    fun resetSteps() {
        _uiStates.value = _uiStates.value.copy(stepCount = 0)
        previousTotalSteps = totalSteps
        _stepCount.value = 0
        sendStepCount(0)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_STEP_COUNTER) {
            totalSteps = event.values[0]
            val currentSteps = (totalSteps - previousTotalSteps).toInt()

            viewModelScope.launch {
                _stepCount.value = currentSteps
            }
        }
    }

    fun sendStepCount(stepCount: Int) {
        viewModelScope.launch {
            val token =dataStoreRepository.readToken()
            try {
                val call =
                    apiServicesRepository.addSteps("Bearer $token", stepCount = stepCount)
                call.enqueue(object : Callback<StepCounterResponse> {
                    override fun onResponse(
                        call: Call<StepCounterResponse>,
                        response: Response<StepCounterResponse>
                    ) {
                        if (response.isSuccessful) {
                            _stepCount.value = 0
                            previousTotalSteps = totalSteps
                        } else {
                            println("Error: ${response.message()}")
                        }
                    }

                    override fun onFailure(call: Call<StepCounterResponse>, t: Throwable) {
                        println("Error: ${t.message}")
                    }
                })
                println("Step count $stepCount sent successfully!")
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
    }

    fun getCalorieConsumed(z: Int){
        viewModelScope.launch {
            val token = dataStoreRepository.readToken()
            try {
                val call =
                    apiServicesRepository.getCaloryConsumed("Bearer $token", z = z)
                call.enqueue(object : Callback<CaloriesConsumedResponse> {
                    override fun onResponse(
                        call: Call<CaloriesConsumedResponse>,
                        response: Response<CaloriesConsumedResponse>
                    ) {

                        if (response.isSuccessful) {
                            _uiStates.value = _uiStates.value.copy(caloriesConsumed = response.body()?.calorieConsumed ?: 0)
                            println("Calories burned: ${response.body()?.calorieConsumed}")
                        } else {
                            println("Error: ${response.message()}")
                        }
                    }

                    override fun onFailure(call: Call<CaloriesConsumedResponse>, t: Throwable) {
                        println("Error: ${t.message}")
                    }
                })
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
    }

    fun getCalorieBurnt(z: Int){
        viewModelScope.launch {
            val token =  dataStoreRepository.readToken()
            try {
                val call =
                    apiServicesRepository.getCalorieBurnt("Bearer $token", z = z)

                call.enqueue(object : Callback<CaloriesBurntResponse> {
                    override fun onResponse(
                        call: Call<CaloriesBurntResponse>,
                        response: Response<CaloriesBurntResponse>
                    ) {
                        if (response.isSuccessful) {
                            _uiStates.value = _uiStates.value.copy(caloriesBurnt = response.body()?.calorieBurned ?: 0)
                            println("Calories burned: ${response.body()?.calorieBurned}")
                        } else {
                            println("Error: ${response.message()}")
                        }
                    }

                    override fun onFailure(call: Call<CaloriesBurntResponse>, t: Throwable) {
                        println("Error: ${t.message}")
                    }
                })
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
    }

    fun getUserData(){
        viewModelScope.launch {
            val token =  dataStoreRepository.readToken()
            try {
                val call =
                    apiServicesRepository.getUserData("Bearer $token")
                call.enqueue(object : Callback<UserDataDto> {
                    override fun onResponse(
                        call: Call<UserDataDto>,
                        response: Response<UserDataDto>
                    ) {
                        Log.d("API Response", "Code: ${response.code()}")
                        if (response.isSuccessful) {
                            _uiStates.value = _uiStates.value.copy(stepGoal = response.body()?.stepGoal ?: 0)
                            _uiStates.value = _uiStates.value.copy(waterGoal = response.body()?.waterGoal ?: 0)
                            _uiStates.value = _uiStates.value.copy(calorieGoal = response.body()?.calorieGoal ?: 0)
                            _uiStates.value = _uiStates.value.copy(coins = response.body()?.coins ?: 0)
                            _uiStates.value = _uiStates.value.copy(age = response.body()?.age ?: 0)
                            _uiStates.value = _uiStates.value.copy(height = response.body()?.height ?: 0)
                            _uiStates.value = _uiStates.value.copy(weight = response.body()?.weight ?: 0)
                            _uiStates.value = _uiStates.value.copy(name = response.body()?.user?.name ?: "")
                            println("User data: ${response.body()}")
                        } else {
                            println("Error: ${response.message()}")
                        }
                    }

                    override fun onFailure(call: Call<UserDataDto>, t: Throwable) {
                        println("Error: ${t.message}")
                    }
                })
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
    }

    fun getWorkoutForUser(){
        viewModelScope.launch {
            val token = dataStoreRepository.readToken()

            apiServicesRepository.getWorkoutForUser("Bearer $token").enqueue(object : Callback<List<WorkoutForUserDto>>{
                override fun onResponse(call: Call<List<WorkoutForUserDto>>, response: Response<List<WorkoutForUserDto>>) {
                    if (response.isSuccessful) {
                        response.body()?.let { workoutForUserDto ->
                            _workouts.value = workoutForUserDto
                        }
                    } else {
                        Log.e("StrengthViewModel", "Failed to fetch exercises: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<List<WorkoutForUserDto>>, t: Throwable) {
                    Log.e("StrengthViewModel", "Error fetching exercises: ${t.message}", t)
                }
            })
        }
    }

    fun fetchExerciseById(exerciseId: String) {
        viewModelScope.launch {
            val token = dataStoreRepository.readToken()
            if (token.isNullOrEmpty()) {
                Log.e("StrengthViewModel", "Token is null or empty")
                return@launch
            }

            apiServicesRepository.getWorkoutExercises("Bearer $token", exerciseId).enqueue(object : Callback<WorkoutExerciseDto> {
                override fun onResponse(call: Call<WorkoutExerciseDto>, response: Response<WorkoutExerciseDto>) {
                    if (response.isSuccessful) {
                        response.body()?.let { workoutExerciseDto ->
                            val exerciseNames = workoutExerciseDto.exercises.map { it.name }
                            _exerciseNames.value = exerciseNames
                            val totalDuration = workoutExerciseDto.exercises.sumOf {
                                it.duration?.filter { char -> char.isDigit() }?.toIntOrNull() ?: 0
                            }
                            _totalDuration.value = totalDuration
                            _exerciseDetails.value = workoutExerciseDto.exercises.map { dto ->
                                ExerciseDetails(
                                    id = dto.id,
                                    name = dto.name,
                                    duration = dto.duration?.filter { char -> char.isDigit() }?.toIntOrNull() ?: 20,
                                    reps = dto.reps ?: 0,
                                    image = dto.image?: "" ,
                                    restTime = dto.restTime
                                )
                            }
                        }
                    } else {
                        Log.e("StrengthViewModel", "Failed to fetch exercises: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<WorkoutExerciseDto>, t: Throwable) {
                    Log.e("StrengthViewModel", "Error fetching exercises: ${t.message}", t)
                }
            })
        }
    }
    
    fun completeWorkout(workoutId: String, exerciseId: String){
        viewModelScope.launch {
            val token = dataStoreRepository.readToken()
            Log.d("HomeScreenViewModel", "Workout ID: $workoutId")
            Log.d("HomeScreenViewModel", "Exercise ID: $exerciseId")
            Log.d("HomeScreenViewModel", "Token: $token")
            try {
                val call =
                    apiServicesRepository.completeWorkout("Bearer $token", workoutPlanId = workoutId, exerciseId = exerciseId)
                call.enqueue(object : Callback<CompleteExerciseDto> {
                    override fun onResponse(
                        call: Call<CompleteExerciseDto>,
                        response: Response<CompleteExerciseDto>
                    ) {
                        Log.d("API Response", "Code: ${response.code()}")
                        if (response.isSuccessful) {
                            Log.d("API Response", "Exercise completed successfully!")
                            _uiStates.value = uiStates.value.copy(coins = uiStates.value.exerciseCalorieBurnt.plus(5))
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

    fun getAllWorkouts(){
        viewModelScope.launch {
            val token = dataStoreRepository.readToken()
            try {
                val call =
                    apiServicesRepository.getAllWorkouts("Bearer $token")
                call.enqueue(object : Callback<List<WorkoutForUserDto>> {
                    override fun onResponse(
                        call: Call<List<WorkoutForUserDto>>,
                        response: Response<List<WorkoutForUserDto>>
                    ) {
                        if (response.isSuccessful) {
                            response.body()?.let { workoutForUserDto ->
                                _workouts.value = workoutForUserDto
                            }
                        } else {
                            Log.e("HomeScreenViewModel", "Failed to fetch exercises: ${response.message()}")
                        }
                    }
                    override fun onFailure(call: Call<List<WorkoutForUserDto>>, t: Throwable) {
                        Log.e("HomeScreenViewModel", "Error fetching exercises: ${t.message}", t)
                    }
                })
            }catch (e: Exception){
                Log.e("HomeScreenViewModel", "Error fetching exercises: ${e.message}", e)
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    override fun onCleared() {
        super.onCleared()
        sensorManager?.unregisterListener(this)
    }
}


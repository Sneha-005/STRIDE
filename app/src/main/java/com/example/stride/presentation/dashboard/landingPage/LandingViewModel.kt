package com.example.stride.presentation.dashboard.landingPage

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.stride.data.local.DataStoreRepository
import com.example.stride.data.remote.dto.LandingDto
import com.example.stride.domain.repository.ApiServicesRepository
import com.example.stride.domain.sharedModels.UserRepository
import com.example.stride.utility.navigation.AuthRouteScreen
import com.example.stride.utility.navigation.Graph
import com.example.stride.utility.navigation.MainRouteScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

data class LandingStates(
    val age:Int? = 0,
    val weight:Float? = 0f,
    val height:Float? = 0f,
    val gender:String? = "",
    val dietType:String? = "",
    val stepGoals:Float? = 0f,
    val calorieGoals:Float? = 0f,
    val waterIntakeGoals:Float? = 0f,
    val isLoading:Boolean = false,
    val fitnessGoal:String? = "",
    val activityLevel:String? = "",
    val errorAgeMessage:String = "",
    val errorWeightMessage:String = "",
    val errorHeightMessage:String = "",
    val errorGenderMessage:String = "",
    val errorDietTypeMessage:String = "",
    val errorFitnessGoalMessage:String = "",
    val errorActivityLevelMessage:String = "",
    val errorStepGoalsMessage:String = "",
    val errorCalorieGoalsMessage:String = "",
    val errorWaterIntakeGoalsMessage:String = "",
    val isAgeValid:Boolean = true,
    val isWeightValid:Boolean = true,
    val isHeightValid:Boolean = true,
    val isStepGoalsValid:Boolean = true,
    val isCalorieGoalValid:Boolean = true,
    val isWaterIntakeGoalValid:Boolean = true,
    val isGenderValid:Boolean = true,
    val isFitnessGoalValid:Boolean = true,
    val isActivityLevelValid:Boolean = true,
    val isDietTypeValid:Boolean = true,
    val isLandingState:Boolean = false,
    val questionState : Boolean = false,
    val goalState : Boolean = false,
    val levelState : Boolean = false

)

@HiltViewModel
class LandingViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    private val apiServicesRepository: ApiServicesRepository,
    private val userRepository: UserRepository
): ViewModel() {

    private var _uiStates = MutableStateFlow(LandingStates())
    val uiStates: StateFlow<LandingStates> = _uiStates.asStateFlow()

    fun onLandingClick(navHostController: NavHostController, context: Context) {
        Log.d("LandingViewModel", "Called")

        if (_uiStates.value.age!! > 0) {
            _uiStates.value = _uiStates.value.copy(
                isAgeValid = true
            )
        } else {
            _uiStates.value = _uiStates.value.copy(
                isAgeValid = false,
                errorAgeMessage = "Invalid Age"
            )
            Toast.makeText(context, "Invalid Age", Toast.LENGTH_SHORT).show()
            return
        }
        if (_uiStates.value.weight!! > 0f) {
            _uiStates.value = _uiStates.value.copy(
                isWeightValid = true
            )
        } else {
            _uiStates.value = _uiStates.value.copy(
                isWeightValid = false,
                errorWeightMessage = "Invalid Weight"
            )
            Toast.makeText(context, "Invalid Weight", Toast.LENGTH_SHORT).show()
        }
        if (_uiStates.value.height!! > 0f) {
            _uiStates.value = _uiStates.value.copy(
                isHeightValid = true
            )
        } else {
            _uiStates.value = _uiStates.value.copy(
                isHeightValid = false,
                errorHeightMessage = "Invalid Height"
            )
            Toast.makeText(context, "Invalid Height", Toast.LENGTH_SHORT).show()
        }
        if (_uiStates.value.gender?.isNotEmpty() == true) {
            _uiStates.value = _uiStates.value.copy(
                isGenderValid = true
            )
        } else {
            _uiStates.value = _uiStates.value.copy(
                isGenderValid = false,
                errorGenderMessage = "Invalid Gender"
            )
            Toast.makeText(context,_uiStates.value.errorGenderMessage, Toast.LENGTH_SHORT).show()
            return
        }
        if (_uiStates.value.fitnessGoal?.isNotEmpty() == true) {
            _uiStates.value = _uiStates.value.copy(
                isFitnessGoalValid = true,
                goalState = true
            )
        } else {
            _uiStates.value = _uiStates.value.copy(
                isFitnessGoalValid = false,
                goalState = false,
                errorFitnessGoalMessage = "Invalid Fitness Goal"
            )
            Toast.makeText(context,_uiStates.value.errorFitnessGoalMessage, Toast.LENGTH_SHORT).show()
        }
        if (_uiStates.value.activityLevel?.isNotEmpty() == true) {
            _uiStates.value = _uiStates.value.copy(
                isActivityLevelValid = true
            )
        } else {
            _uiStates.value = _uiStates.value.copy(
                isActivityLevelValid = false,
                errorActivityLevelMessage = "Invalid Activity Level"
            )
            Toast.makeText(context,_uiStates.value.errorActivityLevelMessage, Toast.LENGTH_SHORT).show()
            return
        }
        if (_uiStates.value.dietType?.isNotEmpty() == true) {
            _uiStates.value = _uiStates.value.copy(
                isDietTypeValid = true
            )
        } else {
            _uiStates.value = _uiStates.value.copy(
                isDietTypeValid = false,
                errorDietTypeMessage = "Invalid Diet Type"
            )
            Toast.makeText(context,_uiStates.value.errorDietTypeMessage, Toast.LENGTH_SHORT).show()
            return
        }

        if (_uiStates.value.stepGoals!! > 0f) {
            _uiStates.value = _uiStates.value.copy(
                isStepGoalsValid = true
            )
        } else {
            _uiStates.value = _uiStates.value.copy(
                isStepGoalsValid = false,
                errorStepGoalsMessage = "Invalid Height"
            )
            Toast.makeText(context,_uiStates.value.errorStepGoalsMessage, Toast.LENGTH_SHORT).show()
            return
        }

        if (_uiStates.value.calorieGoals!! > 0f) {
            _uiStates.value = _uiStates.value.copy(
                isCalorieGoalValid = true
            )
        } else {
            _uiStates.value = _uiStates.value.copy(
                isCalorieGoalValid = false,
                errorCalorieGoalsMessage = "Invalid Height"
            )
            Toast.makeText(context,_uiStates.value.errorCalorieGoalsMessage, Toast.LENGTH_SHORT).show()
            return
        }

        if (_uiStates.value.waterIntakeGoals!! > 0f) {
            _uiStates.value = _uiStates.value.copy(
                isWaterIntakeGoalValid = true
            )
        } else {
            _uiStates.value = _uiStates.value.copy(
                isWaterIntakeGoalValid = false,
                errorWaterIntakeGoalsMessage = "Invalid Height"
            )
            Toast.makeText(context,_uiStates.value.errorWaterIntakeGoalsMessage, Toast.LENGTH_SHORT).show()
            return
        }

        if (_uiStates.value.isAgeValid &&
            _uiStates.value.isWeightValid &&
            _uiStates.value.isHeightValid &&
            _uiStates.value.isGenderValid &&
            _uiStates.value.isFitnessGoalValid &&
            _uiStates.value.isActivityLevelValid &&
            _uiStates.value.isDietTypeValid &&
            _uiStates.value.isStepGoalsValid &&
            _uiStates.value.isCalorieGoalValid &&
            _uiStates.value.isWaterIntakeGoalValid
        ) {
            _uiStates.value = _uiStates.value.copy(
                isLoading = true

            )
            viewModelScope.launch(Dispatchers.IO) {
                val token = userRepository.getValues().token ?: dataStoreRepository.readToken()
                Log.d("LandingViewModel", "Token: $token")
                Log.d("LandingViewModel", "Age: ${_uiStates.value.age}")
                Log.d("LandingViewModel", "Weight: ${_uiStates.value.weight}")
                Log.d("LandingViewModel", "Height: ${_uiStates.value.height}")
                Log.d("LandingViewModel", "Gender: ${_uiStates.value.gender}")
                Log.d("LandingViewModel", "Diet Type: ${_uiStates.value.dietType}")
                Log.d("LandingViewModel", "Fitness Goal: ${_uiStates.value.fitnessGoal}")
                Log.d("LandingViewModel", "Activity Level: ${_uiStates.value.activityLevel}")
                if (token != null) {
                    val call =
                        apiServicesRepository.landingData(
                            token = "Bearer $token",
                            gender = (_uiStates.value.gender ?: ""),
                            age = (_uiStates.value.age ?: 0),
                            height = (_uiStates.value.height ?: 0f),
                            weight = (_uiStates.value.weight ?: 0f),
                            dietType = (_uiStates.value.dietType ?: ""),
                            fitnessGoal = (_uiStates.value.fitnessGoal ?: ""),
                            activityLevel = (_uiStates.value.activityLevel ?: ""),
                            stepCount = (_uiStates.value.stepGoals ?: 0f),
                            calorieCount = (_uiStates.value.calorieGoals ?: 0f),
                            waterGoal = (_uiStates.value.waterIntakeGoals ?: 0f)
                        )
                    call.enqueue(object : Callback<LandingDto> {
                        override fun onResponse(
                            call: Call<LandingDto>,
                            response: Response<LandingDto>
                        ) {
                            Log.d("LandingViewModel", " $token")
                            Log.d("LandingViewModel", "Response: ${response.body()}")
                            Log.d("LandingViewModel", "Response Code: ${response.code()}")
                            Log.d("LandingViewModel", "Response Message: ${response.message()}")
                            if (response.isSuccessful || response.code() == 404) {
                                _uiStates.value = _uiStates.value.copy(
                                    isLoading = false,
                                    isLandingState = true
                                )
                                viewModelScope.launch {
                                    navHostController.navigate(Graph.MainGraph){
                                        popUpTo(AuthRouteScreen.EndDashboard.route){inclusive = true}
                                    }
                                }
                            } else {
                                _uiStates.value = _uiStates.value.copy(
                                    isLoading = false,
                                    isLandingState = false
                                )
                            }
                        }

                        override fun onFailure(p0: Call<LandingDto>, p1: Throwable) {
                            _uiStates.value = _uiStates.value.copy(
                                isLoading = false,
                                isLandingState = false
                            )
                        }
                    })
                } else {
                    _uiStates.value = _uiStates.value.copy(
                        isLoading = false,
                        isLandingState = false
                    )
                    Log.e("LandingViewModel", "Token is null")
                }
            }
        } else {
            Log.d("LandingViewModel", "Not Called")

        }
    }

    fun setAge(age: Int) {
        _uiStates.update { currentState ->
            currentState.copy(age = age)
        }
    }

    fun setGender(gender: String) {
        _uiStates.value = _uiStates.value.copy(
            gender = gender
        )
    }

    fun setHeight(height: Float) {
        _uiStates.value = _uiStates.value.copy(
            height = height
        )
    }

    fun setDietType(dietType: String) {
        _uiStates.value = _uiStates.value.copy(
            dietType = dietType
        )
    }

    fun setWeight(weight: Float) {
        _uiStates.value = _uiStates.value.copy(
            weight = weight
        )
    }

    fun setFitnessGoal(fitnessGoal: String) {
        _uiStates.value = _uiStates.value.copy(
            fitnessGoal = fitnessGoal
        )
    }

    fun setActivityLevel(activityLevel: String) {
        _uiStates.value = _uiStates.value.copy(
            activityLevel = activityLevel
        )
    }

    fun setStepGoals(stepGoals: Float) {
        _uiStates.value = _uiStates.value.copy(
            stepGoals = stepGoals
        )
    }

    fun setCalorieGoals(calorieGoals: Float) {
        _uiStates.value = _uiStates.value.copy(
            calorieGoals = calorieGoals
        )
    }

    fun setWaterIntakeGoals(waterIntakeGoals: Float) {
        _uiStates.value = _uiStates.value.copy(
            waterIntakeGoals = waterIntakeGoals
        )
    }
}
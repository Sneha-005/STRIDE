package com.example.stride.presentation.dashboard.profile

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.stride.data.local.DataStoreRepository
import com.example.stride.data.remote.dto.dashboard.UpdateUserDataDto
import com.example.stride.domain.repository.ApiServicesRepository
import com.example.stride.domain.sharedModels.UserRepository
import com.example.stride.utility.navigation.AuthRouteScreen
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

data class ProfileState(
    val age: Int? = 0,
    val weight: Float? = 0f,
    val height: Float? = 0f,
    val gender: String? = "",
    val dietType: String? = "",
    val fitnessGoal: String? = "",
    val activityLevel: String? = "",
    val name: String? = "",
    val stepGoals: Float? = 0f,
    val calorieGoals: Float? = 0f,
    val waterIntakeGoals: Float? = 0f,
    val isLoading: Boolean = false,
    val isUpdated: Boolean = false,
    val errorMessage: String? = null
)

@HiltViewModel
class UpdateProfileScreenViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    private val apiServicesRepository: ApiServicesRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiStates = MutableStateFlow(ProfileState())
    val uiState: StateFlow<ProfileState> = _uiStates.asStateFlow()

    fun updateUserProfile(context: Context) {
        _uiStates.update { it.copy(isLoading = true, errorMessage = null) }
        viewModelScope.launch(Dispatchers.IO) {

            val token = dataStoreRepository.readToken()

            Log.d("UpdateProfileViewModel", "Final Token: $token")

            if (token.isNullOrEmpty()) {
                Log.e("UpdateProfileViewModel", "Token is null or empty")
                _uiStates.update { it.copy(isLoading = false, errorMessage = "Token is missing") }
                return@launch
            }

            val age = uiState.value.age?.takeIf { it > 0 }
            val gender = uiState.value.gender?.takeIf { it.isNotEmpty() }
            val height = uiState.value.height?.takeIf { it > 0 }
            val weight = uiState.value.weight?.takeIf { it > 0 }
            val dietType = uiState.value.dietType?.takeIf { it.isNotEmpty() }
            val fitnessGoal = uiState.value.fitnessGoal?.takeIf { it.isNotEmpty() }
            val activityLevel = uiState.value.activityLevel?.takeIf { it.isNotEmpty() }
            val waterGoal = uiState.value.waterIntakeGoals?.takeIf { it > 0 }
            val stepCount = uiState.value.stepGoals?.takeIf { it > 0 }
            val calorieCount = uiState.value.calorieGoals?.takeIf { it > 0 }
            val name = uiState.value.name?.takeIf { it.isNotEmpty() }

            apiServicesRepository.updateUserData(
                token = "Bearer $token",
                gender = gender,
                age = age,
                height = height,
                weight = weight,
                dietType = dietType,
                fitnessGoal = fitnessGoal,
                activityLevel = activityLevel,
                waterGoal = waterGoal,
                stepCount = stepCount,
                calorieCount = calorieCount,
                name = name
            ).enqueue(object : Callback<UpdateUserDataDto> {
                override fun onResponse(
                    call: Call<UpdateUserDataDto>,
                    response: Response<UpdateUserDataDto>
                ) {
                    Log.d("API Response", "Code: ${response.code()}")
                    if (response.isSuccessful) {
                        _uiStates.update {
                            it.copy(isLoading = false, isUpdated = true, errorMessage = null)
                        }
                        Log.d("Profile", "Updated Successfully")
                        Toast.makeText(
                            context,
                            "Profile Updated Successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        _uiStates.update {
                            it.copy(
                                isLoading = false,
                                isUpdated = false,
                                errorMessage = "Failed to update profile: ${response.message()}"
                            )
                        }
                        Log.e("Profile", "Update Failed: ${response.errorBody()?.string()}")
                    }
                }

                override fun onFailure(call: Call<UpdateUserDataDto>, t: Throwable) {
                    Log.e("UpdateProfileVM", "Error: ${t.message}", t)
                    _uiStates.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = "An error occurred: ${t.message}"
                        )
                    }
                }
            })
        }
    }

    fun onConfirm(navHostController: NavHostController){
        viewModelScope.launch {
            dataStoreRepository.clearToken()
            navHostController.navigate(AuthRouteScreen.GetStartedScreen.route)
        }
    }

    fun setAge(age: Int) { _uiStates.update { it.copy(age = age) } }
    fun setGender(gender: String) { _uiStates.update { it.copy(gender = gender) } }
    fun setHeight(height: Float) { _uiStates.update { it.copy(height = height) } }
    fun setWeight(weight: Float) { _uiStates.update { it.copy(weight = weight) } }
    fun setDietType(dietType: String) { _uiStates.update { it.copy(dietType = dietType) } }
    fun setFitnessGoal(fitnessGoal: String) { _uiStates.update { it.copy(fitnessGoal = fitnessGoal) } }
    fun setActivityLevel(activityLevel: String) { _uiStates.update { it.copy(activityLevel = activityLevel) } }
    fun setStepGoals(stepGoals: Float) { _uiStates.update { it.copy(stepGoals = stepGoals) } }
    fun setCalorieGoals(calorieGoals: Float) { _uiStates.update { it.copy(calorieGoals = calorieGoals) } }
    fun setWaterIntakeGoals(waterIntakeGoals: Float) { _uiStates.update { it.copy(waterIntakeGoals = waterIntakeGoals) } }
}

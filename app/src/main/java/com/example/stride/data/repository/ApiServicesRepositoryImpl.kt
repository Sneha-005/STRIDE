package com.example.stride.data.repository

import com.example.stride.data.remote.ApiServices
import com.example.stride.data.remote.dto.LandingDto
import com.example.stride.data.remote.dto.LoginResponseDto
import com.example.stride.data.remote.dto.NewPasswordDto
import com.example.stride.data.remote.dto.OauthDto
import com.example.stride.data.remote.dto.OtpLoginDto
import com.example.stride.data.remote.dto.OtpSignUpDto
import com.example.stride.data.remote.dto.ResetPasswordDto
import com.example.stride.data.remote.dto.SignUpDto
import com.example.stride.data.remote.dto.dashboard.CaloriesBurntResponse
import com.example.stride.data.remote.dto.dashboard.CaloriesConsumedResponse
import com.example.stride.data.remote.dto.dashboard.CompleteExerciseDto
<<<<<<< HEAD
import com.example.stride.data.remote.dto.LandingDto
=======
>>>>>>> dev
import com.example.stride.data.remote.dto.dashboard.StepCounterResponse
import com.example.stride.data.remote.dto.dashboard.UpdateUserDataDto
import com.example.stride.data.remote.dto.dashboard.UserDataDto
import com.example.stride.data.remote.dto.dashboard.WorkoutByPartDto
import com.example.stride.data.remote.dto.dashboard.WorkoutExerciseDto
import com.example.stride.data.remote.dto.dashboard.WorkoutForUserDto
import com.example.stride.data.remote.dto.isRegistered
import com.example.stride.domain.models.LandingRequest
import com.example.stride.domain.models.LoginRequest
import com.example.stride.domain.models.NewPasswordRequest
import com.example.stride.domain.models.OauthRequest
import com.example.stride.domain.models.OtpLoginRequest
import com.example.stride.domain.models.OtpSignUpRequest
import com.example.stride.domain.models.RegisterRequest
import com.example.stride.domain.models.ResetPasswordRequest
import com.example.stride.domain.models.SignUpRequest
import com.example.stride.domain.models.dashboard.UpdateUserRequest
import com.example.stride.domain.repository.ApiServicesRepository
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

class ApiServicesRepositoryImpl @Inject constructor(
    private val apiService: ApiServices
) : ApiServicesRepository {
    override suspend fun registerUser(email: String): Call<isRegistered> {
        val registerRequest = RegisterRequest(email)
        return apiService.isRegistered(registerRequest)
    }

    override suspend fun loginUser(
        email: String,
        password: String
    ): Call<LoginResponseDto> {
        val loginRequest = LoginRequest(email, password)
        return apiService.login(loginRequest)
    }

    override suspend fun resetPassword(
        email: String
    ): Call<ResetPasswordDto> {
        val resetPasswordRequest = ResetPasswordRequest(email)
        return apiService.resetPassword(resetPasswordRequest)
    }

    override suspend fun otpLogin(
        email: String,
        otp: String
    ): Call<OtpLoginDto> {
        val otpLoginRequest = OtpLoginRequest(email, otp)
        return apiService.otpLogin(otpLoginRequest)
    }

    override suspend fun newPassword(
        password: String,
        email: String
    ): Call<NewPasswordDto> {
        val newPasswordRequest = NewPasswordRequest(password,email)
        return apiService.newPassword(newPasswordRequest)
    }

    override suspend fun oauth(token: String): Call<OauthDto> {
        val request = OauthRequest(token)
        return apiService.oauth(request)
    }

    override suspend fun signUp(
        userName: String,
        email: String,
        password: String
    ): Call<SignUpDto> {
        val signUpRequest = SignUpRequest(userName, email, password)
        return apiService.signUp(signUpRequest)
    }

    override suspend fun otpSignUp(
        email: String,
        otp: String
    ): Call<OtpSignUpDto> {
        val otpSignUpRequest = OtpSignUpRequest(email, otp)
        return apiService.otpSignUp(otpSignUpRequest)
    }

    override suspend fun landingData(
        token: String,
        gender: String,
        age: Int,
        height: Float,
        weight: Float,
        dietType: String,
        fitnessGoal: String,
        activityLevel: String,
        waterGoal: Float,
        stepCount: Float,
        calorieCount: Float
    ): Call<LandingDto> {
        val landingRequest = LandingRequest(gender, age, height, weight, dietType, fitnessGoal, activityLevel, waterGoal, stepCount, calorieCount)
        return apiService.landingData(token,landingRequest)
    }

    override suspend fun getUserData(token: String): Call<UserDataDto> {
        return apiService.getCurrentUser(token)
    }

    override suspend fun addSteps(token: String,stepCount: Int): Call<StepCounterResponse> {
        return apiService.addSteps(token,stepCount)
    }

    override suspend fun getCaloryConsumed(token: String,z: Int): Call<CaloriesConsumedResponse> {
        return apiService.getCaloryConsumed(token,z)
    }

    override suspend fun getCalorieBurnt(token: String,z: Int): Call<CaloriesBurntResponse> {
        return apiService.getCalorieBurnt(token,z)
    }

    override suspend fun updateUserData(
        token: String,
        gender: String?,
        age: Int?,
        height: Float?,
        weight: Float?,
        dietType: String?,
        fitnessGoal: String?,
        activityLevel: String?,
        waterGoal: Float?,
        stepCount: Float?,
        calorieCount: Float?,
        name: String?
    ): Call<UpdateUserDataDto> {
        val updateUserRequest = UpdateUserRequest(
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
        )
        return apiService.updateUserData(token, updateUserRequest)
    }

    override suspend fun workoutByPart(
        token: String,
        bodyPart: String
    ): Call<WorkoutByPartDto> {
        return apiService.workoutByPart(token, bodyPart)
    }

    override suspend fun getWorkoutExercises(
        token: String,
        workoutId: String
    ): Call<WorkoutExerciseDto> {
        return apiService.getWorkoutExercises(token, workoutId)
    }

    override suspend fun getWorkoutForUser(
        token: String
    ): Call<List<WorkoutForUserDto>>{
        return apiService.getWorkoutForUser(token)
    }

    override suspend fun completeExercise(
        token: String,
        exerciseId: String
    ): Call<CompleteExerciseDto> {
        return apiService.completeExercise(token, exerciseId)
    }

    override suspend fun completeWorkout(
        token: String,
        workoutPlanId: String,
        exerciseId: String
    ): Call<CompleteExerciseDto> {
        return apiService.completeWorkout(token, workoutPlanId, exerciseId)
    }

    override suspend fun getAllWorkouts(token: String): Call<List<WorkoutForUserDto>> {
        return apiService.getAllWorkouts(token)
    }
}

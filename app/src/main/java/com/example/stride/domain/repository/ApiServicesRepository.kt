package com.example.stride.domain.repository

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
import com.example.stride.data.remote.dto.dashboard.StepCounterResponse
import com.example.stride.data.remote.dto.dashboard.UpdateUserDataDto
import com.example.stride.data.remote.dto.dashboard.UserDataDto
import com.example.stride.data.remote.dto.dashboard.WorkoutByPartDto
import com.example.stride.data.remote.dto.dashboard.WorkoutExerciseDto
import com.example.stride.data.remote.dto.dashboard.WorkoutForUserDto
import com.example.stride.data.remote.dto.isRegistered
import retrofit2.Call
import retrofit2.Response

interface ApiServicesRepository {
    suspend fun registerUser(
        email: String
    ): Call<isRegistered>

    suspend fun loginUser(
        email: String,
        password: String
    ): Call<LoginResponseDto>

    suspend fun resetPassword(
        email: String
    ):Call<ResetPasswordDto>

    suspend fun otpLogin(
        email: String,
        otp: String
    ):Call<OtpLoginDto>

    suspend fun newPassword(
        password: String,
        email: String
    ): Call<NewPasswordDto>

    suspend fun oauth(
        token: String
    ): Call<OauthDto>

    suspend fun signUp(
        userName: String,
        email: String,
        password: String
    ): Call<SignUpDto>

    suspend fun otpSignUp(
        email: String,
        otp: String
    ):Call<OtpSignUpDto>

    suspend fun landingData(
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
    ): Call<LandingDto>

    suspend fun getUserData(
        token: String
    ): Call<UserDataDto>

    suspend fun updateUserData(
        token: String,
        gender: String? = null,
        age: Int? = null,
        height: Float? = null,
        weight: Float? = null,
        dietType: String? = null,
        fitnessGoal: String? = null,
        activityLevel: String? = null,
        waterGoal: Float? = null,
        stepCount: Float? = null,
        calorieCount: Float? = null,
        name: String? = null
    ): Call<UpdateUserDataDto>

    suspend fun addSteps(
        token: String,
        stepCount: Int
    ): Call<StepCounterResponse>

    suspend fun getCaloryConsumed(
        token: String,
        z: Int
    ): Call<CaloriesConsumedResponse>

    suspend fun getCalorieBurnt(
        token: String,
        z: Int
    ): Call<CaloriesBurntResponse>

    suspend fun workoutByPart(
        token: String,
        bodyPart: String
    ): Call<WorkoutByPartDto>

    suspend fun getWorkoutExercises(
        token: String,
        workoutId: String
    ): Call<WorkoutExerciseDto>

    suspend fun getWorkoutForUser(
        token: String
    ): Call<List<WorkoutForUserDto>>

    suspend fun completeExercise(
        token: String,
        exerciseId: String
    ): Call<CompleteExerciseDto>

    suspend fun completeWorkout(
        token: String,
        workoutPlanId: String,
        exerciseId: String
    ): Call<CompleteExerciseDto>

    suspend fun getAllWorkouts(
        token: String
    ): Call<List<WorkoutForUserDto>>
}

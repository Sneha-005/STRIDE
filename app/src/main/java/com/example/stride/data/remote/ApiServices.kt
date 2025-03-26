package com.example.stride.data.remote

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
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {

    @POST("v1/auth/ifRegistered")
    fun isRegistered(
        @Body registerRequest: RegisterRequest
    ): Call<isRegistered>

    @POST("v1/auth/authenticate")
    fun login(
        @Body loginRequest: LoginRequest
    ): Call<LoginResponseDto>

    @POST("v1/auth/forgot-password")
    fun resetPassword(
        @Body resetPasswordRequest: ResetPasswordRequest
    ): Call<ResetPasswordDto>

    @POST("v1/auth/verify-Email")
    fun otpLogin(
        @Body otpLoginRequest: OtpLoginRequest
    ): Call<OtpLoginDto>

    @PUT("v1/auth/reset-password")
    fun newPassword(
        @Body newPasswordRequest: NewPasswordRequest
    ): Call<NewPasswordDto>

    @POST("v1/auth/google-login")
    fun oauth(
        @Body oauthRequest: OauthRequest
    ): Call<OauthDto>

    @POST("register")
    fun signUp(
        @Body signUpRequest: SignUpRequest
    ): Call<SignUpDto>

    @POST("validate")
    fun otpSignUp(
        @Body otpSignUpRequest : OtpSignUpRequest
    ): Call<OtpSignUpDto>

    @POST("user-data/add")
    fun landingData(
        @Header("Authorization") token: String,
        @Body landingRequest: LandingRequest
    ): Call<LandingDto>

    @GET("user-data/getCurrentuser")
    fun getCurrentUser(
        @Header("Authorization") token: String
    ): Call<UserDataDto>

    @POST("steps/add/{stepCount}")
    fun addSteps(
        @Header("Authorization") token: String,
        @Query("stepCount") stepCount: Int
    ): Call<StepCounterResponse>

    @GET("foodlogs/food/calories/{z}")
    fun getCaloryConsumed(
        @Header("Authorization") token: String,
        @Query("z") z: Int
    ): Call<CaloriesConsumedResponse>

    @GET("exercise-session/calories/{z}")
    fun getCalorieBurnt(
        @Header("Authorization") token: String,
        @Query("z") z: Int
    ): Call<CaloriesBurntResponse>

    @PUT("user-data/update")
    fun updateUserData(
        @Header("Authorization") token: String,
        @Body request: UpdateUserRequest
    ): Call<UpdateUserDataDto>

    @GET("exercise/bodyPart/{bodyPart}")
    fun workoutByPart(
        @Header("Authorization") token: String,
        @Path("bodyPart") bodyPart: String
    ): Call<WorkoutByPartDto>

    @GET("workout/{workoutId}/exercises")
    fun getWorkoutExercises(
        @Header("Authorization") token: String,
        @Path("workoutId") workoutId: String
    ): Call<WorkoutExerciseDto>

    @GET("workout/user-plan")
    fun getWorkoutForUser(
        @Header("Authorization") token: String
    ): Call<List<WorkoutForUserDto>>

    @POST("workout-session/complete-exercise/{workoutPlanId}/{exerciseId}")
    fun completeWorkout(
        @Header("Authorization") token: String,
        @Path("workoutPlanId") workoutPlanId: String,
        @Path("exerciseId") exerciseId: String
    ): Call<CompleteExerciseDto>

    @GET("workout/all")
    fun getAllWorkouts(
        @Header("Authorization") token: String
    ): Call<List<WorkoutForUserDto>>

    @POST("workout-session/complete-exercise/{exerciseId}")
    fun completeExercise(
        @Header("Authorization") token: String,
        @Path("exerciseId") exerciseId: String
    ): Call<CompleteExerciseDto>
}
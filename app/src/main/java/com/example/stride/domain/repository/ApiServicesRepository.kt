package com.example.stride.domain.repository

import com.example.stride.data.remote.isRegistered
import com.example.stride.domain.models.RegisterRequest
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Multipart

interface ApiServicesRepository {
    suspend fun registerUser(
        email: String
    ): Call<isRegistered>
}

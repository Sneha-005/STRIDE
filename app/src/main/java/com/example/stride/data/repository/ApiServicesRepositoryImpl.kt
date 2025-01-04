package com.example.stride.data.repository

import com.example.stride.data.remote.ApiServices
import com.example.stride.data.remote.isRegistered
import com.example.stride.domain.models.RegisterRequest
import com.example.stride.domain.repository.ApiServicesRepository
import okhttp3.MultipartBody
import retrofit2.Call
import javax.inject.Inject

class ApiServicesRepositoryImpl @Inject constructor(
    private val apiService: ApiServices
) : ApiServicesRepository {
    override suspend fun registerUser(email: String): Call<isRegistered> {
        val emailMap = mapOf("email" to email)
        return apiService.isRegistered(emailMap)
    }
}

package com.example.stride.data.remote

import com.example.stride.domain.models.RegisterRequest
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiServices {

    @POST("ifRegistered")
    fun isRegistered(
        @Body email: Map<String, String>
    ): Call<isRegistered>
}
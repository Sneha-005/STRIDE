package com.example.stride.di

import com.example.stride.data.local.DataStoreRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val dataStoreRepository: DataStoreRepository
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking { dataStoreRepository.readToken() } // Use runBlocking sparingly
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()
        return chain.proceed(request)
    }
}

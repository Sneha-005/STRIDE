package com.example.stride.domain.usecases

import android.util.Log
import com.example.stride.data.remote.isRegistered
import com.example.stride.domain.repository.ApiServicesRepository
import com.example.stride.utility.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import timber.log.Timber
import javax.inject.Inject

class PostRegisterUser @Inject constructor(
    private val apiServicesRepository: ApiServicesRepository
) {
    operator fun invoke(
        email: String
    ): Flow<Resource<isRegistered>> = flow {
        try {
            emit(Resource.Loading())

            val response = apiServicesRepository.registerUser(
                email = email
            )
            Timber.tag("PostRegisterUser").e("Response: %s", response)

        } catch (e: Exception) {
            Timber.tag("PostRegisterUser").e("Error: %s", e.localizedMessage)
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }
}
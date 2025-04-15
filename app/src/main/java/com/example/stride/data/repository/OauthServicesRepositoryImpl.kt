package com.example.stride.data.repository

import com.example.stride.data.remote.OauthServices
import com.example.stride.data.remote.dto.AccessTokenResponse
import com.example.stride.domain.repository.OauthServiceRepository
import retrofit2.Call
import javax.inject.Inject

class OauthServicesRepositoryImpl @Inject constructor(
    private val apiService: OauthServices
) : OauthServiceRepository {

    override suspend fun getAccessToken(
        code: String,
        clientId: String,
        clientSecret: String,
        redirectUri: String,
        grantType: String
    ): Call<AccessTokenResponse> {
        return apiService.getAccessToken(code, clientId, clientSecret, redirectUri, grantType)
    }
}

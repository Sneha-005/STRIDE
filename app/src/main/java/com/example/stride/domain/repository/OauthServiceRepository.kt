package com.example.stride.domain.repository

import com.example.stride.data.remote.dto.AccessTokenResponse
import retrofit2.Call

interface OauthServiceRepository {

    suspend fun getAccessToken(
        code: String,
        clientId: String,
        clientSecret: String,
        redirectUri: String,
        grantType: String
    ): Call<AccessTokenResponse>

}
package com.example.stride.data.remote

import com.example.stride.data.remote.dto.AccessTokenResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface OauthServices {
    @POST("convert-token")
    @FormUrlEncoded
    suspend fun getAccessToken(
        @Field("code") code: String,
        @Field("client_id") clientId: String,
        @Field("clientSecret") clientSecret: String,
        @Field("redirectUri") redirectUri: String,
        @Field("grantType") grantType: String
    ): Call<AccessTokenResponse>
}
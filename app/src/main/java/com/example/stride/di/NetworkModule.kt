package com.example.stride.di

import android.app.Application
import android.app.NotificationManager
import android.content.Context
import android.hardware.SensorManager
import com.example.stride.data.local.DataStoreRepository
import com.example.stride.data.local.ResetDataStore
import com.example.stride.data.remote.ApiServices
import com.example.stride.data.remote.OauthServices
import com.example.stride.data.repository.ApiServicesRepositoryImpl
import com.example.stride.data.repository.OauthServicesRepositoryImpl
import com.example.stride.domain.repository.ApiServicesRepository
import com.example.stride.domain.repository.OauthServiceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.runBlocking
import retrofit2.Retrofit
import com.google.android.gms.auth.api.identity.Identity
import okhttp3.OkHttpClient
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class OauthRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RegularRetrofit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideDataStoreRepository(@ApplicationContext context: Context): DataStoreRepository {
        return DataStoreRepository(context)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Provides
    @Singleton
    @OauthRetrofit
    fun providesOauthRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://accounts.google.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @RegularRetrofit
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://fitness-tracker-hbou.onrender.com/api/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideOauthService(@OauthRetrofit retrofit: Retrofit): OauthServices {
        return retrofit.create(OauthServices::class.java)
    }

    @Provides
    @Singleton
    fun provideApiService(@RegularRetrofit retrofit: Retrofit): ApiServices {
        return retrofit.create(ApiServices::class.java)
    }

    @Provides
    @Singleton
    fun provideApiServicesRepository(apiService: ApiServices): ApiServicesRepository {
        return ApiServicesRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideOauthServiceRepository(oauthServices: OauthServices): OauthServiceRepository {
        return OauthServicesRepositoryImpl(oauthServices)
    }

    @Provides
    @Singleton
    fun provideApiKeys(dataStoreRepository: DataStoreRepository): String {
        return runBlocking {
            dataStoreRepository.readToken() ?: ""
        }
    }

    @Provides
    @Singleton
    fun provideResetDataStore(context: Context): ResetDataStore {
        return ResetDataStore(context)
    }

}

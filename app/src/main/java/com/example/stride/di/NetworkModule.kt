package com.example.stride.di

import android.content.Context
import com.example.stride.data.local.DataStoreRepository
import com.example.stride.data.remote.ApiServices
import com.example.stride.data.repository.ApiServicesRepositoryImpl
import com.example.stride.domain.repository.ApiServicesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.runBlocking
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule
{

    @Provides
    @Singleton
    fun provideDataStoreRepository(@ApplicationContext context: Context): DataStoreRepository {
        return DataStoreRepository(context)
    }

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }
    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://fitness-tracker-hbou.onrender.com/api/v1/auth/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiServices {
        return retrofit.create(ApiServices::class.java)
    }

    @Provides
    @Singleton
    fun provideApiServicesRepository(apiService: ApiServices): ApiServicesRepository {
        return ApiServicesRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideApiKeys(dataStoreRepository: DataStoreRepository): String{
        return runBlocking {
            dataStoreRepository.readToken() ?: ""
        }
    }
}
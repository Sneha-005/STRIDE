package com.example.stride.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking

val Context.dataStore by preferencesDataStore(name = "app_preferences")



class ResetDataStore(private val context: Context) {
    companion object {
        private val RESET_PASSWORD_TOKEN_KEY = stringPreferencesKey("reset_password_token")
    }

    suspend fun saveResetPasswordToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[RESET_PASSWORD_TOKEN_KEY] = token
        }
    }

    val resetPasswordToken: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[RESET_PASSWORD_TOKEN_KEY]
    }

    suspend fun getResetPasswordToken(): String? {
        return context.dataStore.data.map { preferences ->
            preferences[RESET_PASSWORD_TOKEN_KEY]
        }.firstOrNull()
    }


}
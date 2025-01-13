// Top-level declaration for user_preferences
package com.example.stride.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

private val Context.userPreferencesDataStore by preferencesDataStore(name = "user_preferences")

class DataStoreRepository(private val context: Context) {

    private val TOKEN_KEY = stringPreferencesKey("token")

    val token: Flow<String?> = context.userPreferencesDataStore.data
        .map { preferences ->
            preferences[TOKEN_KEY]
        }

    suspend fun saveToken(token: String) {
        context.userPreferencesDataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
        }
    }

    suspend fun clearToken() {
        context.userPreferencesDataStore.edit { preferences ->
            preferences.remove(TOKEN_KEY)
        }
    }

    suspend fun readToken(): String? {
        return context.userPreferencesDataStore.data.map { preferences ->
            preferences[TOKEN_KEY]
        }.firstOrNull()
    }
}
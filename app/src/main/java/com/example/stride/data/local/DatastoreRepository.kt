package com.example.stride.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.userPreferencesDataStore by preferencesDataStore(name = "user_preferences")

class DataStoreRepository(private val context: Context) {

    private val TOKEN_KEY = stringPreferencesKey("token")
    private val OAUTH_KEY = stringPreferencesKey("oauth")

    val token: Flow<String?> = context.userPreferencesDataStore.data
        .map { preferences ->
            preferences[TOKEN_KEY]
        }

    val oauth: Flow<String?> = context.userPreferencesDataStore.data
        .map { preferences ->
            preferences[OAUTH_KEY]
        }

    suspend fun saveToken(token: String) {
        context.userPreferencesDataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
            preferences.remove(OAUTH_KEY)
        }
    }

    suspend fun saveOauth(oauth: String) {
        context.userPreferencesDataStore.edit { preferences ->
            preferences[OAUTH_KEY] = oauth
            preferences.remove(TOKEN_KEY)
        }
    }
    suspend fun clearToken() {
        context.userPreferencesDataStore.edit { preferences ->
            preferences.remove(TOKEN_KEY)
            preferences.remove(OAUTH_KEY)
        }
    }

    suspend fun readToken(): String? {
        val preferences = context.userPreferencesDataStore.data.first()
        return preferences[TOKEN_KEY] ?: preferences[OAUTH_KEY]
    }

}
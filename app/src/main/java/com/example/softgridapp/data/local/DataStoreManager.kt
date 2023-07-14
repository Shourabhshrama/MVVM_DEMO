package com.example.softgridapp.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException



class DataStoreManager(private val context: Context) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "device_token_datastore")
    companion object {
        private val DEVICE_TOKEN_KEY = stringPreferencesKey("device_token")
    }

    suspend fun saveDeviceToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[DEVICE_TOKEN_KEY] = token
        }
    }

    val deviceTokenFlow: Flow<String?> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[DEVICE_TOKEN_KEY]
        }
}

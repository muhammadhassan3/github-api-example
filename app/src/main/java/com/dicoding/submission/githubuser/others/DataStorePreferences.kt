package com.dicoding.submission.githubuser.others

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStorePreferences private constructor(private val datastore: DataStore<Preferences>) {

    fun getDarkMode(): Flow<Boolean> {
        return datastore.data.map { preferences ->
            preferences[DARK_MODE_KEY] ?: false
        }
    }

    suspend fun setDarkMode(value: Boolean) {
        datastore.edit { preferences ->
            preferences[DARK_MODE_KEY] = value
        }
    }

    companion object {
        private val DARK_MODE_KEY = booleanPreferencesKey("dark_mode")

        @Volatile
        private var INSTANCE: DataStorePreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): DataStorePreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = DataStorePreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}
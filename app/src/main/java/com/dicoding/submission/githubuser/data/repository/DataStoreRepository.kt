package com.dicoding.submission.githubuser.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.dicoding.submission.githubuser.others.DataStorePreferences

class DataStoreRepository(private val dataStore: DataStorePreferences) {
    fun getDarkMode(): LiveData<Boolean> = dataStore.getDarkMode().asLiveData()

    suspend fun setDarkMode(value: Boolean) = dataStore.setDarkMode(value)
}
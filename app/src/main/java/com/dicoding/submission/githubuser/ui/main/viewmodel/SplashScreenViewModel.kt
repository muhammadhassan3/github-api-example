package com.dicoding.submission.githubuser.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.submission.githubuser.data.repository.DataStoreRepository
import com.dicoding.submission.githubuser.others.DataStorePreferences

class SplashScreenViewModel(dataStore: DataStorePreferences) : ViewModel() {
    private val dataStoreRepository = DataStoreRepository(dataStore)
    fun getDarkTheme(): LiveData<Boolean> {
        return dataStoreRepository.getDarkMode()
    }
}
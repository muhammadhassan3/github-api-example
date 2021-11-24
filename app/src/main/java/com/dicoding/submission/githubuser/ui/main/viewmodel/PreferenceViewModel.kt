package com.dicoding.submission.githubuser.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.submission.githubuser.data.repository.DataStoreRepository
import com.dicoding.submission.githubuser.others.DataStorePreferences
import kotlinx.coroutines.launch

class PreferenceViewModel(dataStore: DataStorePreferences) : ViewModel() {
    private val dataStoreRepository = DataStoreRepository(dataStore)
    fun getDarkTheme(): LiveData<Boolean> {
        return dataStoreRepository.getDarkMode()
    }

    fun setDarkTheme(value: Boolean) {
        viewModelScope.launch {
            dataStoreRepository.setDarkMode(value)
        }
    }
}
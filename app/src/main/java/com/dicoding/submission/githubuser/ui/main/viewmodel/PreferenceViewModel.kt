/*******************************************************************************
 * Created by muham on 25/09/2021, 12:18 PM
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 25/09/2021, 12:18 PM
 ******************************************************************************/

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
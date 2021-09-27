/*******************************************************************************
 * Created by muham on 25/09/2021, 12:18 PM
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 25/09/2021, 12:18 PM
 ******************************************************************************/

package com.dicoding.submission.githubuser.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.submission.githubuser.others.DataStorePreferences
import kotlinx.coroutines.launch

class PreferenceViewModel(private val dataStore: DataStorePreferences) : ViewModel() {

    fun getDarkTheme(): LiveData<Boolean> {
        return dataStore.getDarkMode().asLiveData()
    }

    fun setDarkTheme(value: Boolean) {
        viewModelScope.launch {
            dataStore.setDarkMode(value)
        }
    }
}
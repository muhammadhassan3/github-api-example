/*******************************************************************************
 * Created by muham on 24/11/2021, 6:03 PM
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 24/11/2021, 6:03 PM
 ******************************************************************************/

package com.dicoding.submission.githubuser.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.dicoding.submission.githubuser.others.DataStorePreferences

class DataStoreRepository(private val dataStore: DataStorePreferences) {
    fun getDarkMode(): LiveData<Boolean> = dataStore.getDarkMode().asLiveData()

    suspend fun setDarkMode(value: Boolean) = dataStore.setDarkMode(value)
}
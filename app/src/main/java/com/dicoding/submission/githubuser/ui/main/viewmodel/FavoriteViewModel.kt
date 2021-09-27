/*******************************************************************************
 * Created by muham on 12/09/2021, 10:16 AM
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 09/09/2021, 11:30 AM
 ******************************************************************************/

package com.dicoding.submission.githubuser.ui.main.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.submission.githubuser.data.database.entity.Favorite
import com.dicoding.submission.githubuser.data.repository.FavoriteRepository
import kotlinx.coroutines.launch

class FavoriteViewModel(app: Application) : AndroidViewModel(app) {
    private val repository = FavoriteRepository(app)
    val data = repository.getAll()


    fun insert(favorite: Favorite) {
        viewModelScope.launch {
            repository.insert(favorite)
        }
    }

    fun deleteAll() {
        repository.deleteAll()
    }
}
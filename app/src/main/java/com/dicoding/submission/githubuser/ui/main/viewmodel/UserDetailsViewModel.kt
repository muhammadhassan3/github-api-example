/*******************************************************************************
 * Created by muham on 12/09/2021, 10:16 AM
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 12/08/2021, 8:14 AM
 ******************************************************************************/

package com.dicoding.submission.githubuser.ui.main.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.submission.githubuser.data.database.entity.Favorite
import com.dicoding.submission.githubuser.data.model.UserDetails
import com.dicoding.submission.githubuser.data.repository.FavoriteRepository
import com.dicoding.submission.githubuser.data.repository.UserRepository
import com.dicoding.submission.githubuser.others.ApiResponse

class UserDetailsViewModel(var app: Application) : AndroidViewModel(app) {
    private val repository = UserRepository()
    private val favoriteRepository = FavoriteRepository(app)
    val data: MutableLiveData<ApiResponse<UserDetails>> = repository.getUserData()

    fun getUserDetails(username: String) {
        data.postValue(repository.getUserbyUsername(username).value)
    }

    fun getFavoriteUser(username: String): LiveData<Favorite?> {
        return favoriteRepository.getByUsername(username)
    }

    fun addFavorite(favorite: Favorite) {
        favoriteRepository.insert(favorite)
    }

    fun deleteFavorite(favorite: Favorite) {
        favorite.username?.let {
            favoriteRepository.deleteByusername(it)
        }
    }
}
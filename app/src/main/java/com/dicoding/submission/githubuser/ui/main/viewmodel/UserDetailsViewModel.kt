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
import androidx.lifecycle.viewModelScope
import com.dicoding.submission.githubuser.data.database.entity.Favorite
import com.dicoding.submission.githubuser.data.model.UserDetails
import com.dicoding.submission.githubuser.data.repository.FavoriteRepository
import com.dicoding.submission.githubuser.data.repository.UserRepository
import com.dicoding.submission.githubuser.others.ApiResponse
import kotlinx.coroutines.launch

class UserDetailsViewModel(app: Application) : AndroidViewModel(app) {
    private val repository = UserRepository()
    private val favoriteRepository = FavoriteRepository(app)
    private val data: MutableLiveData<ApiResponse<UserDetails>> = repository.getUserData()
    private val favoriteData: MutableLiveData<ApiResponse<Favorite>> =
        favoriteRepository.getSingleData()

    fun getData(): MutableLiveData<ApiResponse<UserDetails>> = data

    fun getFavoriteData(): LiveData<ApiResponse<Favorite>> = favoriteData

    fun getUserDetails(username: String) {
        data.postValue(repository.getUserbyUsername(username).value)
    }

    fun getFavoriteUser(username: String) {
        viewModelScope.launch {
            favoriteData.postValue(favoriteRepository.getByUsername(username).value)
        }
    }

    fun addFavorite(favorite: Favorite) {
        viewModelScope.launch {
            favoriteRepository.insert(favorite)
        }
    }

    fun deleteFavorite(favorite: Favorite) {
        viewModelScope.launch {
            favorite.username?.let {
                favoriteRepository.deleteByusername(it)
            }
        }
    }
}
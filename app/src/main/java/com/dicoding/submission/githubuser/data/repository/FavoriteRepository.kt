/*******************************************************************************
 * Created by muham on 12/09/2021, 10:16 AM
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 09/09/2021, 11:26 AM
 ******************************************************************************/

package com.dicoding.submission.githubuser.data.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.submission.githubuser.data.database.GithubDatabase
import com.dicoding.submission.githubuser.data.database.dao.FavoriteDao
import com.dicoding.submission.githubuser.data.database.entity.Favorite
import com.dicoding.submission.githubuser.others.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FavoriteRepository(var application: Application) {
    private var favoriteDao: FavoriteDao
    private var database = GithubDatabase.getInstance(application)
    private var data = MutableLiveData<ApiResponse<List<Favorite>>>()
    private var singleData = MutableLiveData<ApiResponse<Favorite>>()

    companion object {
        private const val TAG = "FavoriteRepository"
    }

    fun getData(): MutableLiveData<ApiResponse<List<Favorite>>> = data

    fun getSingleData(): MutableLiveData<ApiResponse<Favorite>> = singleData

    init {
        favoriteDao = database.favoriteDao()
        data.value = ApiResponse.noData()
    }

    suspend fun insert(favorite: Favorite) {
        withContext(Dispatchers.IO) {
            favorite.username?.let {
                Log.i(TAG, "insert: $favorite")
                favoriteDao.insert(favorite)
                getByUsername(it)
            }
        }
    }

    fun getAll(): MutableLiveData<ApiResponse<List<Favorite>>> {
        val result = favoriteDao.getAll()
        if (result.value != null && result.value!!.isNotEmpty()) {
            data.postValue(ApiResponse.success(result.value))
        } else data.postValue(ApiResponse.noData())

        return data
    }

    fun deleteAll() {
        favoriteDao.deleteAll()
        getAll()
    }

    suspend fun getByUsername(username: String): LiveData<ApiResponse<Favorite>> {
        withContext(Dispatchers.IO) {
            val result = favoriteDao.findByUsername(username)
            Log.i(TAG, "getByUsername: $username result = ${result}")
            if (result != null) {
                singleData.postValue(ApiResponse.success(result))
            } else singleData.postValue(ApiResponse.noData())
        }
        return singleData
    }

    suspend fun deleteByusername(username: String) {
        withContext(Dispatchers.IO) {
            Log.i(TAG, "deleteByusername: $username")
            favoriteDao.deleteByUsername(username)
            getByUsername(username)
        }
    }
}
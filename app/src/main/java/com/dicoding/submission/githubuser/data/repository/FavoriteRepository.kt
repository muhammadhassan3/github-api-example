/*******************************************************************************
 * Created by muham on 12/09/2021, 10:21 AM
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 12/09/2021, 10:21 AM
 ******************************************************************************/

/*******************************************************************************
 * Created by muham on 12/09/2021, 10:16 AM
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 09/09/2021, 11:26 AM
 ******************************************************************************/

package com.dicoding.submission.githubuser.data.repository

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.dicoding.submission.githubuser.data.database.GithubDatabase
import com.dicoding.submission.githubuser.data.database.dao.FavoriteDao
import com.dicoding.submission.githubuser.data.database.entity.Favorite
import com.dicoding.submission.githubuser.others.ApiResponse

class FavoriteRepository(application: Application) {
    private var favoriteDao: FavoriteDao
    private var database = GithubDatabase.getInstance(application)
    private var data = MutableLiveData<ApiResponse<List<Favorite>>>()

    fun getData(): MutableLiveData<ApiResponse<List<Favorite>>> = data

    init {
        favoriteDao = database.favoriteDao()
        data.value = ApiResponse.noData()
    }

    fun insert(favorite: Favorite) {
        favoriteDao.insert(favorite)
        getAll()
    }

    fun delete(favorite: Favorite) {
        favoriteDao.delete(favorite)
        getAll()
    }

    fun getAll(): MutableLiveData<ApiResponse<List<Favorite>>> {
        val result = favoriteDao.getAll()
        if (result.value != null && result.value!!.isNotEmpty()) {
            data.value = ApiResponse.success(result.value)
        } else data.value = ApiResponse.noData()

        return data
    }

    fun deleteAll() {
        favoriteDao.deleteAll()
        getAll()
    }
}
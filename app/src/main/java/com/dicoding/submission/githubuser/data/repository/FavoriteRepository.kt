/*******************************************************************************
 * Created by muham on 12/09/2021, 10:16 AM
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 09/09/2021, 11:26 AM
 ******************************************************************************/

package com.dicoding.submission.githubuser.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.dicoding.submission.githubuser.data.database.GithubDatabase
import com.dicoding.submission.githubuser.data.database.dao.FavoriteDao
import com.dicoding.submission.githubuser.data.database.entity.Favorite
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {
    private var favoriteDao: FavoriteDao
    private var database = GithubDatabase.getInstance(application)
    private val executor = Executors.newSingleThreadExecutor()

    companion object;

    init {
        favoriteDao = database.favoriteDao()
    }

    fun insert(favorite: Favorite) {
        favorite.username?.let {
            executor.execute {
                favoriteDao.insert(favorite)
            }
        }
    }

    fun getAll(): LiveData<List<Favorite>?> {
        return favoriteDao.getAll()
    }

    fun deleteAll() {
        executor.execute {
            favoriteDao.deleteAll()
        }
    }

    fun getByUsername(username: String): LiveData<Favorite?> {
        return favoriteDao.findByUsername(username)
    }

    fun deleteByusername(username: String) {
        executor.execute {
            favoriteDao.deleteByUsername(username)
        }
    }
}
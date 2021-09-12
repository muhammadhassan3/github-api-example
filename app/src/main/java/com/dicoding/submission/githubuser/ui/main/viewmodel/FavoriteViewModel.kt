/*******************************************************************************
 * Created by muham on 12/09/2021, 12:20 PM
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 12/09/2021, 12:20 PM
 ******************************************************************************/

/*******************************************************************************
 * Created by muham on 12/09/2021, 12:19 PM
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 12/09/2021, 10:21 AM
 ******************************************************************************/

/*******************************************************************************
 * Created by muham on 12/09/2021, 10:21 AM
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 12/09/2021, 10:17 AM
 ******************************************************************************/

/*******************************************************************************
 * Created by muham on 12/09/2021, 10:16 AM
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 09/09/2021, 11:30 AM
 ******************************************************************************/

package com.dicoding.submission.githubuser.ui.main.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.dicoding.submission.githubuser.data.database.entity.Favorite
import com.dicoding.submission.githubuser.data.repository.FavoriteRepository
import com.dicoding.submission.githubuser.others.ApiResponse

class FavoriteViewModel(app: Application) : AndroidViewModel(app) {
    private val repository = FavoriteRepository(app)
    private val data = repository.getData()

    fun getData(): MutableLiveData<ApiResponse<List<Favorite>>> = data

    fun insert(favorite: Favorite) {
        repository.insert(favorite)
    }

    fun delete(favorite: Favorite) {
        repository.delete(favorite)
    }

    fun deleteAll() {
        repository.deleteAll()
    }

    fun getAll() {
        data.value = repository.getAll().value
    }
}
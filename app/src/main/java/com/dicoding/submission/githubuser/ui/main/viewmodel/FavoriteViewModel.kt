package com.dicoding.submission.githubuser.ui.main.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.dicoding.submission.githubuser.data.repository.FavoriteRepository

class FavoriteViewModel(app: Application) : AndroidViewModel(app) {
    private val repository = FavoriteRepository(app)
    val data = repository.getAll()


    fun deleteAll() {
        repository.deleteAll()
    }
}
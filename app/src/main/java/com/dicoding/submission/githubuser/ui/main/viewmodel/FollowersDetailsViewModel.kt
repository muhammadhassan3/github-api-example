package com.dicoding.submission.githubuser.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.submission.githubuser.data.model.UserDetails
import com.dicoding.submission.githubuser.data.repository.UserRepository
import com.dicoding.submission.githubuser.others.ApiResponse

class FollowersDetailsViewModel : ViewModel() {
    private val repository = UserRepository()
    private var data: MutableLiveData<ApiResponse<List<UserDetails>>>? =
        repository.getFollowersData()

    fun getData(url: String) {
        data?.postValue(repository.getFollowersList(url).value)
    }

    fun getData(): MutableLiveData<ApiResponse<List<UserDetails>>>? = data

    override fun onCleared() {
        data = null
        super.onCleared()
    }
}
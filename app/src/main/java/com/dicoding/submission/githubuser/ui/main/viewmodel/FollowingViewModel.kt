package com.dicoding.submission.githubuser.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.submission.githubuser.data.model.UserDetails
import com.dicoding.submission.githubuser.data.repository.UserRepository
import com.dicoding.submission.githubuser.others.ApiResponse

class FollowingViewModel : ViewModel() {
    private val repository = UserRepository()
    private var data: MutableLiveData<ApiResponse<List<UserDetails>>>? =
        repository.getFollowingData()

    fun getData(): MutableLiveData<ApiResponse<List<UserDetails>>>? = data

    fun getData(username: String) {
        data?.postValue(repository.getFollowingList(username).value)
    }

    override fun onCleared() {
        data = null
        super.onCleared()
    }
}
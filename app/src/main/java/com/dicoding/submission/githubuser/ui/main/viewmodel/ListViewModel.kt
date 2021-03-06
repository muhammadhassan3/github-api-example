package com.dicoding.submission.githubuser.ui.main.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.submission.githubuser.data.model.UserDetails
import com.dicoding.submission.githubuser.data.repository.UserRepository
import com.dicoding.submission.githubuser.others.ApiResponse

class ListViewModel : ViewModel() {
    private val repository = UserRepository()
    private var data: MutableLiveData<ApiResponse<List<UserDetails>>>? = repository.getData()

    fun getUserListFromLocal(context: Context) {
        data?.postValue(repository.getLocalData(context).value)

    }

    fun getUserByQuery(query: String) {
        data?.postValue(repository.getUserListByQuery(query).value)
    }

    fun getData(): MutableLiveData<ApiResponse<List<UserDetails>>>? {
        return data
    }

    override fun onCleared() {
        data = null
        super.onCleared()
    }
}
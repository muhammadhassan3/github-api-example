/*******************************************************************************
 * Created by muham on 12/09/2021, 10:16 AM
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 12/08/2021, 12:58 PM
 ******************************************************************************/

package com.dicoding.submission.githubuser.data.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.dicoding.submission.githubuser.R
import com.dicoding.submission.githubuser.data.api.ApiClient
import com.dicoding.submission.githubuser.data.api.ApiInterface
import com.dicoding.submission.githubuser.data.model.GithubSearch
import com.dicoding.submission.githubuser.data.model.UserDetails
import com.dicoding.submission.githubuser.others.ApiResponse
import com.dicoding.submission.githubuser.others.CallbackWithRetry
import retrofit2.Call
import retrofit2.Response

class UserRepository {
    private val api = ApiClient.getClient()?.create(ApiInterface::class.java)
    private val data = MutableLiveData<ApiResponse<List<UserDetails>>>()
    private val userData = MutableLiveData<ApiResponse<UserDetails>>()
    private val followingData = MutableLiveData<ApiResponse<List<UserDetails>>>()
    private val followersData = MutableLiveData<ApiResponse<List<UserDetails>>>()

    fun getData(): MutableLiveData<ApiResponse<List<UserDetails>>> = data

    fun getUserData(): MutableLiveData<ApiResponse<UserDetails>> = userData

    fun getFollowingData(): MutableLiveData<ApiResponse<List<UserDetails>>> = followingData

    fun getFollowersData(): MutableLiveData<ApiResponse<List<UserDetails>>> = followersData

    fun getLocalData(context: Context): MutableLiveData<ApiResponse<List<UserDetails>>> {
        this.data.value = ApiResponse.loading()
        val usernames = context.resources.getStringArray(R.array.username)
        val name = context.resources.getStringArray(R.array.name)
        val location = context.resources.getStringArray(R.array.location)
        val repository = context.resources.getStringArray(R.array.repository)
        val company = context.resources.getStringArray(R.array.company)
        val followers = context.resources.getStringArray(R.array.followers)
        val following = context.resources.getStringArray(R.array.following)
        val avatar = context.resources.getStringArray(R.array.avatar)
        val data = mutableListOf<UserDetails>()

        for (position in usernames.indices) {
            val user = UserDetails()
            user.avatarUrl = avatar[position]
            user.name = name[position]
            user.location = location[position]
            user.publicRepos = repository[position].toInt()
            user.company = company[position]
            user.followers = followers[position].toInt()
            user.following = following[position].toInt()
            user.login = usernames[position]
            data.add(user)
        }
        this.data.value = ApiResponse.success(data)
        return this.data
    }

    fun getUserListByQuery(query: String): MutableLiveData<ApiResponse<List<UserDetails>>> {
        val request = api?.getUserList(query)
        data.value = ApiResponse.loading()
        request?.enqueue(object : CallbackWithRetry<GithubSearch>() {
            override fun onResponse(call: Call<GithubSearch>, response: Response<GithubSearch>) {
                super.onResponse(call, response)
                if (response.isSuccessful) {
                    if (response.body()?.totalCount!! > 0) {
                        data.postValue(ApiResponse.success(response.body()!!.items))
                    } else {
                        data.postValue(ApiResponse.noData())
                    }
                } else {
                    data.postValue(
                        ApiResponse.error(
                            response.body()?.items,
                            response.code().toString()
                        )
                    )
                }
            }

            override fun doOnFailureCall(msg: String) {
                data.postValue(ApiResponse.error(null, msg))
            }
        })
        return data
    }

    fun getUserbyUsername(username: String): MutableLiveData<ApiResponse<UserDetails>> {
        val request = api?.getUserByUsername(username)
        userData.value = ApiResponse.loading()
        request?.enqueue(object : CallbackWithRetry<UserDetails>() {
            override fun onResponse(call: Call<UserDetails>, response: Response<UserDetails>) {
                super.onResponse(call, response)
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        userData.postValue(ApiResponse.success(response.body()))
                    } else userData.postValue(ApiResponse.noData())
                } else
                    userData.postValue(ApiResponse.error(null, response.message()))
            }

            override fun doOnFailureCall(msg: String) {
                userData.postValue(ApiResponse.error(null, msg))
            }

        })
        return userData
    }

    fun getFollowingList(username: String): MutableLiveData<ApiResponse<List<UserDetails>>> {
        val request = api?.getFollowingList(username)
        followingData.value = ApiResponse.loading()
        request?.enqueue(object : CallbackWithRetry<List<UserDetails>>() {
            override fun doOnFailureCall(msg: String) {
                followingData.postValue(ApiResponse.error(null, msg))
            }

            override fun onResponse(
                call: Call<List<UserDetails>>,
                response: Response<List<UserDetails>>
            ) {
                if (response.isSuccessful) {
                    if (response.body()?.isNotEmpty() == true) {
                        followingData.postValue(ApiResponse.success(response.body()))
                    } else followingData.postValue(ApiResponse.noData())
                } else {
                    followingData.postValue(ApiResponse.error(null, response.message()))
                }
            }
        })

        return followingData
    }

    fun getFollowersList(username: String): MutableLiveData<ApiResponse<List<UserDetails>>> {
        val request = api?.getFollowersList(username)
        followersData.value = ApiResponse.loading()
        request?.enqueue(object : CallbackWithRetry<List<UserDetails>>() {
            override fun doOnFailureCall(msg: String) {
                followersData.postValue(ApiResponse.error(null, msg))
            }

            override fun onResponse(
                call: Call<List<UserDetails>>,
                response: Response<List<UserDetails>>
            ) {
                super.onResponse(call, response)
                if (response.isSuccessful) {
                    if (response.body()?.isNotEmpty() == true) {
                        followersData.postValue(ApiResponse.success(response.body()))
                    } else followersData.postValue(ApiResponse.noData())
                } else {
                    followersData.postValue(ApiResponse.error(null, response.message()))
                }
            }
        })

        return followersData
    }
}
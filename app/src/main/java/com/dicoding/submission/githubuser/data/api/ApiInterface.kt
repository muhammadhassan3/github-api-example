/*******************************************************************************
 * Created by muham on 12/09/2021, 10:21 AM
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 12/09/2021, 10:17 AM
 ******************************************************************************/

/*******************************************************************************
 * Created by muham on 12/09/2021, 10:16 AM
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 12/08/2021, 8:28 AM
 ******************************************************************************/

package com.dicoding.submission.githubuser.data.api

import com.dicoding.submission.githubuser.data.model.GithubSearch
import com.dicoding.submission.githubuser.data.model.UserDetails
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    @GET("search/users")
    fun getUserList(@Query("q") query: String): Call<GithubSearch>

    @GET("users/{username}")
    fun getUserByUsername(@Path("username") username: String): Call<UserDetails>

    @GET("users/{username}/followers")
    fun getFollowersList(@Path("username") username: String): Call<List<UserDetails>>

    @GET("users/{username}/following")
    fun getFollowingList(@Path("username") username: String): Call<List<UserDetails>>
}
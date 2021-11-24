package com.dicoding.submission.githubuser.others

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class CallbackWithRetry<T> : Callback<T> {
    private val maxTry = 3
    private var count = 0

    override fun onResponse(call: Call<T>, response: Response<T>) {
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        if (count < maxTry) {
            call.clone().enqueue(this)
            count++
        } else {
            t.printStackTrace()
            doOnFailureCall(t.message.toString())
        }
    }

    abstract fun doOnFailureCall(msg: String)
}
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
 * Last modified 12/08/2021, 11:34 AM
 ******************************************************************************/

package com.dicoding.submission.githubuser.others

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class CallbackWithRetry<T> : Callback<T> {
    private val maxTry = 3
    private var count = 0

    companion object;

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
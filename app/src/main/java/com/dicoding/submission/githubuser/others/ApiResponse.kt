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
 * Last modified 09/09/2021, 3:09 PM
 ******************************************************************************/

package com.dicoding.submission.githubuser.others

class ApiResponse<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?): ApiResponse<T> {
            return ApiResponse(Status.SUCCESS, data, null)
        }

        fun <T> error(data: T?, msg: String?): ApiResponse<T> {
            return ApiResponse(Status.ERROR, data, msg)
        }

        fun <T> loading(): ApiResponse<T> {
            return ApiResponse(Status.LOADING, null, null)
        }

        fun <T> noData(): ApiResponse<T> {
            return ApiResponse(Status.NO_DATA, null, null)
        }
    }
}
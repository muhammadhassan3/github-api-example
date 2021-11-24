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
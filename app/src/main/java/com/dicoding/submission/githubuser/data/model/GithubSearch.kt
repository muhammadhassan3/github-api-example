package com.dicoding.submission.githubuser.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class GithubSearch(
    @SerializedName("total_count")
    val totalCount: Int,
    @SerializedName("incomplete_result")
    val incompleteResult: Boolean,
    @SerializedName("items")
    val items: ArrayList<UserDetails>?
) : Parcelable
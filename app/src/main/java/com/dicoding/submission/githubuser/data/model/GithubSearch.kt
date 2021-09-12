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

package com.dicoding.submission.githubuser.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
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
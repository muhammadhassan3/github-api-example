/*******************************************************************************
 * Created by muham on 12/09/2021, 10:21 AM
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 12/09/2021, 10:21 AM
 ******************************************************************************/

/*******************************************************************************
 * Created by muham on 12/09/2021, 10:16 AM
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 09/09/2021, 2:37 PM
 ******************************************************************************/

package com.dicoding.submission.githubuser.data.database.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "tbl_favorite")
data class Favorite(
    @PrimaryKey(autoGenerate = true)
    var id: Int?,
    var username: String,
    var avatarUrl: String,
    var location: String
) : Parcelable

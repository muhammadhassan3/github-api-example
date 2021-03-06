package com.dicoding.submission.githubuser.data.database.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "tbl_favorite", indices = [Index(value = ["username"], unique = true)])
data class Favorite(
    @PrimaryKey(autoGenerate = true)
    var id: Int?,
    var name: String?,
    var username: String?,
    var avatarUrl: String?,
    var location: String?
) : Parcelable

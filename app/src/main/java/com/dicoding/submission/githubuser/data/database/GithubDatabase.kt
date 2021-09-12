/*******************************************************************************
 * Created by muham on 12/09/2021, 10:16 AM
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 09/09/2021, 7:40 AM
 ******************************************************************************/

package com.dicoding.submission.githubuser.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dicoding.submission.githubuser.data.database.dao.FavoriteDao
import com.dicoding.submission.githubuser.data.database.entity.Favorite

@Database(entities = [Favorite::class], version = 1)
abstract class GithubDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao

    companion object {
        private var instance: GithubDatabase? = null

        @Synchronized
        fun getInstance(context: Context): GithubDatabase {
            if (instance == null)
                instance = Room.databaseBuilder(
                    context.applicationContext, GithubDatabase::class.java,
                    "github_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
            return instance!!
        }
    }
}
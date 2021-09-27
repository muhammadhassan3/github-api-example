/*******************************************************************************
 * Created by muham on 12/09/2021, 10:16 AM
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 09/09/2021, 7:28 AM
 ******************************************************************************/

package com.dicoding.submission.githubuser.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dicoding.submission.githubuser.data.database.entity.Favorite

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favorite: Favorite)

    @Query("delete from tbl_favorite  where username = :username")
    fun deleteByUsername(username: String)

    @Query("delete from tbl_favorite")
    fun deleteAll()

    @Query("select * from tbl_favorite order by id desc")
    fun getAll(): LiveData<List<Favorite>?>

    @Query("select * from tbl_favorite e where e.username = :username")
    fun findByUsername(username: String): LiveData<Favorite?>
}
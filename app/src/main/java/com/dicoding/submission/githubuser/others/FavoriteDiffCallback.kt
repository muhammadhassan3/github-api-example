package com.dicoding.submission.githubuser.others

import androidx.recyclerview.widget.DiffUtil
import com.dicoding.submission.githubuser.data.database.entity.Favorite

class FavoriteDiffCallback(
    private val oldDataList: List<Favorite>,
    private val newDataList: List<Favorite>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldDataList.size
    }

    override fun getNewListSize(): Int {
        return newDataList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldDataList[oldItemPosition].id == newDataList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldData = oldDataList[oldItemPosition]
        val newData = newDataList[newItemPosition]
        return (oldData.id == newData.id) && (oldData.username == newData.username)
    }
}
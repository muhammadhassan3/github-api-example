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
 * Last modified 12/08/2021, 12:58 PM
 ******************************************************************************/

package com.dicoding.submission.githubuser.adapter

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.submission.githubuser.R
import com.dicoding.submission.githubuser.data.model.UserDetails
import com.dicoding.submission.githubuser.databinding.ItemListUserBinding
import com.dicoding.submission.githubuser.others.Util

class UserListAdapter(private val userListInterface: UserListInterface) :
    RecyclerView.Adapter<UserViewHolder>() {
    private val list = mutableListOf<UserDetails>()

    fun setData(data: List<UserDetails>?) {
        data?.let {
            list.clear()
            list.addAll(it)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding =
            ItemListUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        with(holder) {
            with(list[position]) {
                val avatar = if (avatarUrl?.contains("res/drawable/") == true) {
                    Util.getImageId(itemView.context, avatarUrl!!)
                } else avatarUrl
                Glide.with(itemView.context).load(avatar)
                    .fitCenter().circleCrop().into(binding.imgIcon)
                if (location != null) {
                    binding.tvAddress.text = this.location
                } else {
                    binding.tvAddress.text =
                        itemView.resources.getString(R.string.no_data_available)
                    binding.tvAddress.setTypeface(null, Typeface.ITALIC)
                }
                binding.tvName.text = name ?: login
                itemView.setOnClickListener {
                    userListInterface.onClick(this)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface UserListInterface {
        fun onClick(user: UserDetails)
    }

}
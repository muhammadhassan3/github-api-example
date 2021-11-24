package com.dicoding.submission.githubuser.adapter

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.submission.githubuser.R
import com.dicoding.submission.githubuser.data.database.entity.Favorite
import com.dicoding.submission.githubuser.databinding.ItemListUserBinding
import com.dicoding.submission.githubuser.others.FavoriteDiffCallback

class FavoriteAdapter(private val listInterface: ListInterface) :
    RecyclerView.Adapter<UserViewHolder>() {
    private var list = ArrayList<Favorite>()

    fun setData(list: List<Favorite>) {
        val diffCallback = FavoriteDiffCallback(this.list, list)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.list.clear()
        this.list.addAll(list)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding =
            ItemListUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.apply {
            with(list[position]) {
                Glide.with(holder.itemView.context)
                    .load(avatarUrl).fitCenter().circleCrop().into(binding.imgIcon)
                if (location != null) {
                    binding.tvAddress.text = this.location
                } else {
                    binding.tvAddress.text =
                        itemView.resources.getString(R.string.no_data_available)
                    binding.tvAddress.setTypeface(null, Typeface.ITALIC)
                }
                binding.tvName.text = name ?: username
                itemView.setOnClickListener {
                    listInterface.onItemClicked(this)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface ListInterface {
        fun onItemClicked(favorite: Favorite)
    }
}
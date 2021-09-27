/*******************************************************************************
 * Created by muham on 12/09/2021, 10:16 AM
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 09/09/2021, 11:44 AM
 ******************************************************************************/

package com.dicoding.submission.githubuser.ui.view

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submission.githubuser.R
import com.dicoding.submission.githubuser.adapter.FavoriteAdapter
import com.dicoding.submission.githubuser.data.database.entity.Favorite
import com.dicoding.submission.githubuser.data.model.UserDetails
import com.dicoding.submission.githubuser.databinding.ActivityFavoriteBinding
import com.dicoding.submission.githubuser.others.gone
import com.dicoding.submission.githubuser.others.visible
import com.dicoding.submission.githubuser.ui.main.viewmodel.FavoriteViewModel

class FavoriteActivity : AppCompatActivity(), FavoriteAdapter.ListInterface {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: FavoriteAdapter
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = getString(R.string.favorite)
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24)
        }

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )
            .get(FavoriteViewModel::class.java)

        initRecyclerView()
        initViewModel()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initViewModel() {
        viewModel.data.observe(this, {
            if (it != null && it.size > 0) {
                binding.tvNoData.gone()
                binding.rvList.visible()
                adapter.setData(it)
            } else {
                binding.tvNoData.visible()
                binding.rvList.gone()
            }
        })
    }

    private fun initRecyclerView() {
        adapter = FavoriteAdapter(this)
        binding.rvList.apply {
            adapter = this@FavoriteActivity.adapter
            layoutManager = LinearLayoutManager(this@FavoriteActivity)
        }
    }

    override fun onItemClicked(favorite: Favorite) {
        val intent = Intent(this, UserDetailsActivity::class.java)
        intent.putExtra(
            UserDetailsActivity.USER_INFO, UserDetails(
                avatarUrl = favorite.avatarUrl,
                login = favorite.username,
                name = favorite.name,
                location = favorite.location
            )
        )
        startActivity(intent)
    }

}
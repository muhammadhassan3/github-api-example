/*******************************************************************************
 * Created by muham on 12/09/2021, 10:16 AM
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 12/09/2021, 7:49 AM
 ******************************************************************************/

package com.dicoding.submission.githubuser.ui.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.dicoding.submission.githubuser.R
import com.dicoding.submission.githubuser.adapter.SectionPagerAdapter
import com.dicoding.submission.githubuser.data.database.entity.Favorite
import com.dicoding.submission.githubuser.data.model.UserDetails
import com.dicoding.submission.githubuser.databinding.ActivityUserDetailsBinding
import com.dicoding.submission.githubuser.others.*
import com.dicoding.submission.githubuser.ui.main.viewmodel.UserDetailsViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator

class UserDetailsActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityUserDetailsBinding
    private lateinit var user: UserDetails
    private lateinit var viewModel: UserDetailsViewModel

    private var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24)
        user = if (savedInstanceState != null) {
            savedInstanceState.getParcelable(USER_INFO)!!
        } else {
            intent.getParcelableExtra(USER_INFO)!!
        }

        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application))
            .get(UserDetailsViewModel::class.java)

        initTabLayoutWithViewPager()
        setupViewModel()
        user.login?.let {
            viewModel.getUserDetails(user.login!!)
        }
        binding.btnFavorite.setOnClickListener(this)
    }

    private fun setupViewModel() {
        viewModel.data.observe(this, {
            if (it != null) {
                when (it.status) {
                    Status.LOADING -> {
                        setView(Show.PROGRESS_INDICATOR, null)
                    }
                    Status.NO_DATA -> {
                        setView(Show.VIEW, it.data)
                        Util.setupAlertDialog(
                            this,
                            resources.getString(R.string.info_title),
                            resources.getString(R.string.no_data_available)
                        )
                    }
                    Status.ERROR -> {
                        setView(Show.VIEW, null)
                        Util.setupAlertDialog(
                            this,
                            resources.getString(R.string.http_error_title),
                            resources.getString(R.string.error_code, it.message)
                        )
                    }
                    Status.SUCCESS -> {
                        setView(Show.VIEW, it.data)
                    }
                }
            }
        })

        user.login?.let {
            viewModel.getFavoriteUser(it).observe(this@UserDetailsActivity, {
                if (it != null) {
                    isFavorite = true
                    binding.btnFavorite.apply {
                        setImageResource(R.drawable.ic_baseline_favorite_24)
                    }
                } else {
                    isFavorite = false
                    binding.btnFavorite.apply {
                        setImageResource(R.drawable.ic_baseline_favorite_border_24)
                    }
                }
            })
        }
    }

    private fun initTabLayoutWithViewPager() {
        val sectionPagerAdapter = SectionPagerAdapter(this, user.login!!)
        binding.viewPager.adapter = sectionPagerAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = resources.getString(tabTitle[position])
        }.attach()
    }


    private fun setView(show: Show, user: UserDetails?) {
        binding.apply {
            progressIndicator.gone()
            imgAvatar.invisible()
            tvName.invisible()
            tvUsername.invisible()
            tvLocation.invisible()
            iconLocation.invisible()
            tvUsername.invisible()
            tvOffice.invisible()
            iconOffice.invisible()
            tvFollowers.invisible()
            tvFollowing.invisible()
            tvRepository.invisible()
            btnFavorite.invisible()
        }
        if (show == Show.PROGRESS_INDICATOR) {
            binding.progressIndicator.visible()
        } else {
            user?.let {
                this.user = it
            }
            binding.apply {
                imgAvatar.visible()
                tvName.visible()
                tvUsername.visible()
                tvLocation.visible()
                iconLocation.visible()
                tvUsername.visible()
                tvOffice.visible()
                iconOffice.visible()
                tvFollowers.visible()
                tvFollowing.visible()
                tvRepository.visible()
                btnFavorite.visible()
            }
            Glide.with(this).load(user?.avatarUrl)
                .fitCenter()
                .apply(RequestOptions.bitmapTransform(RoundedCorners(12)))
                .into(binding.imgAvatar)
            binding.tvName.text = user?.name ?: resources.getString(R.string.no_data_available)
            binding.tvUsername.text = if (user?.login != null) (resources.getString(
                R.string.username_tag,
                user.login
            )) else resources.getString(R.string.no_data_available)
            binding.tvLocation.text =
                user?.location ?: resources.getString(R.string.no_data_available)
            binding.tvOffice.text =
                user?.company ?: resources.getString(R.string.no_data_available)
            binding.tvFollowers.text = user?.followers.toString()
            binding.tvFollowing.text = user?.following.toString()
            binding.tvRepository.text = user?.publicRepos.toString()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.share_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.btnShare -> {
                val intent = Intent(Intent.ACTION_SEND)
                val content = """
                    ${user.name} yang tinggal di ${user.location} mempunyai pekerjaan pada perusahaan ${user.company} sudah memiliki ${user.followers} Pengikut dan sudah berkontribusi sebanyak ${user.publicRepos} Project di Github.
                    
                    Created by Github Users App""".trimIndent()
                intent.putExtra(Intent.EXTRA_SUBJECT, "Profile ${user.name}")
                intent.putExtra(Intent.EXTRA_TEXT, content)
                startActivity(Intent.createChooser(intent, "Bagikan melalui"))
            }
            R.id.btnSettings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
            }
            else ->
                return false
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(USER_INFO, user)
        super.onSaveInstanceState(outState)
    }

    private enum class Show {
        PROGRESS_INDICATOR,
        VIEW
    }

    override fun onClick(p0: View?) {
        p0?.let {
            when (it.id) {
                binding.btnFavorite.id -> {
                    val favorite =
                        Favorite(null, user.name, user.login, user.avatarUrl, user.location)
                    if (isFavorite) {
                        Snackbar.make(
                            it,
                            getString(R.string.removed_from_favorite),
                            Snackbar.LENGTH_SHORT
                        ).show()
                        viewModel.deleteFavorite(favorite)
                    } else {
                        viewModel.addFavorite(favorite)
                        Snackbar.make(
                            it,
                            getString(R.string.added_to_favorite),
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    companion object {
        const val USER_INFO = "user_info"

        @StringRes
        private val tabTitle = arrayOf(
            R.string.followers,
            R.string.following
        )
    }
}
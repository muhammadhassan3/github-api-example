/*******************************************************************************
 * Created by muham on 12/09/2021, 10:16 AM
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 12/08/2021, 8:14 AM
 ******************************************************************************/

package com.dicoding.submission.githubuser.ui.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.dicoding.submission.githubuser.databinding.ActivitySplashScreenBinding
import com.dicoding.submission.githubuser.others.DataStorePreferences
import com.dicoding.submission.githubuser.others.dataStore
import com.dicoding.submission.githubuser.ui.main.viewmodel.SplashScreenViewModel

class SplashScreen : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    private lateinit var viewModel: SplashScreenViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dataStorePreference = DataStorePreferences.getInstance(dataStore)
        viewModel = SplashScreenViewModel(dataStorePreference)
        initConfiguration()
    }

    private fun initHandler() {
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, ListActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }, 2000)
    }

    private fun initConfiguration() {
        viewModel.getDarkTheme().observe(this, { isDarkMode ->
            if (isDarkMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        })
        initHandler()
    }
}
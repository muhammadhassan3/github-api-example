/*******************************************************************************
 * Created by muham on 18/09/2021, 10:01 AM
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 18/09/2021, 10:01 AM
 ******************************************************************************/

package com.dicoding.submission.githubuser.ui.view

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.submission.githubuser.R
import com.dicoding.submission.githubuser.databinding.SettingsActivityBinding
import com.dicoding.submission.githubuser.ui.view.fragment.PreferenceFragment

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: SettingsActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SettingsActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = getString(R.string.settings)
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24)
        }
        supportFragmentManager.beginTransaction().add(binding.settings.id, PreferenceFragment())
            .commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
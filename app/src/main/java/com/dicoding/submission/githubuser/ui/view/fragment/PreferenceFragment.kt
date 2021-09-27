/*******************************************************************************
 * Created by muham on 24/09/2021, 2:45 PM
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 24/09/2021, 2:45 PM
 ******************************************************************************/
package com.dicoding.submission.githubuser.ui.view.fragment

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.dicoding.submission.githubuser.R
import com.dicoding.submission.githubuser.others.DataStorePreferences
import com.dicoding.submission.githubuser.others.dataStore
import com.dicoding.submission.githubuser.ui.main.viewmodel.PreferenceViewModel

class PreferenceFragment : PreferenceFragmentCompat() {
    private var darkThemeKey: String? = null
    private var darkThemeSwitch: SwitchPreferenceCompat? = null
    private var viewModel: PreferenceViewModel? = null
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)
        init()
        initViewModel()
    }

    override fun onPreferenceTreeClick(preference: Preference?): Boolean {
        when (preference?.key) {
            getString(R.string.localization) -> {
                val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(intent)
            }
            getString(R.string.dark_mode) -> {
                darkThemeSwitch?.let {
                    viewModel?.setDarkTheme(it.isChecked)
                }
            }
        }
        return super.onPreferenceTreeClick(preference)
    }

    private fun initViewModel() {
        val dataStorePreference = context?.dataStore?.let { DataStorePreferences.getInstance(it) }
        viewModel = dataStorePreference?.let { PreferenceViewModel(it) }

        viewModel
        viewModel?.getDarkTheme()?.observe(this, {
            darkThemeSwitch?.isChecked = it
            if (it) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        })
    }

    private fun init() {
        darkThemeKey = resources.getString(R.string.dark_mode)

        darkThemeKey?.let {
            darkThemeSwitch = findPreference(it)
        }
    }
}
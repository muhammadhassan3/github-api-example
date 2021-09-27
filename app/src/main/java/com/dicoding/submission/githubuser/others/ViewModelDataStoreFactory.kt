/*******************************************************************************
 * Created by muham on 25/09/2021, 11:58 AM
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 25/09/2021, 11:58 AM
 ******************************************************************************/

package com.dicoding.submission.githubuser.others

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelDataStoreFactory(private val pref: DataStorePreferences) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(DataStorePreferences::class.java)
            .newInstance(pref)
    }
}
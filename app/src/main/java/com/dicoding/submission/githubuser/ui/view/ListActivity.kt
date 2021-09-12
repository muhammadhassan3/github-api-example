/*******************************************************************************
 * Created by muham on 12/09/2021, 10:21 AM
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 12/09/2021, 10:21 AM
 ******************************************************************************/

/*******************************************************************************
 * Created by muham on 12/09/2021, 10:16 AM
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 12/08/2021, 8:28 AM
 ******************************************************************************/

package com.dicoding.submission.githubuser.ui.view

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submission.githubuser.R
import com.dicoding.submission.githubuser.adapter.UserListAdapter
import com.dicoding.submission.githubuser.data.model.UserDetails
import com.dicoding.submission.githubuser.databinding.ActivityListBinding
import com.dicoding.submission.githubuser.others.Status
import com.dicoding.submission.githubuser.others.Util
import com.dicoding.submission.githubuser.others.gone
import com.dicoding.submission.githubuser.others.visible
import com.dicoding.submission.githubuser.ui.main.viewmodel.ListViewModel

class ListActivity : AppCompatActivity(), UserListAdapter.UserListInterface {
    private lateinit var binding: ActivityListBinding
    private lateinit var listViewModel: ListViewModel
    private lateinit var adapter: UserListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        listViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(ListViewModel::class.java)

        initRecyclerView()
        initViewModel()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_setting_toolbar, menu)
        initSearchView(menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.btnSettings -> {
                val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun progressIndicator(show: Boolean) {
        if (show) {
            binding.progressIndicator.visible()
            binding.rvList.gone()
        } else {
            binding.progressIndicator.gone()
            binding.rvList.visible()
        }
    }

    private fun initSearchView(menu: Menu?) {
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView
        val menuItem = menu.findItem(R.id.search)

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                requestUserByUsername(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
        menuItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(p0: MenuItem?): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
                listViewModel.getUserListFromLocal(this@ListActivity)
                return true
            }

        })
    }

    private fun requestUserByUsername(query: String?) {
        if (query != null) {
            listViewModel.getUserByQuery(query)
        } else Toast.makeText(
            this,
            resources.getString(R.string.invalid_username_query),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun initViewModel() {
        listViewModel.getData()?.observe(this, {
            if (it != null) {
                when (it.status) {
                    Status.LOADING -> {
                        progressIndicator(true)
                    }
                    Status.NO_DATA -> {
                        progressIndicator(false)
                        adapter.setData(it.data)
                        binding.tvResult.text =
                            resources.getString(R.string.user_found, it.data?.size)
                        Util.setupAlertDialog(
                            this,
                            resources.getString(R.string.info_title),
                            resources.getString(R.string.no_data_available)
                        )
                    }
                    Status.ERROR -> {
                        progressIndicator(false)
                        Util.setupAlertDialog(
                            this,
                            resources.getString(R.string.http_error_title),
                            resources.getString(R.string.error_code, it.message)
                        )
                    }
                    Status.SUCCESS -> {
                        progressIndicator(false)
                        adapter.setData(it.data)
                        binding.tvResult.text =
                            resources.getString(R.string.user_found, it.data?.size)
                    }
                }
            }
        })
        listViewModel.getUserListFromLocal(this)
    }

    private fun initRecyclerView() {
        adapter = UserListAdapter(this)
        val layoutManager = LinearLayoutManager(this)
        val itemDivider = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvList.layoutManager = layoutManager
        binding.rvList.addItemDecoration(itemDivider)
        binding.rvList.adapter = adapter
    }

    override fun onClick(user: UserDetails) {
        val intent = Intent(this, UserDetailsActivity::class.java)
        intent.putExtra(UserDetailsActivity.USER_INFO, user)
        startActivity(intent)
    }
}
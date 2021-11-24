/*******************************************************************************
 * Created by muham on 12/09/2021, 10:16 AM
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 12/08/2021, 11:39 AM
 ******************************************************************************/

package com.dicoding.submission.githubuser.ui.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submission.githubuser.R
import com.dicoding.submission.githubuser.adapter.UserListAdapter
import com.dicoding.submission.githubuser.data.model.UserDetails
import com.dicoding.submission.githubuser.databinding.FragmentFollowingBinding
import com.dicoding.submission.githubuser.others.Status
import com.dicoding.submission.githubuser.others.Util
import com.dicoding.submission.githubuser.others.gone
import com.dicoding.submission.githubuser.others.visible
import com.dicoding.submission.githubuser.ui.main.viewmodel.FollowingViewModel
import com.dicoding.submission.githubuser.ui.view.UserDetailsActivity

class FollowingFragment : Fragment(), UserListAdapter.UserListInterface {
    private var username: String? = null
    private val USERNAME = "username"
    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!
    private lateinit var followingViewModel: FollowingViewModel
    private lateinit var adapter: UserListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString(USERNAME)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        followingViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(FollowingViewModel::class.java)

        setupRecyclerView()
        setupViewModel()
        if (username.isNullOrEmpty()) {
            setView(Show.NO_DATA_TEXTVIEW)
        } else {
            followingViewModel.getData(username!!)
        }
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(context)
        val divider = DividerItemDecoration(context, layoutManager.orientation)
        adapter = UserListAdapter(this)
        binding.rvList.addItemDecoration(divider)
        binding.rvList.layoutManager = layoutManager
        binding.rvList.adapter = adapter
    }

    private fun setupViewModel() {
        followingViewModel.getData()?.observe(requireActivity(), {
            if (it != null) {
                when (it.status) {
                    Status.SUCCESS -> {
                        adapter.setData(it.data)
                        setView(Show.RECYCLER_VIEW)
                    }
                    Status.ERROR -> {
                        Util.setupAlertDialog(
                            requireContext(),
                            resources.getString(R.string.http_error_title),
                            resources.getString(R.string.error_code, it.message)
                        )
                        setView(Show.NO_DATA_TEXTVIEW)
                    }
                    Status.NO_DATA -> {
                        setView(Show.NO_DATA_TEXTVIEW)
                    }
                    Status.LOADING -> {
                        setView(Show.PROGRESS_INDICATOR)
                    }
                }
            }
        })
    }

    private fun setView(show: Show) {
        binding.rvList.gone()
        binding.pBar.gone()
        binding.tvNoData.gone()

        when (show) {
            Show.NO_DATA_TEXTVIEW -> {
                binding.tvNoData.visible()
            }
            Show.PROGRESS_INDICATOR -> {
                binding.pBar.visible()
            }
            Show.RECYCLER_VIEW -> {
                binding.rvList.visible()
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(username: String) =
            FollowingFragment().apply {
                arguments = Bundle().apply {
                    putString(USERNAME, username)
                }
            }
    }

    private enum class Show {
        PROGRESS_INDICATOR,
        RECYCLER_VIEW,
        NO_DATA_TEXTVIEW
    }

    override fun onClick(user: UserDetails) {
        val intent = Intent(activity, UserDetailsActivity::class.java)
        intent.putExtra(UserDetailsActivity.USER_INFO, user)
        startActivity(intent)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
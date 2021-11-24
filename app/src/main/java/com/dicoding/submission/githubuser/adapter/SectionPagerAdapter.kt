package com.dicoding.submission.githubuser.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dicoding.submission.githubuser.ui.view.fragment.FollowersFragment
import com.dicoding.submission.githubuser.ui.view.fragment.FollowingFragment

class SectionPagerAdapter(activity: AppCompatActivity, var username: String) :
    FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FollowersFragment.newInstance(username)
            1 -> FollowingFragment.newInstance(username)
            else -> null as Fragment
        }
    }

}
package com.amora.viewpager2.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.amora.viewpager2.ui.dashboard.DashboardFragment
import com.amora.viewpager2.ui.home.HomeFragment
import com.amora.viewpager2.ui.notifications.NotificationsFragment
import com.amora.viewpager2.utils.Constant.NUM_PAGES

class ViewPagerAdapter(fa: FragmentActivity): FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = NUM_PAGES

    override fun createFragment(position: Int): Fragment = when (position) {
        1 -> DashboardFragment()
        2 -> NotificationsFragment()
        else -> HomeFragment()
    }

}
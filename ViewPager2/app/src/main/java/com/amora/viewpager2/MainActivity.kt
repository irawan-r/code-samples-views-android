package com.amora.viewpager2

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.amora.viewpager2.adapter.DepthPageTransformer
import com.amora.viewpager2.adapter.ViewPagerAdapter
import com.amora.viewpager2.adapter.ZoomOutPageTransformer
import com.amora.viewpager2.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val pagingAdapter = ViewPagerAdapter(this)
        binding.apply {
            viewPager.apply {
                // TODO: Set any animation pager here
                setPageTransformer(DepthPageTransformer())
                adapter = pagingAdapter
            }

            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = "Tab $position"
            }.attach()
        }
        setContentView(binding.root)
    }
}
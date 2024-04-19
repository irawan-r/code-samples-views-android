package com.amora.stickylayout

import android.os.Bundle
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amora.stickylayout.adapter.StickyAdapter
import com.amora.stickylayout.data.Content
import com.amora.stickylayout.data.Header
import com.amora.stickylayout.data.StickyModel
import com.amora.stickylayout.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val listRv = mutableListOf<StickyModel>()
        for (i in 1..8) {
            listRv.add(Header("Header $i"))
            for (j in 1..3) {
                listRv.add(Content("Hi, ini content dari Header $i content $j", "Header $i"))
            }
        }

        val mLayoutManager = LinearLayoutManager(this)
        val mAdapter = StickyAdapter(listRv)

        binding.rvLayout.apply {
            layoutManager = mLayoutManager
            adapter = mAdapter
            addItemDecoration(
                DividerItemDecoration(
                    this@MainActivity,
                    DividerItemDecoration.VERTICAL
                )
            )
            addOnScrollListener(RvStickyScroll(listRv, binding.tvHeader, mLayoutManager))
        }
        binding.tvHeader.text = listRv.first().header
    }

    inner class RvStickyScroll(
        private val list: MutableList<StickyModel>,
        private val header: TextView,
        private val rvLayoutManager: LinearLayoutManager
    ) : RecyclerView.OnScrollListener() {

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            val position = rvLayoutManager.findFirstCompletelyVisibleItemPosition()
            val item = list[position]
            println("newState: $newState")
            when (item) {
                is Header -> header.isVisible = false
                is Content -> {
                    header.isVisible = true
                    val headerText =
                        list[rvLayoutManager.findFirstCompletelyVisibleItemPosition()].header
                    if (headerText.isNullOrEmpty().not()) header.text = headerText
                }
            }
        }
    }
}
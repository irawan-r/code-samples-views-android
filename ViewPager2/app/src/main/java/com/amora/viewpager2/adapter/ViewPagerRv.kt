package com.amora.viewpager2.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amora.viewpager2.databinding.FragmentHomeBinding

class PagerAdapter(private val context: Context) : RecyclerView.Adapter<PagerAdapter.PageHolder>() {

    var titleList: List<String> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageHolder =
        PageHolder(
            FragmentHomeBinding.inflate(LayoutInflater.from(context))
        )

    override fun onBindViewHolder(holder: PageHolder, position: Int) {
        holder.textView.text = titleList[position]
    }

    override fun getItemCount(): Int = titleList.size

    inner class PageHolder(view: FragmentHomeBinding) : RecyclerView.ViewHolder(view.root) {
        val textView = view.textHome
    }
}
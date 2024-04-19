package com.amora.stickylayout.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amora.stickylayout.data.Content
import com.amora.stickylayout.data.Header
import com.amora.stickylayout.data.StickyModel
import com.amora.stickylayout.databinding.ContentItemRvStickyBinding
import com.amora.stickylayout.databinding.HeaderItemRvStickyBinding
import java.lang.IllegalArgumentException

class StickyAdapter(private val list: MutableList<StickyModel>): RecyclerView.Adapter<StickyAdapter.Companion.BaseViewHolder<*>>() {

    private lateinit var headerBinding: HeaderItemRvStickyBinding
    private lateinit var contentBinding: ContentItemRvStickyBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return when(viewType) {
            ViewType.Header.ordinal -> {
                headerBinding = HeaderItemRvStickyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                HeaderViewHolder(headerBinding)
            }
            ViewType.Content.ordinal -> {
                contentBinding = ContentItemRvStickyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ContentViewHolder(contentBinding)
            }
            else -> throw IllegalArgumentException("Fail retrive ViewType")
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is ContentViewHolder -> {
                val item = list[position] as Content
                holder.apply {
                    binding.apply {
                        tvDescription.text = item.description
                        tvNum.text = position.toString()
                        tvCheck.text = "✔"
                    }
                }
            }

            is HeaderViewHolder -> {
                val item = list[position] as Header
                holder.binding.tvRvHeader.text = item.header
            }
        }
    }

    override fun getItemCount(): Int = list.size

    override fun getItemViewType(position: Int): Int {
        return when(list[position]) {
            is Header -> ViewType.Header.ordinal
            is Content -> ViewType.Content.ordinal
            else -> -1
        }
    }

    inner class HeaderViewHolder(val binding: HeaderItemRvStickyBinding): BaseViewHolder<HeaderItemRvStickyBinding>(binding.root)
    inner class ContentViewHolder(val binding: ContentItemRvStickyBinding): BaseViewHolder<ContentItemRvStickyBinding>(binding.root)

    companion object {
        abstract class BaseViewHolder<T>(view: View): RecyclerView.ViewHolder(view)
    }

    enum class ViewType {
        Header, Content
    }
}
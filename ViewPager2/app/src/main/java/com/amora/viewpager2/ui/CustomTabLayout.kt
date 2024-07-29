package com.amora.viewpager2.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.amora.viewpager2.R
import com.google.android.material.tabs.TabLayout

class CustomTabLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : TabLayout(context, attrs) {

    private val indicatorPaint = Paint().apply {
        color = ContextCompat.getColor(context, R.color.red) // Replace with your desired color
        strokeWidth = context.resources.getDimension(R.dimen.bottom_rectangle_height) // Customize height
        style = Paint.Style.FILL
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val tabCount = tabCount
        if (tabCount == 0) return

        val tabLayout = getChildAt(0) as? ViewGroup ?: return
        for (i in 0 until tabCount) {
            val tab = getTabAt(i)
            if (tab != null && tab.isSelected) {
                val tabView = tabLayout.getChildAt(tab.position)
                tabView?.let {
                    val tabTextView = it.findViewById<TextView>(android.R.id.text1)
                    val textWidth = tabTextView?.measuredWidth ?: it.width

                    val startX = (it.left + (it.width - textWidth) / 2).toFloat()
                    val endX = (it.right - (it.width - textWidth) / 2).toFloat()
                    val bottom = height.toFloat()
                    canvas.drawRect(startX, bottom - indicatorPaint.strokeWidth, endX, bottom, indicatorPaint)
                }
            }
        }
    }
}
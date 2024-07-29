package com.amora.viewpager2

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import android.os.Build
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.Px
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.amora.viewpager2.adapter.DepthPageTransformer
import com.amora.viewpager2.adapter.PagerAdapter
import com.amora.viewpager2.adapter.ViewPagerAdapter
import com.amora.viewpager2.adapter.ZoomOutPageTransformer
import com.amora.viewpager2.databinding.ActivityMainBinding
import com.amora.viewpager2.databinding.ItemVpBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    val pagingAdapter by lazy { ViewPagerAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val data = listOf("Surpise Deal", "super deal", "Surpise Deal", "Surpise Deal", "super deal", "Surpise Deal")
        binding.apply {
            viewPager.apply {
                // TODO: Set any animation pager here
//                setPageTransformer(DepthPageTransformer())
                pagingAdapter.data.addAll(data)
                adapter = pagingAdapter
            }
            tabLayout.handleDynamicTab()
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                val tabItem = data[position]
                tab.view.setPadding(tab.view.paddingStart, tab.view.paddingTop, tab.view.paddingRight, 0)
                tab.text = tabItem
            }.attach()
        }
        setContentView(binding.root)
    }

    private fun TabLayout?.handleDynamicTab() = this?.run {
        post {
            println("width $width, pixels ${this.resources.displayMetrics.widthPixels}")
            if (width < this.resources.displayMetrics.widthPixels) {
                tabMode = TabLayout.MODE_FIXED
                val mParams: ViewGroup.LayoutParams = layoutParams
                mParams.width = ViewGroup.LayoutParams.MATCH_PARENT
                tabMode
                setLayoutParams(mParams)
            } else {
                tabMode = TabLayout.MODE_SCROLLABLE
            }
        }
    }


    private fun setTabBackground(tabLayout: TabLayout, tab: TabLayout.Tab) {
        val tabStrip = tabLayout.getChildAt(0) as ViewGroup
        val tabView = tab.view
        val tabTextView = tabView.findViewById<TextView>(android.R.id.text1)

        tabTextView.post {
            val textWidth = tabTextView.paint.measureText(tabTextView.text.toString())
            val height = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 4f, resources.displayMetrics
            ).toInt()

            val drawable = GradientDrawable().apply {
                shape = GradientDrawable.RECTANGLE
                setColor(ContextCompat.getColor(tabLayout.context, R.color.red))
                setSize(textWidth.toInt(), height)
            }

            tabView.background = drawable
        }
    }


    fun createLayerDrawable(
        context: Context,
        color: Int,
        heightDp: Float = 4F,
        isStart: Boolean = false,
        isEnd: Boolean = false,
        widthPx: Int
    ): Drawable {
        // Convert dp to pixels
        val heightPx = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            heightDp,
            context.resources.displayMetrics
        ).toInt()
        val radius = 20.dpToPx(context).toFloat()

        // Create the solid shape
        val shapeDrawable = GradientDrawable().apply {
            setColor(color)
            setSize(widthPx, heightPx)
            cornerRadii = when {
                isStart && isEnd ->
                    floatArrayOf(radius, radius, radius, radius, radius, radius, radius, radius)
                isStart ->
                    floatArrayOf(radius, radius, 0f, 0f, 0f, 0f, radius, radius)
                isEnd ->
                    floatArrayOf(0f, 0f, radius, radius, radius, radius, 0f, 0f)
                else ->
                    floatArrayOf(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f)
            }
        }

        // Create the layer drawable
        val layers = arrayOf<Drawable>(shapeDrawable)
        val layerDrawable = LayerDrawable(layers).apply {
            // Set gravity to bottom
            setLayerGravity(0, android.view.Gravity.BOTTOM)
            setLayerHeight(0, heightPx)
        }

        return layerDrawable
    }

    // Extension function to convert dp to px
    private fun Int.dpToPx(context: Context): Int {
        return (this * context.resources.displayMetrics.density).toInt()
    }
}
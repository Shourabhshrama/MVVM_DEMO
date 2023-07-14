package com.example.softgridapp.ui.view.screens

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.softgridapp.R
import com.example.softgridapp.data.local.DataStoreManager
import com.example.softgridapp.data.model.homeresponse_model.Category
import com.example.softgridapp.data.model.homeresponse_model.HomeResponse
import com.example.softgridapp.data.model.homeresponse_model.ImageSlider
import com.example.softgridapp.databinding.ActivityMainBinding
import com.example.softgridapp.ui.view.adapters.CategoryPagerAdapter
import com.example.softgridapp.ui.view.adapters.ImageSliderAdapter
import com.example.softgridapp.ui.viewmodel.HomeViewModel
import com.example.softgridapp.utils.DELAY_MS
import com.example.softgridapp.utils.NetworkResult
import com.example.softgridapp.utils.PERIOD_MS
import com.example.softgridapp.utils.SOMETHING_WENT_WRONG
import com.example.softgridapp.utils.URL_KEY
import com.example.softgridapp.utils.showToast
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewmodel: HomeViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private var timer: Timer? = null
    private var currentPage = 0
    private lateinit var dataStoreManager: DataStoreManager

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        dataStoreManager = DataStoreManager(this@MainActivity)
        setContentView(binding.root)
        setObserver()
    }

    private fun setObserver() {
        viewmodel.homeResponseData.observe(this) {
            when (it) {
                is NetworkResult.Success -> {
                    binding.progressBar.visibility = View.GONE
                    setUpUIData(it.data)
                }

                is NetworkResult.Error -> {
                    binding.progressBar.visibility = View.GONE
                    showToast(it.message)
                }

                is NetworkResult.Loading -> binding.progressBar.visibility = View.VISIBLE
                else -> {
                    showToast(SOMETHING_WENT_WRONG)
                }
            }

        }
    }

    private fun setUpUIData(data: HomeResponse) {
        if (data.imageSlider?.isNotEmpty() == true) {
            setupSliderData(data.imageSlider)
            startAutoScroll(data.imageSlider)
            createIndicators(data.imageSlider)

        }

        if (data.categories.isNotEmpty()) {
            setupProductData(data.categories)
        }

        Glide.with(this@MainActivity).load(data.banner.image).into(binding.bannerImageView)
        binding.bannerImageView.setOnClickListener {
            openAppLink(data.banner.image)
        }

    }

    private fun setupProductData(categories: List<Category>) {
        val adapter = CategoryPagerAdapter(supportFragmentManager, lifecycle, categories)

        binding.viewPagerProducts.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPagerProducts) { tab, position ->
            tab.text = categories[position].category
        }.attach()
    }

    private fun setupSliderData(imageSlider: List<ImageSlider>) {
        val adapter = ImageSliderAdapter(imageSlider)
        binding.viewPager.adapter = adapter
    }


    private fun startAutoScroll(imageSlider: List<ImageSlider>) {
        if (timer == null) {
            timer = Timer()
            val handler = Handler(Looper.getMainLooper())
            val update = Runnable {
                if (currentPage == imageSlider.size) {
                    currentPage = 0
                }
                binding.viewPager.setCurrentItem(currentPage++, true)
            }
            timer?.schedule(object : TimerTask() {
                override fun run() {
                    handler.post(update)
                }
            }, DELAY_MS, PERIOD_MS)
        }
    }

    private fun createIndicators(imageSlider: List<ImageSlider>) {
        val indicators = arrayOfNulls<ImageView>(imageSlider.size)

        val indicatorSize = resources.getDimensionPixelSize(R.dimen.indicator_size)
        val indicatorMargin = resources.getDimensionPixelSize(R.dimen.indicator_margin)

        for (i in imageSlider.indices) {
            indicators[i] = ImageView(this)
            val params = LinearLayout.LayoutParams(indicatorSize, indicatorSize)
            params.setMargins(indicatorMargin, 0, indicatorMargin, 0)
            indicators[i]?.layoutParams = params
            indicators[i]?.setBackgroundResource(R.drawable.item_indicator)
            binding.indicatorContainer.addView(indicators[i])
        }


        val pageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                for (i in imageSlider.indices) {
                    if (indicators[i] != null) {
                        indicators[i]?.setBackgroundResource(R.drawable.item_indicator)
                    }
                }
                if (indicators[position] != null) {
                    indicators[position]?.setBackgroundResource(R.drawable.item_indicator_selected)
                }
            }
        }
        binding.viewPager.registerOnPageChangeCallback(pageChangeCallback)
    }


    private fun openAppLink(url: String) {
        var intent = Intent(this, WebViewActivity::class.java)
        intent.putExtra(URL_KEY, url)
        startActivity(intent)

    }








}
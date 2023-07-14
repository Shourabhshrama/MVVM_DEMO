package com.example.softgridapp.ui.view.screens

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.example.softgridapp.data.model.SplashResponse

import com.example.softgridapp.databinding.ActivitySplashBinding
import com.example.softgridapp.ui.viewmodel.SplashViewModel
import com.example.softgridapp.utils.NetworkResult
import com.example.softgridapp.utils.SOMETHING_WENT_WRONG
import com.example.softgridapp.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private val viewModel: SplashViewModel by viewModels()
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        removeStatusbar()
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setObserver()


    }

    fun removeStatusbar() {
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
    }

    private fun setObserver() {
        viewModel.splashResponseData.observe(this) {
            when (it) {
                is NetworkResult.Success -> {
                    binding.progressBar.visibility = View.GONE
                    onSplashDataFetchAndNavigate(it.data)
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


    private fun onSplashDataFetchAndNavigate(data: SplashResponse?) {

        // show random data from api response on splash screen

        Glide.with(this).load(data?.random()?.image).into(binding.imageViewSplash);

        Handler().postDelayed({
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()

        }, 3000)

    }


}
package com.example.softgridapp.ui.view.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.softgridapp.R
import com.example.softgridapp.databinding.ActivityMainBinding
import com.example.softgridapp.databinding.ActivityWebViewBinding
import com.example.softgridapp.utils.URL_KEY

class WebViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWebViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.webView.settings.javaScriptEnabled = true
        val urlData = intent.getStringExtra(URL_KEY)?.let { binding.webView.loadUrl(it) }
        binding.webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                view?.loadUrl(urlData.toString());
                return true;
            }

        };


    }
}
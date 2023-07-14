package com.example.softgridapp.data.repository

import com.example.softgridapp.data.api.ApiHelper
import javax.inject.Inject

class SplashRepository @Inject constructor(private val apiHelper: ApiHelper) {

    suspend fun getSplashData() = apiHelper.getSplashData()
}
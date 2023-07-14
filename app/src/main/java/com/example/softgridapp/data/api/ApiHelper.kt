package com.example.softgridapp.data.api

import com.example.softgridapp.data.model.SplashResponse
import com.example.softgridapp.data.model.homeresponse_model.HomeResponse
import okhttp3.ResponseBody
import retrofit2.Response

interface ApiHelper {

   suspend fun getSplashData(): Response<SplashResponse>

   suspend fun getHomeData(): Response<HomeResponse>

   suspend fun getSendDeviceToken(deviceToken: String): ResponseBody
}
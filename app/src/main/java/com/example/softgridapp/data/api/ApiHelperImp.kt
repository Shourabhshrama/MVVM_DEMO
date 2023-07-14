package com.example.softgridapp.data.api

import com.example.softgridapp.data.model.SplashResponse
import com.example.softgridapp.data.model.homeresponse_model.HomeResponse
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImp @Inject constructor(private val apiService: ApiService) : ApiHelper{
    override suspend fun getSplashData(): Response<SplashResponse> = apiService.getSplash()

    override suspend fun getHomeData(): Response<HomeResponse> = apiService.getHomeData()

    override suspend fun getSendDeviceToken(deviceToken: String): ResponseBody = apiService.sendDeviceToken(deviceToken)

}
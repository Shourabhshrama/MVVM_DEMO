package com.example.softgridapp.data.repository

import com.example.softgridapp.data.api.ApiHelper
import javax.inject.Inject

class HomeRepository @Inject constructor(private val apiHelper: ApiHelper) {
    suspend fun homeData()=apiHelper.getHomeData()
    suspend fun getSendDeviceToken(deviceToken: String)=apiHelper.getSendDeviceToken(deviceToken)
}
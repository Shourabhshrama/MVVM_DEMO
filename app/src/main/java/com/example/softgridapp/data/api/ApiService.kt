package com.example.softgridapp.data.api

import com.example.softgridapp.data.model.SplashResponse
import com.example.softgridapp.data.model.homeresponse_model.HomeResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST


interface ApiService {
 @GET("splashData")
 suspend fun  getSplash(): Response<SplashResponse>

 @GET("homeData")
 suspend fun  getHomeData(): Response<HomeResponse>


 @FormUrlEncoded
 @POST("your-endpoint")
 fun sendDeviceToken(@Field("device_token") deviceToken: String?): ResponseBody
}
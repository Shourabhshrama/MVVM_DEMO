package com.example.softgridapp.data.model.homeresponse_model

data class HomeResponse(
    val banner: Banner,
    val categories: List<Category>,
    val imageSlider: List<ImageSlider>?
)
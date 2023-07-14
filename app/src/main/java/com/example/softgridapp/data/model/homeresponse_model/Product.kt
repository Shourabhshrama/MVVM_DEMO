package com.example.softgridapp.data.model.homeresponse_model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val id: Int,
    val image: String,
    val name: String
): Parcelable
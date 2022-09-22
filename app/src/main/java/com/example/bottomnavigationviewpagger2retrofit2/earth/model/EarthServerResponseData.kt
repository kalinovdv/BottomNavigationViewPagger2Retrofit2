package com.example.bottomnavigationviewpagger2retrofit2.earth.model

import com.google.gson.annotations.SerializedName

data class EarthServerResponseData(
    @field:SerializedName("date") val date: String,
    @field:SerializedName("image") val image: String
)

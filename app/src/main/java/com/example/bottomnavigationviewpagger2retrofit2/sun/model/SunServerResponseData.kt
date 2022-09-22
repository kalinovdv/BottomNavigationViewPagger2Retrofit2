package com.example.bottomnavigationviewpagger2retrofit2.sun.model

import com.google.gson.annotations.SerializedName

data class SunServerResponseData (
    @field:SerializedName("beginTime") val beginTime: String,
    @field:SerializedName("peakTime") val peakTime: String,
    @field:SerializedName("endTime") val endTime: String,
    @field:SerializedName("classType") val classType: String,
    @field:SerializedName("sourceLocation") val sourceLocation: String
)
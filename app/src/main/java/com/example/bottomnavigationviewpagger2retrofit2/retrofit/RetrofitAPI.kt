package com.example.bottomnavigationviewpagger2retrofit2.retrofit

import com.example.bottomnavigationviewpagger2retrofit2.earth.model.EarthServerResponseData
import com.example.bottomnavigationviewpagger2retrofit2.mars.model.MarsServerResponseData
import com.example.bottomnavigationviewpagger2retrofit2.pod.model.PodServerResponseData
import com.example.bottomnavigationviewpagger2retrofit2.sun.model.SunServerResponseData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitAPI {
    @GET("planetary/apod")
    fun getPicturesOfTheDay(
        @Query("api_key") api_key: String,
        @Query("start_date") start_date: String,
        @Query("end_date") end_date: String
    ): Call<List<PodServerResponseData>>

    @GET("EPIC/api/natural/images")
    fun getEarthPictures(
        @Query("api_key") api_key: String,
    ): Call<List<EarthServerResponseData>>

    @GET("mars-photos/api/v1/rovers/curiosity/photos")
    fun getMarsRoverPictures(
        @Query("api_key") api_key: String,
        @Query("earth_date") earth_date: String,
    ): Call<MarsServerResponseData>

    @GET("DONKI/FLR")
    fun getSunFlare(
        @Query("api_key") api_key: String,
        @Query("startDate") startDate: String,
        @Query("endDate") endDate: String
    ): Call<List<SunServerResponseData>>
}
package com.example.bottomnavigationviewpagger2retrofit2.mars.model

import com.example.bottomnavigationviewpagger2retrofit2.retrofit.RetrofitAPI
import com.google.gson.GsonBuilder
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MarsRetrofitImpl {
    private val baseUrl = "https://api.nasa.gov/"

    private val api by lazy {
        Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build().create(RetrofitAPI::class.java)
    }

    fun getMarsRoverPictures(
        apiKey: String,
        earthDate: String,
        marsCallback: Callback<MarsServerResponseData>
    ) {
        api.getMarsRoverPictures(apiKey, earthDate).enqueue(marsCallback)
    }
}
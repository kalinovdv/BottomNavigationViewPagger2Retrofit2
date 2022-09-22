package com.example.bottomnavigationviewpagger2retrofit2.earth.model

sealed class EarthData{
    data class Success(val serverResponseData: List<EarthServerResponseData>): EarthData()
    data class Error(val error: Throwable): EarthData()
    object Loading: EarthData()
}

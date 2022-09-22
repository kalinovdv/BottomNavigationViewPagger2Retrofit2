package com.example.bottomnavigationviewpagger2retrofit2.sun.model

sealed class SunData{
    data class Success(val serverResponseData: List<SunServerResponseData>): SunData()
    data class Error(val error: Throwable): SunData()
    object Loading: SunData()
}

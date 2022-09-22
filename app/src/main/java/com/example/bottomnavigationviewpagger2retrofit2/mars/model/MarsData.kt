package com.example.bottomnavigationviewpagger2retrofit2.mars.model

sealed class MarsData{
    data class Success(val serverResponseData: MarsServerResponseData): MarsData()
    data class Error(val error: Throwable): MarsData()
    object Loading: MarsData()
}

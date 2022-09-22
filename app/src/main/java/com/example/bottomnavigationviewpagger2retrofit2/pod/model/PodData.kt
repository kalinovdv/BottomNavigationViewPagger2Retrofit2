package com.example.bottomnavigationviewpagger2retrofit2.pod.model

sealed class PodData{
    data class Success(val serverResponseData: List<PodServerResponseData>): PodData()
    data class Error(val error: Throwable): PodData()
    object Loading : PodData()
}

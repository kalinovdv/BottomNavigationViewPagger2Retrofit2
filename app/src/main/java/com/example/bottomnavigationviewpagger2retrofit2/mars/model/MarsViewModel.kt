package com.example.bottomnavigationviewpagger2retrofit2.mars.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bottomnavigationviewpagger2retrofit2.BuildConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MarsViewModel(
    private val liveDataForViewToObserver: MutableLiveData<MarsData> = MutableLiveData(),
    private val retrofitImpl: MarsRetrofitImpl = MarsRetrofitImpl()
) : ViewModel() {

    fun getLiveData(): LiveData<MarsData> {
        return liveDataForViewToObserver
    }

    fun sendServerRequest(marsDate: String) {
        liveDataForViewToObserver.postValue(MarsData.Loading)
        val apiKey = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            MarsData.Error(Throwable("Вам нужен NASA API KEY!"))
        } else {
            retrofitImpl.getMarsRoverPictures(apiKey, marsDate, marsCallback)
        }
    }

    val marsCallback = object : Callback<MarsServerResponseData> {
        override fun onResponse(
            call: Call<MarsServerResponseData>,
            response: Response<MarsServerResponseData>
        ) {
            if (response.isSuccessful && response.body() != null) {
                liveDataForViewToObserver.value = MarsData.Success(response.body()!!)
            } else {
                val message = response.message()
                if (message.isNullOrEmpty()) {
                    liveDataForViewToObserver.value =
                        MarsData.Error(Throwable("Неизвестная ошибка!"))
                } else {
                    liveDataForViewToObserver.value = MarsData.Error(Throwable(message))
                }
            }
        }

        override fun onFailure(call: Call<MarsServerResponseData>, t: Throwable) {
            liveDataForViewToObserver.postValue(MarsData.Error(t))
        }
    }

}
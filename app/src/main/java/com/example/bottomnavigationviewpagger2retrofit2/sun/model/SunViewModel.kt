package com.example.bottomnavigationviewpagger2retrofit2.sun.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bottomnavigationviewpagger2retrofit2.BuildConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SunViewModel(
    private val liveDataForViewToObserver: MutableLiveData<SunData> = MutableLiveData(),
    private val retrofitImpl: SunRetrofitImpl = SunRetrofitImpl()
) : ViewModel() {

    fun getLiveData(): LiveData<SunData> {
        return liveDataForViewToObserver
    }

    fun sendServerRequest(startDate: String, endDate: String) {
        liveDataForViewToObserver.postValue(SunData.Loading)
        val apiKey = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()){
            SunData.Error(Throwable("Вам нужен NASA API KEY!"))
        } else {
            retrofitImpl.getSunFlare(apiKey, startDate, endDate, sunCallback)
        }
    }

    val sunCallback = object : Callback<List<SunServerResponseData>> {
        override fun onResponse(
            call: Call<List<SunServerResponseData>>,
            response: Response<List<SunServerResponseData>>
        ) {
            if (response.isSuccessful && response.body() != null) {
                liveDataForViewToObserver.value = SunData.Success(response.body()!!)
            } else {
                val message = response.message()
                if (message.isNullOrEmpty()) {
                    liveDataForViewToObserver.value = SunData.Error(Throwable("Неизвестная ошибка!"))
                } else {
                    liveDataForViewToObserver.value = SunData.Error(Throwable(message))
                }
            }
        }

        override fun onFailure(call: Call<List<SunServerResponseData>>, t: Throwable) {
            liveDataForViewToObserver.postValue(SunData.Error(t))
        }

    }

}
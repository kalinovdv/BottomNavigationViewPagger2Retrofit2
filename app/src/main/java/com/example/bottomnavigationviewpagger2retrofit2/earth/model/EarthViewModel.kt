package com.example.bottomnavigationviewpagger2retrofit2.earth.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bottomnavigationviewpagger2retrofit2.BuildConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EarthViewModel(
    private val liveDataForViewToObserver: MutableLiveData<EarthData> = MutableLiveData(),
    private val retrofitImpl: EarthRetrofitImpl = EarthRetrofitImpl()
) : ViewModel() {

    fun getLiveData(): LiveData<EarthData> {
        return liveDataForViewToObserver
    }

    fun sendServerRequest() {
        liveDataForViewToObserver.postValue(EarthData.Loading)
        val apiKey = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()){
            EarthData.Error(Throwable("Вам нужен NASA API KEY!"))
        } else {
            retrofitImpl.getEarthPictures(apiKey, earthCallback)
        }
    }

    val earthCallback = object : Callback<List<EarthServerResponseData>> {
        override fun onResponse(
            call: Call<List<EarthServerResponseData>>,
            response: Response<List<EarthServerResponseData>>
        ) {
            if (response.isSuccessful && response.body() != null) {
                liveDataForViewToObserver.value = EarthData.Success(response.body()!!)
            } else {
                val message = response.message()
                if (message.isNullOrEmpty()) {
                    liveDataForViewToObserver.value = EarthData.Error(Throwable("Неизвестная ошибка!"))
                } else {
                    liveDataForViewToObserver.value = EarthData.Error(Throwable(message))
                }
            }
        }

        override fun onFailure(call: Call<List<EarthServerResponseData>>, t: Throwable) {
            liveDataForViewToObserver.postValue(EarthData.Error(t))
        }

    }

}
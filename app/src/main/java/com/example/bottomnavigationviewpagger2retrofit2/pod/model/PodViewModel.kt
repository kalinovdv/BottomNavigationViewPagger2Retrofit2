package com.example.bottomnavigationviewpagger2retrofit2.pod.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bottomnavigationviewpagger2retrofit2.BuildConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PodViewModel(
    private val liveDataForViewToObserver: MutableLiveData<PodData> = MutableLiveData(),
    private val retrofitImpl: PodRetrofitImpl = PodRetrofitImpl()) : ViewModel() {

    fun getLiveData():LiveData<PodData>{
        return liveDataForViewToObserver
    }

    fun sendServerRequest(startDate: String, endDate: String){
        liveDataForViewToObserver.postValue(PodData.Loading)
        val apiKey = BuildConfig.NASA_API_KEY
        if(apiKey.isBlank()){
            PodData.Error(Throwable("Вам нужен NASA API KEY!"))
        }else{
            retrofitImpl.getPictureOfTheDay(apiKey, startDate, endDate, podCallback)
        }
    }

    val podCallback  = object : Callback<List<PodServerResponseData>>{
        override fun onResponse(
            call: Call<List<PodServerResponseData>>,
            response: Response<List<PodServerResponseData>>
        ) {
            if(response.isSuccessful && response.body()!=null){
                liveDataForViewToObserver.value = PodData.Success(response.body()!!)
            }else{
                val message = response.message()
                if (message.isNullOrEmpty()) {
                    liveDataForViewToObserver.value =
                        PodData.Error(Throwable("Неизвестная ошибка"))
                } else {
                    liveDataForViewToObserver.value = PodData.Error(Throwable(message))
                }
            }
        }

        override fun onFailure(call: Call<List<PodServerResponseData>>, t: Throwable) {
            liveDataForViewToObserver.postValue(PodData.Error(t))
        }
    }

    /*fun getData(startDate: String, endDate: String): LiveData<PodData> {
        sendServerRequest(startDate, endDate)
        return liveDataForViewToObserver
    }

    private fun sendServerRequest(startDate: String, endDate: String) {
        liveDataForViewToObserver.value = PodData.Loading(null)
        val nasaApiKey = BuildConfig.NASA_API_KEY
        if (nasaApiKey.isBlank()) {
            PodData.Error(Throwable("Вам нужен NASA API KEY!"))
        } else {
            retrofitImpl.getRetrofitImpl().getPicturesOfTheDay(nasaApiKey, startDate, endDate)
                .enqueue(object : Callback<List<PodServerResponseData>> {
                    override fun onResponse(
                        call: Call<List<PodServerResponseData>>,
                        response: Response<List<PodServerResponseData>>
                    ) {
                        if (response.isSuccessful && response.body() != null) {
                            liveDataForViewToObserver.value = PodData.Success(response.body()!!)
                        } else {
                            val message = response.message()
                            if (message.isNullOrEmpty()) {
                                liveDataForViewToObserver.value =
                                    PodData.Error(Throwable("Неизвестная ошибка"))
                            } else {
                                liveDataForViewToObserver.value = PodData.Error(Throwable(message))
                            }
                        }
                    }

                    override fun onFailure(call: Call<List<PodServerResponseData>>, t: Throwable) {
                        liveDataForViewToObserver.value = PodData.Error(Throwable(t))
                    }
                })
        }
    }*/
}
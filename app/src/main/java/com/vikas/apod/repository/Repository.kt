package com.vikas.apod.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.vikas.apod.API_KEY
import com.vikas.apod.data.RestClient
import com.vikas.apod.model.APODResponse
import com.vikas.apod.model.ErrorState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object Repository {

    private val TAG = "Repository"
    fun getAPOD(context: Context): MutableLiveData<APODResponse> {


        val apodLiveData: MutableLiveData<APODResponse> = MutableLiveData()


        val apodCallback =
            NetworkUtilities.getRetrofit(context).create(RestClient::class.java).getAPOD(API_KEY)

        apodCallback.enqueue(object : Callback<APODResponse> {

            override fun onResponse(
                call: Call<APODResponse>,
                response: Response<APODResponse>
            ) {

                response.body()?.let {
                    if (response.raw().networkResponse != null) {
                        apodLiveData.value = response.body()
                    } else {
                        it.isCached = response.raw().cacheResponse != null
                        apodLiveData.value = it
                    }
                }
            }

            override fun onFailure(call: Call<APODResponse>, t: Throwable) {
                Log.e(TAG, "onFailure ${t.localizedMessage}")

                apodLiveData.value =
                    APODResponse(
                        errorState = ErrorState(
                            isFailure = true,
                            errorString = t.message ?: ""
                        )
                    )
            }

        })
        return apodLiveData
    }
}
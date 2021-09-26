package com.vikas.apod.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.vikas.apod.API_KEY
import com.vikas.apod.data.RestClient
import com.vikas.apod.model.APODResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object Repository {

    private val TAG = "Repository"
    fun getAPOD(): MutableLiveData<APODResponse> {
        val apodLiveData: MutableLiveData<APODResponse> = MutableLiveData()
        val apodCallback =
            NetworkUtilities.getRetrofit().create(RestClient::class.java).getAPOD(API_KEY)

        apodCallback.enqueue(object : Callback<APODResponse> {

            override fun onResponse(call: Call<APODResponse>, response: Response<APODResponse>) {
                Log.e(TAG, "OnResponse ${response.body()}")
                apodLiveData.value = response.body()

            }

            override fun onFailure(call: Call<APODResponse>, t: Throwable) {
                Log.e(TAG, "onFailure ${t.localizedMessage}")

                apodLiveData.value = APODResponse(isFailure = false)
            }

        })
        return apodLiveData
    }
}
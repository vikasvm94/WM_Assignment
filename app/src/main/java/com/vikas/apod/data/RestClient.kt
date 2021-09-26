package com.vikas.apod.data

import com.vikas.apod.model.APODResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import javax.security.auth.callback.Callback

interface RestClient {

    @GET("/planetary/apod")
    fun getAPOD(@Query("api_key") apiKey: String): Call<APODResponse>
}
package com.vikas.apod.data

import com.vikas.apod.model.APODResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * An interface which has all the REST API's defined, which will be used with retrofit client
 */
interface RestClient {

    @GET("/planetary/apod")
    fun getAPOD(@Query("api_key") apiKey: String): Call<APODResponse>
}
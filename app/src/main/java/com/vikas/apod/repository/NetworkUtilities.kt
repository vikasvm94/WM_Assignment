package com.vikas.apod.repository

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.vikas.apod.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.logging.HttpLoggingInterceptor




object NetworkUtilities {

    /**
     * Use this API to get a retrofit client to make asynchronous REST API calls
     * Uses GSON as a converter factory and OKHttp as a networking client
     */
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(getGson()))
            .baseUrl(
                BASE_URL
            ).client(getOkHttpClient())
            .build()

    }

    private fun getGson(): Gson {
        return GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()
    }

    /**
     * A method to get OkHttpClient, we have added API-key as part of header for all api's
     */
    private fun getOkHttpClient(): OkHttpClient {
        val client = OkHttpClient.Builder()
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        client.addInterceptor(logging)
        return client.build()
    }

}
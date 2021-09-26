package com.vikas.apod.repository

import android.content.Context
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.vikas.apod.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import android.net.ConnectivityManager
import android.net.NetworkCapabilities

import android.os.Build
import okhttp3.Cache

/**
 * A utility class which provides helpfull methods for network related operations
 */
object NetworkUtilities {

    /**
     * Use this API to get a retrofit client to make asynchronous REST API calls
     * Uses GSON as a converter factory and OKHttp as a networking client
     */
    fun getRetrofit(context: Context): Retrofit {
        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(getGson()))
            .baseUrl(
                BASE_URL
            ).client(getOkHttpClient(context))
            .build()

    }

    private fun getGson(): Gson {
        return GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()
    }

    /**
     * A method to get OkHttpClient, we have added API-key as part of header for all api's
     */
    private fun getOkHttpClient(context: Context): OkHttpClient {

        val myCache = Cache(context.cacheDir, cacheSize)

        val client = OkHttpClient.Builder()
        client.cache(myCache)
        client.addInterceptor { chain ->
            var request = chain.request()
            request = if (isNetworkConnected(context)) {
                request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
            } else
                request.newBuilder().header(
                    "Cache-Control",
                    "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7
                ).build()
            chain.proceed(request)
        }
        return client.build()
    }


    val cacheSize = (5 * 1024 * 1024).toLong()

    /**
     * An API to check whether the internet is available or not
     * returns true if available else false
     */
    fun isNetworkConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val nw = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(nw)
            actNw != null && (actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || actNw.hasTransport(
                NetworkCapabilities.TRANSPORT_CELLULAR
            ) || actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) || actNw.hasTransport(
                NetworkCapabilities.TRANSPORT_BLUETOOTH
            ))
        } else {
            val nwInfo = connectivityManager.activeNetworkInfo
            nwInfo != null && nwInfo.isConnected
        }
    }
}
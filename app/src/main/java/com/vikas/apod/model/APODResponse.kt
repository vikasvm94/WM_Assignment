package com.vikas.apod.model

import com.google.gson.annotations.SerializedName

data class APODResponse(
    @SerializedName("copyright")
    val copyRight: String = "",
    @SerializedName("date")
    val date: String = "",
    @SerializedName("explanation")
    val explanation: String = "",
    @SerializedName("hdurl")
    val hdImageUrl: String = "",
    @SerializedName("media_type")
    val mediaType: String = "",
    @SerializedName("service_version")
    val serviceVersion: String = "",
    @SerializedName("title")
    val title: String = "",
    @SerializedName("url")
    val imageUrl: String = "",

    var isFailure: Boolean = false
)

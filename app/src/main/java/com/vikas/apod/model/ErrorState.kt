package com.vikas.apod.model

data class ErrorState(
    var isFailure: Boolean = true,
    var errorString: String = "",
    var hasInternet: Boolean = true
)


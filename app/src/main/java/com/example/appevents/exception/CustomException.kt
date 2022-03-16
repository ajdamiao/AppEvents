package com.example.appevents.exception

import com.google.gson.annotations.SerializedName

data class CustomException(
    @SerializedName("status")
    val status: Int,

    @SerializedName("type")
    val type: String,

    @SerializedName("error")
    val error: Error
): Throwable()
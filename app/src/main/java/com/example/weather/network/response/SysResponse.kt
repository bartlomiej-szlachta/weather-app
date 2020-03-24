package com.example.weather.network.response

import com.google.gson.annotations.SerializedName

data class SysResponse (
    @SerializedName("sunrise") val sunrise: Long,
    @SerializedName("sunset") val sunset: Long
)
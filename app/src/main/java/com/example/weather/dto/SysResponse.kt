package com.example.weather.dto

import com.google.gson.annotations.SerializedName

data class SysResponse (
    @SerializedName("sunrise") val sunrise: Long,
    @SerializedName("sunset") val sunset: Long
)
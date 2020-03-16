package com.example.weather.dto

import com.google.gson.annotations.SerializedName

data class SysResponse (
    @SerializedName("sunrise") val sunrise: Int,
    @SerializedName("sunset") val sunset: Int
)
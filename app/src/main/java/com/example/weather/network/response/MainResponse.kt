package com.example.weather.network.response

import com.google.gson.annotations.SerializedName

data class MainResponse (
    @SerializedName("temp") val temperature: Double,
    @SerializedName("pressure") val pressure: Int
)
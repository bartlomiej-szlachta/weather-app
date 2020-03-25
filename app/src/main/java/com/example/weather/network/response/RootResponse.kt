package com.example.weather.network.response

import com.google.gson.annotations.SerializedName

data class RootResponse(
    @SerializedName("name") val cityName: String,
    @SerializedName("main") val main: MainResponse,
    @SerializedName("weather") val weather: List<WeatherResponse>,
    @SerializedName("sys") val sys: SysResponse,
    @SerializedName("dt") val dt: Long
)
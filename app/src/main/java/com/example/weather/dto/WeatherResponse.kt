package com.example.weather.dto

import com.google.gson.annotations.SerializedName

data class WeatherResponse (
    @SerializedName("description") val description: String,
    @SerializedName("icon") val icon: String
)
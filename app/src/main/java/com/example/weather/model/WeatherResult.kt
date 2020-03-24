package com.example.weather.model

data class WeatherResult(
    val isLoading: Boolean,
    val isSuccessful: Boolean? = null,
    val errorMessage: String? = null,
    val data: WeatherData? = null
)
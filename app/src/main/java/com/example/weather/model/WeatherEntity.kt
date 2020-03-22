package com.example.weather.model

import java.util.Date

data class WeatherEntity(
    val cityName: String,
    val temperature: Double,
    val pressure: Int,
    val description: String,
    val icon: String,
    val sunrise: Int,
    val sunset: Int,
    val datetime: Date
)
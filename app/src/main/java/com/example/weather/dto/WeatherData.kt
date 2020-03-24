package com.example.weather.dto

import java.util.Date

data class WeatherData(
    val cityName: String,
    val temperature: Int,
    val pressure: Int,
    val description: String,
    val iconResource: Int,
    val sunrise: Date,
    val sunset: Date,
    val datetime: Date
)
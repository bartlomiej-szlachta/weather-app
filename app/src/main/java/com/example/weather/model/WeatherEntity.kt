package com.example.weather.model

import java.util.*

data class WeatherEntity(
    private val cityName: String,
    private val temperature: Double,
    private val pressure: Int,
    private val description: String,
    private val icon: String,
    private val sunrise: Int,
    private val sunset: Int,
    private val datetime: Date
)
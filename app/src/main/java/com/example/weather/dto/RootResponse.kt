package com.example.weather.dto

import com.example.weather.model.WeatherEntity
import com.google.gson.annotations.SerializedName
import java.util.Date

data class RootResponse(
    @SerializedName("name") private val cityName: String,
    @SerializedName("main") private val main: MainResponse,
    @SerializedName("weather") private val weather: List<WeatherResponse>,
    @SerializedName("sys") private val sys: SysResponse,
    @SerializedName("dt") private val dt: Long
) {
    fun format(): WeatherEntity = WeatherEntity(
        cityName,
        main.temperature - 273.15,
        main.pressure,
        weather[0].description,
        weather[0].icon,
        sys.sunrise,
        sys.sunset,
        Date(dt)
    )
}
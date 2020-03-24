package com.example.weather.network

import com.example.weather.R
import com.example.weather.dto.WeatherData
import com.google.gson.annotations.SerializedName
import java.util.Date
import kotlin.math.roundToInt

data class RootResponse(
    @SerializedName("name") private val cityName: String,
    @SerializedName("main") private val main: MainResponse,
    @SerializedName("weather") private val weather: List<WeatherResponse>,
    @SerializedName("sys") private val sys: SysResponse,
    @SerializedName("dt") private val dt: Long
) {
    fun format(): WeatherData =
        WeatherData(
            cityName,
            (main.temperature - 273.15).roundToInt(),
            main.pressure,
            weather[0].description,
            getIconResource(weather[0].icon),
            Date(sys.sunrise * 1000),
            Date(sys.sunset * 1000),
            Date(dt * 1000)
        )

    private fun getIconResource(iconName: String): Int = when (iconName.substring(0, 2).toInt()) {
        1 -> R.drawable.icon_weather_01d
        2 -> R.drawable.icon_weather_02d
        3 -> R.drawable.icon_weather_03d
        4 -> R.drawable.icon_weather_04d
        9 -> R.drawable.icon_weather_09d
        10 -> R.drawable.icon_weather_10d
        11 -> R.drawable.icon_weather_11d
        13 -> R.drawable.icon_weather_13d
        50 -> R.drawable.icon_weather_50d
        else -> throw RuntimeException("no image for the icon specified")
    }
}
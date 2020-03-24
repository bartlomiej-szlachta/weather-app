package com.example.weather.network

import com.example.weather.network.response.RootResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("/data/2.5/weather")
    fun getWeatherData(@Query("q") cityName: String): Call<RootResponse>
}
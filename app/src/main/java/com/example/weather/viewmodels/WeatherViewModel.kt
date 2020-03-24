package com.example.weather.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.weather.model.WeatherResult
import com.example.weather.network.WeatherRepository

class WeatherViewModel : ViewModel() {
    private val repository: WeatherRepository = WeatherRepository.getInstance()

    private lateinit var weatherData: LiveData<WeatherResult>

    fun getWeatherResult(cityName: String): LiveData<WeatherResult> {
        weatherData = repository.getWeatherData(cityName)
        return weatherData
    }
}
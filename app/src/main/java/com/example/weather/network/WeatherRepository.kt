package com.example.weather.network

import androidx.lifecycle.MutableLiveData
import com.example.weather.R
import com.example.weather.model.WeatherData
import com.example.weather.model.WeatherResult
import com.example.weather.network.response.RootResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Date
import kotlin.math.roundToInt

class WeatherRepository {
    companion object {
        private var repository: WeatherRepository? = null

        fun getInstance(): WeatherRepository {
            if (repository == null) {
                repository = WeatherRepository()
            }
            return repository!!
        }
    }

    private var api: WeatherApi = RetrofitService.createService()

    fun getWeatherData(cityName: String): MutableLiveData<WeatherResult> {
        val weatherData: MutableLiveData<WeatherResult> = MutableLiveData()
        weatherData.value = WeatherResult(isLoading = true)
        api.getWeatherData(cityName).enqueue(object : Callback<RootResponse> {
            override fun onResponse(call: Call<RootResponse>, response: Response<RootResponse>) {
                if (response.code() != 200) {
                    weatherData.value = WeatherResult(
                        isLoading = false,
                        isSuccessful = false,
                        errorMessage = response.message()
                    )
                    return
                }
                weatherData.value = WeatherResult(
                    isLoading = false,
                    isSuccessful = true,
                    data = format(response.body()!!)
                )
            }

            override fun onFailure(call: Call<RootResponse>, t: Throwable) {
                weatherData.value = WeatherResult(
                    isLoading = false,
                    isSuccessful = false,
                    errorMessage = t.message
                )
            }
        })
        return weatherData
    }

    fun format(data: RootResponse): WeatherData = WeatherData(
        cityName = data.cityName,
        temperature = (data.main.temperature - 273.15).roundToInt(),
        pressure = data.main.pressure,
        description = data.weather[0].description,
        iconResource = getIconResource(data.weather[0].icon),
        sunrise = Date(data.sys.sunrise * 1000),
        sunset = Date(data.sys.sunset * 1000),
        datetime = Date(data.dt * 1000)
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
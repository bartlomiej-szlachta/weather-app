package com.example.weather.network

import androidx.lifecycle.MutableLiveData
import com.example.weather.model.WeatherResult
import com.example.weather.network.response.RootResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
                    data = response.body()!!.format()
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
}
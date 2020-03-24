package com.example.weather.network

import com.example.weather.BuildConfig
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("/data/2.5/weather")
    fun getWeatherData(@Query("q") cityName: String): Call<RootResponse>

    companion object {
        fun create(): WeatherApiService {
            val authInterceptor = Interceptor { chain ->
                val newUrl: HttpUrl = chain.request().url
                    .newBuilder()
                    .addQueryParameter("APPID",
                        BuildConfig.API_KEY
                    )
                    .build()
                val newRequest: Request = chain.request()
                    .newBuilder()
                    .url(newUrl)
                    .build()
                chain.proceed(newRequest)
            }
            val client = OkHttpClient().newBuilder()
                .addInterceptor(authInterceptor)
                .build()
            val retrofit: Retrofit = Retrofit.Builder()
                .client(client)
                .baseUrl("https://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(WeatherApiService::class.java)
        }
    }
}
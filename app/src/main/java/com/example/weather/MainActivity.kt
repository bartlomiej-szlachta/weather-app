package com.example.weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.weather.dto.RootResponse
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val apiService: WeatherApiService by lazy {
        WeatherApiService.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            textView.text = "loading"
            apiService.getWeatherData(editText.text.toString())
                .enqueue(WeatherDataResponseHandler())
        }
    }

    private inner class WeatherDataResponseHandler : Callback<RootResponse> {
        override fun onResponse(call: Call<RootResponse>, response: Response<RootResponse>) {
            if (response.body() == null) {
                textView.text = "Failure"
                return
            }
            textView.text = response.body().toString()
        }

        override fun onFailure(call: Call<RootResponse>, t: Throwable) {
            textView.text = "Failure"
        }
    }
}

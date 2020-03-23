package com.example.weather

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.weather.dto.RootResponse
import com.example.weather.model.WeatherEntity
import kotlinx.android.synthetic.main.activity_main.label_error
import kotlinx.android.synthetic.main.activity_main.progress_bar
import kotlinx.android.synthetic.main.card_input.input_city_text
import kotlinx.android.synthetic.main.card_weather.card_info
import kotlinx.android.synthetic.main.card_weather.icon_weather
import kotlinx.android.synthetic.main.card_weather.value_city_name
import kotlinx.android.synthetic.main.card_weather.value_datetime
import kotlinx.android.synthetic.main.card_weather.value_description
import kotlinx.android.synthetic.main.card_weather.value_pressure
import kotlinx.android.synthetic.main.card_weather.value_sunrise
import kotlinx.android.synthetic.main.card_weather.value_sunset
import kotlinx.android.synthetic.main.card_weather.value_temperature
import kotlinx.android.synthetic.main.layout_welcome.label_welcome
import kotlinx.android.synthetic.main.layout_welcome.layout_welcome
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DateFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private val apiService: WeatherApiService by lazy {
        WeatherApiService.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showWelcome()

        input_city_text.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().isEmpty()) {
                    showWelcome()
                } else {
                    showLoading()
                    apiService
                        .getWeatherData(s.toString())
                        .enqueue(WeatherDataResponseHandler())
                }
            }
        })
    }

    private inner class WeatherDataResponseHandler : Callback<RootResponse> {
        @ExperimentalStdlibApi
        override fun onResponse(call: Call<RootResponse>, response: Response<RootResponse>) {
            if (response.code() != 200) {
                showError(response.message())
                return
            }

            val rootResponse: RootResponse = response.body()!!
            showData(rootResponse.format())
        }

        override fun onFailure(call: Call<RootResponse>, t: Throwable) {
            showError(t.message ?: "Error while loading data")
        }
    }

    private fun showWelcome() {
        layout_welcome.visibility = View.VISIBLE
        progress_bar.visibility = View.GONE
        label_error.visibility = View.GONE
        card_info.visibility = View.GONE
    }

    private fun showLoading() {
        layout_welcome.visibility = View.GONE
        progress_bar.visibility = View.VISIBLE
        label_error.visibility = View.GONE
        card_info.visibility = View.GONE
    }

    private fun showError(message: String) {
        layout_welcome.visibility = View.GONE
        progress_bar.visibility = View.GONE
        label_error.visibility = View.VISIBLE
        card_info.visibility = View.GONE

        label_error.text = message
    }

    @ExperimentalStdlibApi
    private fun showData(data: WeatherEntity) {
        layout_welcome.visibility = View.GONE
        progress_bar.visibility = View.GONE
        label_error.visibility = View.GONE
        card_info.visibility = View.VISIBLE

        icon_weather.setImageResource(data.iconResource)
        value_city_name.text = data.cityName
        value_description.text = data.description.capitalize(Locale.ROOT)
        value_temperature.text = data.temperature.toString()
        value_pressure.text = getString(R.string.template_pressure, data.pressure.toString())
        value_sunrise.text = getString(
            R.string.template_sunrise,
            DateFormat.getTimeInstance().format(data.sunrise)
        )
        value_sunset.text = getString(
            R.string.template_sunset,
            DateFormat.getTimeInstance().format(data.sunset)
        )
        value_datetime.text = getString(
            R.string.template_datetime,
            DateFormat.getDateTimeInstance().format(data.datetime)
        )
    }
}

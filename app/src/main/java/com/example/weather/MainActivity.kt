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
import kotlinx.android.synthetic.main.layout_input.input_city_text
import kotlinx.android.synthetic.main.layout_weather.icon_weather
import kotlinx.android.synthetic.main.layout_weather.layout_info
import kotlinx.android.synthetic.main.layout_weather.value_city_name
import kotlinx.android.synthetic.main.layout_weather.value_datetime
import kotlinx.android.synthetic.main.layout_weather.value_description
import kotlinx.android.synthetic.main.layout_weather.value_pressure
import kotlinx.android.synthetic.main.layout_weather.value_sunrise
import kotlinx.android.synthetic.main.layout_weather.value_sunset
import kotlinx.android.synthetic.main.layout_weather.value_temperature
import kotlinx.android.synthetic.main.layout_welcome.layout_welcome
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
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
                val message = when (response.code()) {
                    404 -> "No location found with the name provided"
                    else -> response.message()
                }
                showError(message)
                return
            }

            val rootResponse: RootResponse = response.body()!!
            showData(rootResponse.format())
        }

        override fun onFailure(call: Call<RootResponse>, t: Throwable) {
            if (t.message == null) {
                showError("Error while loading data")
                return
            }
            if (t.message!!.toString().contains("Unable to resolve host")) {
                showError("Mobile data is off")
                return
            }
            showError(t.message.toString())
        }
    }

    private fun showWelcome() {
        layout_welcome.visibility = View.VISIBLE
        progress_bar.visibility = View.GONE
        label_error.visibility = View.GONE
        layout_info.visibility = View.GONE
    }

    private fun showLoading() {
        layout_welcome.visibility = View.GONE
        progress_bar.visibility = View.VISIBLE
        label_error.visibility = View.GONE
        layout_info.visibility = View.GONE
    }

    private fun showError(message: String) {
        if (input_city_text.text.toString().isEmpty()) {
            showWelcome()
            return
        }

        layout_welcome.visibility = View.GONE
        progress_bar.visibility = View.GONE
        label_error.visibility = View.VISIBLE
        layout_info.visibility = View.GONE

        label_error.text = message
    }

    @ExperimentalStdlibApi
    private fun showData(data: WeatherEntity) {
        layout_welcome.visibility = View.GONE
        progress_bar.visibility = View.GONE
        label_error.visibility = View.GONE
        layout_info.visibility = View.VISIBLE

        val dateTimeFormatter = SimpleDateFormat("dd-MM-yyyy hh:mm", Locale.getDefault())
        val timeFormatter = SimpleDateFormat("hh:mm", Locale.getDefault())

        icon_weather.setImageResource(data.iconResource)
        value_city_name.text = data.cityName
        value_description.text = data.description.capitalize(Locale.ROOT)
        value_temperature.text = data.temperature.toString()
        value_pressure.text = getString(R.string.template_pressure, data.pressure.toString())
        value_sunrise.text = getString(
            R.string.template_sunrise,
            timeFormatter.format(data.sunrise)
        )
        value_sunset.text = getString(
            R.string.template_sunset,
            timeFormatter.format(data.sunset)
        )
        value_datetime.text = getString(
            R.string.template_datetime,
            dateTimeFormatter.format(data.datetime)
        )
    }
}

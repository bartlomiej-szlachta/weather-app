package com.example.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.weather.model.WeatherEntity
import kotlinx.android.synthetic.main.fragment_weather.value_city_name
import kotlinx.android.synthetic.main.fragment_weather.value_datetime
import kotlinx.android.synthetic.main.fragment_weather.value_description
import kotlinx.android.synthetic.main.fragment_weather.value_icon
import kotlinx.android.synthetic.main.fragment_weather.value_pressure
import kotlinx.android.synthetic.main.fragment_weather.value_sunrise
import kotlinx.android.synthetic.main.fragment_weather.value_sunset
import kotlinx.android.synthetic.main.fragment_weather.value_temperature
import java.text.DateFormat.getDateTimeInstance
import java.text.DateFormat.getTimeInstance

class WeatherFragment(private val data: WeatherEntity) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        value_city_name.text = data.cityName
        value_icon.text = data.icon
        value_description.text = data.description
        value_temperature.text = data.temperature.toString()
        value_pressure.text = data.pressure.toString()
        value_sunrise.text = getTimeInstance().format(data.sunrise)
        value_sunset.text = getTimeInstance().format(data.sunset)
        value_datetime.text = getDateTimeInstance().format(data.datetime)
    }
}

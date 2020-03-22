package com.example.weather

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.weather.model.WeatherEntity
import kotlinx.android.synthetic.main.fragment_weather.*

class WeatherFragment(private val data: WeatherEntity) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
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
        value_sunrise.text = data.sunrise.toString()
        value_sunset.text = data.sunset.toString()
        value_datetime.text = data.datetime.toString()
    }
}

package com.example.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.weather.dto.RootResponse
import kotlinx.android.synthetic.main.fragment_input.button_submit
import kotlinx.android.synthetic.main.fragment_input.input_city
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InputFragment(private val currentCityName: String = "") : Fragment() {

    private val apiService: WeatherApiService = WeatherApiService.create()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_input, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        input_city.setText(currentCityName)

        button_submit.setOnClickListener {
            apiService
                .getWeatherData(input_city.text.toString())
                .enqueue(WeatherDataResponseHandler())
        }
    }

    private inner class WeatherDataResponseHandler : Callback<RootResponse> {
        override fun onResponse(call: Call<RootResponse>, response: Response<RootResponse>) {
            if (response.code() != 200) {
                Toast.makeText(activity, response.message(), Toast.LENGTH_SHORT).show()
                return
            }

            val rootResponse: RootResponse = response.body()!!
            activity!!
                .supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, WeatherFragment(rootResponse.format()))
                .commit()
        }

        override fun onFailure(call: Call<RootResponse>, t: Throwable) {
            Toast.makeText(activity, "Failue", Toast.LENGTH_SHORT).show()
        }
    }
}

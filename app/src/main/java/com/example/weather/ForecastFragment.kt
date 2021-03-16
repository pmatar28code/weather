package com.example.weather

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weather.databinding.ForecastFragmentBinding
import com.example.weather.repositories.WeatherRepository

class ForecastFragment: Fragment(R.layout.forecast_fragment) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ForecastFragmentBinding.bind(view)


        binding.forecastItem.apply{
            adapter = ForecastAdapter(WeatherRepository.forecastList)
            Handler().postDelayed(
                    {
                        adapter?.notifyDataSetChanged()
                        // This method will be executed once the timer is over
                    },
                    5000 // value in milliseconds
            )
            layoutManager = LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL,
                false)
        }
    }
}
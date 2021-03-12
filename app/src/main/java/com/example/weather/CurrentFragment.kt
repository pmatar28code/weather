package com.example.weather

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weather.databinding.CurrentFragmentBinding
import com.example.weather.databinding.ForecastFragmentBinding
import com.example.weather.repositories.WeatherRepository
import java.util.*

class CurrentFragment: Fragment(R.layout.current_fragment) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = CurrentFragmentBinding.bind(view)

        binding.currentTempText.text = WeatherRepository.currentWeather?.main?.temp.toString()
        binding.currentDescriptionText.text =WeatherRepository.currentWeather?.weather?.description
        val dateNow = Calendar.getInstance().time
        binding.dateText.text = dateNow.toString()

    }
}

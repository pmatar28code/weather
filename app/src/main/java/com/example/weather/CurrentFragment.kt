package com.example.weather

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weather.databinding.CurrentFragmentBinding
import com.example.weather.databinding.ForecastFragmentBinding
import com.example.weather.repositories.WeatherRepository
import com.squareup.picasso.Picasso
import java.util.*

class CurrentFragment: Fragment(R.layout.current_fragment) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = CurrentFragmentBinding.bind(view)

        binding.currentTempText.text = WeatherRepository.currentWeather?.main?.temp.toString()
        binding.currentDescriptionText.text =WeatherRepository.currentWeather?.weather?.description
        val dateNow = Calendar.getInstance().time
        binding.dateText.text = dateNow.toString()
        binding.locationText.text = "lat: ${WeatherRepository.locationObj.lat} lon: ${WeatherRepository.locationObj.lon}"
        Picasso.get().load(getCurrentImage(getImageCode())).into(binding.currentImage)
    }

    fun getCurrentImage(iconCode:String):String {
        var iconUrl = "http://openweathermap.org/img/w/$iconCode.png";
        return iconUrl
    }
    fun getImageCode():String{
        val imageCode = WeatherRepository.currentWeather?.weather?.icon.toString()
        return imageCode
    }
}


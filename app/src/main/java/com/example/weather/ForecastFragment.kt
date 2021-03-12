package com.example.weather

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weather.databinding.ForecastFragmentBinding
import com.example.weather.repositories.WeatherRepository
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ForecastFragment: Fragment(R.layout.forecast_fragment) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ForecastFragmentBinding.bind(view)


        binding.forecastItem.apply{
            adapter = ForecastAdapter(WeatherRepository.forecastList)
            //WeatherRepository.callGetForecast(WeatherRepository.locationObj.context!!,WeatherRepository.locationObj.lat,WeatherRepository.locationObj.lon,"metric","1fb88785b228435d667386a70a2eb3b4")
            Handler().postDelayed(
                    {
                        adapter?.notifyDataSetChanged()
                        // This method will be executed once the timer is over
                    },
                    5000 // value in milliseconds
            )
            // adapter?.notifyDataSetChanged()
            //Toast.makeText(context, "recycler loaded get Weather ", Toast.LENGTH_LONG).show()
            layoutManager = LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL,
                false)
        }
    }
}
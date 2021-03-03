package com.example.weather.repository

import android.util.Log
import android.widget.Toast
import com.example.weather.MainActivity
import com.example.weather.api.CurrentWeather
import com.example.weather.api.createOpenWeatherMapService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForeCastRepository {

    var currentForecast:CurrentWeather ?= null

    fun loadCurrentForecast(zipcode : String){
        val call = createOpenWeatherMapService().currentWeather(zipcode ,"metric","1fb88785b228435d667386a70a2eb3b4")
        call.enqueue(object : Callback<CurrentWeather>{
            override fun onFailure(call: Call<CurrentWeather>, t: Throwable) {
                Log.v("Failure","enque failed to get current weather")

            }

            override fun onResponse(call: Call<CurrentWeather>, response: Response<CurrentWeather>) {
                val weatherResponse = response.body()
                if(weatherResponse != null){
                    currentForecast = weatherResponse

                }
            }

        })
    }



}
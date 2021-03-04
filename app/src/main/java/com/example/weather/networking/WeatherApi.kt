package com.example.weather.networking

import com.example.weather.WeatherServer
import retrofit2.Call
import retrofit2.http.GET

interface WeatherApi {
    @GET("/data.2.5/weather")
    fun getWeather(): Call<WeatherServer>

}
package com.example.weather.networking

import com.example.weather.Weather
import com.example.weather.WeatherServer
import com.example.weather.networking.Network.getWeather
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*

object Network {

    var testing = "nothing"
    val list = mutableListOf<Weather>()
    val client = OkHttpClient()
    val weatherApi:WeatherApi
        get(){
            return Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org")
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(WeatherApi::class.java)
        }
    fun getWeather(){
        //if(list.isNotEmpty()){
            //onSuccess(list)
        //}
        weatherApi.getWeather().enqueue(object : Callback<WeatherServer>{
            override fun onFailure(call: Call<WeatherServer>, t: Throwable) {
                testing = "failure"
            }

            override fun onResponse(call: Call<WeatherServer>, response: Response<WeatherServer>) {
                var weatherMod = Weather(temperature = response.body()!!.main.temp)
                list.add(weatherMod)
                testing = "onResponse"

            }

        })


    }




}
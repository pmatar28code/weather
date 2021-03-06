package com.example.weather

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    //"weather?lat=35&lon=139&appid={API key}"
    @GET("data/2.5/weather?")
    fun getWeather(@Query("lat") lat : Int,@Query("lon") lon :Int,@Query("appid") appid : String) : Call<WeatherServer>
    //@Query("lat") lat : Int,@Query("lon") lon :Int,@Query("appid") appid : String
}
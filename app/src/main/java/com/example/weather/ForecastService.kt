package com.example.weather

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ForecastService {
    @GET("data/2.5/onecall?")
    fun getForecast(@Query("lat") lat : Double, @Query("lon")
    lon : Double, @Query("units") units : String, @Query("appid") appid : String
    ): Call<ForecastServer>
}
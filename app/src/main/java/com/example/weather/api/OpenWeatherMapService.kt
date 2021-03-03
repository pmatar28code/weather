package com.example.weather.api

import com.google.android.gms.common.api.internal.ApiKey
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private val client = OkHttpClient()

fun createOpenWeatherMapService() : OpenWeatherMapService{
    val retrofit = Retrofit.Builder()
            .baseUrl("http://openweathermap.org")
            //.client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            return retrofit.create(OpenWeatherMapService::class.java)

}

interface OpenWeatherMapService {

    @GET("/data.2.5/weather")
        fun currentWeather(
            @Query("zip") zipcode : String,
            @Query("units") units : String,
            @Query("appid") apiKey: String
    ) : Call<CurrentWeather>

    }

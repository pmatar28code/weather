package com.example.weather

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class CurrentForecast(

        val list: List<ForeC>

)
data class ForeC(
        val clouds: Clouds,
        val dt: Int,
        val dtTxt: String,
        val main: Main,
        val pop: Int,
        val rain: Rain,
        val sys: Sys,
        val visibility: Int,
        val weather: List<Weather>,
        val wind: Wind
) {
    data class Clouds(
            val all: Int
    )

    data class Main(
            val feelsLike: Double,
            val grndLevel: Int,
            val humidity: Int,
            val pressure: Int,
            val seaLevel: Int,
            val temp: Double,
            val tempKf: Double,
            val tempMax: Double,
            val tempMin: Double
    )

    data class Rain(
            val h: Double
    )

    data class Sys(
            val pod: String
    )

    data class Weather(
            val description: String,
            val icon: String,
            val id: Int,
            val main: String
    )

    data class Wind(
            val deg: Int,
            val speed: Double
    )
}
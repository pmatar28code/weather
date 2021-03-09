package com.example.weather

data class CurrentWeather(
        val coord: Coord,
        val weather:  Weather,
        val base: String,
        val main: Main,
       // val visibilityFactor: Int, // as "visibility"
        val wind: Wind,
        val clouds: Clouds,
        val dt: Int,   // time of data collection.
        val sys: Sys,
        val timezone: Int,  // Shift in seconds from UTC
        val id: Int,
        val name: String,
        val cod: Int   // internal parameter
)

data class Coord(
        val lon: Double,
        val lat: Double
)

data class Weather(
        val id: Int,
        val main: String,
        val description: String,
        val icon: String
)

// ** As "Main" for name.
data class Main(
        val temp : Double,
        val feelsLike : Double,
        val tempMin: Double,
        val tempMax: Double,
        val pressure: Int,
        val humidity: Int
)

data class Wind(
        val speed: Double,
        val deg: Double
)

data class Clouds (
        val all: Int
)

data class Sys (
        val type: Int?,
        val id: Int?,
        val message: Double?,
        val country: String,
        val sunrise: Int,
        val sunset: Int
)
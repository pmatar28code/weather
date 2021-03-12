package com.example.weather


data class CurrentForecast(
        val current: Current,
        val daily: List<Daily>,
        val hourly: List<Hourly>,
        val lat: Int,
        val lon: Int,
        val minutely: List<Minutely>,
        val timezone: String,
        val timezoneOffset: Int
) {
    data class Current(
            val clouds: Int,
            val dewPoint: Double,
            val dt: Int,
            val feelsLike: Double,
            val humidity: Int,
            val pressure: Int,
            val sunrise: Int,
            val sunset: Int,
            val temp: Double,
            val uvi: Int,
            val visibility: Int,
            val weather: List<Weather>,
            val windDeg: Int,
            val windSpeed: Double
    ) {
        data class Weather(
                val description: String,
                val icon: String,
                val id: Int,
                val main: String
        )
    }

    data class Daily(
            val clouds: Int,
            val dewPoint: Double,
            val dt: Int,
            val feelsLike: ForecastServer.Daily.FeelsLike,
            val humidity: Int,
            val pop: Double,
            val pressure: Int,
           // val rain: Double,
            val sunrise: Int,
            val sunset: Int,
            val temp: ForecastServer.Daily.Temp,
            val uvi: Double,
            val weather: List<ForecastServer.Daily.Weather>,
            val windDeg: Int,
            val windSpeed: Double
    ) {
        data class FeelsLike(
                val day: Double,
                val eve: Double,
                val morn: Double,
                val night: Double
        )

        data class Temp(
                val day: Double,
                val eve: Double,
                val max: Double,
                val min: Double,
                val morn: Double,
                val night: Double
        )

        data class Weather(
                val description: String,
                val icon: String,
                val id: Int,
                val main: String
        )
    }

    data class Hourly(
            val clouds: Int,
            val dewPoint: Double,
            val dt: Int,
            val feelsLike: Double,
            val humidity: Int,
            val pop: Double,
            val pressure: Int,
            //val rain: Rain?,
            val temp: Double
           // val uvi: Int?,
            //val visibility: Int?,
            //val weather: List<Weather>,
           // val windDeg: Int?,
           // val windSpeed: Double?
    ) {
        data class Rain(
                val h: Double
        )

        data class Weather(
                val description: String,
                val icon: String,
                val id: Int,
                val main: String
        )
    }

    data class Minutely(
            val dt: Int,
            val precipitation: Int
    )
}
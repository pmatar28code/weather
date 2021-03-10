package com.example.weather


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ForecastServer(
        @Json(name = "current")
        val current: Current,
        @Json(name = "daily")
        val daily: List<Daily>,
        @Json(name = "hourly")
        val hourly: List<Hourly>,
        @Json(name = "lat")
        val lat: Int,
        @Json(name = "lon")
        val lon: Int,
        @Json(name = "minutely")
        val minutely: List<Minutely>,
        @Json(name = "timezone")
        val timezone: String,
        @Json(name = "timezone_offset")
        val timezoneOffset: Int
) {
    @JsonClass(generateAdapter = true)
    data class Current(
            @Json(name = "clouds")
            val clouds: Int,
            @Json(name = "dew_point")
            val dewPoint: Double,
            @Json(name = "dt")
            val dt: Int,
            @Json(name = "feels_like")
            val feelsLike: Double,
            @Json(name = "humidity")
            val humidity: Int,
            @Json(name = "pressure")
            val pressure: Int,
            @Json(name = "sunrise")
            val sunrise: Int,
            @Json(name = "sunset")
            val sunset: Int,
            @Json(name = "temp")
            val temp: Double,
            @Json(name = "uvi")
            val uvi: Int,
            @Json(name = "visibility")
            val visibility: Int,
            @Json(name = "weather")
            val weather: List<Weather>,
            @Json(name = "wind_deg")
            val windDeg: Int,
            @Json(name = "wind_speed")
            val windSpeed: Double
    ) {
        @JsonClass(generateAdapter = true)
        data class Weather(
                @Json(name = "description")
                val description: String,
                @Json(name = "icon")
                val icon: String,
                @Json(name = "id")
                val id: Int,
                @Json(name = "main")
                val main: String
        )
    }

    @JsonClass(generateAdapter = true)
    data class Daily(
            @Json(name = "clouds")
            val clouds: Int,
            @Json(name = "dew_point")
            val dewPoint: Double,
            @Json(name = "dt")
            val dt: Int,
            @Json(name = "feels_like")
            val feelsLike: FeelsLike,
            @Json(name = "humidity")
            val humidity: Int,
            @Json(name = "pop")
            val pop: Double,
            @Json(name = "pressure")
            val pressure: Int,
            @Json(name = "rain")
            val rain: Double,
            @Json(name = "sunrise")
            val sunrise: Int,
            @Json(name = "sunset")
            val sunset: Int,
            @Json(name = "temp")
            val temp: Temp,
            @Json(name = "uvi")
            val uvi: Double,
            @Json(name = "weather")
            val weather: List<Weather>,
            @Json(name = "wind_deg")
            val windDeg: Int,
            @Json(name = "wind_speed")
            val windSpeed: Double
    ) {
        @JsonClass(generateAdapter = true)
        data class FeelsLike(
                @Json(name = "day")
                val day: Double,
                @Json(name = "eve")
                val eve: Double,
                @Json(name = "morn")
                val morn: Double,
                @Json(name = "night")
                val night: Double
        )

        @JsonClass(generateAdapter = true)
        data class Temp(
                @Json(name = "day")
                val day: Double,
                @Json(name = "eve")
                val eve: Double,
                @Json(name = "max")
                val max: Double,
                @Json(name = "min")
                val min: Double,
                @Json(name = "morn")
                val morn: Double,
                @Json(name = "night")
                val night: Double
        )

        @JsonClass(generateAdapter = true)
        data class Weather(
                @Json(name = "description")
                val description: String,
                @Json(name = "icon")
                val icon: String,
                @Json(name = "id")
                val id: Int,
                @Json(name = "main")
                val main: String
        )
    }

    @JsonClass(generateAdapter = true)
    data class Hourly(
            @Json(name = "clouds")
            val clouds: Int,
            @Json(name = "dew_point")
            val dewPoint: Double,
            @Json(name = "dt")
            val dt: Int,
            @Json(name = "feels_like")
            val feelsLike: Double,
            @Json(name = "humidity")
            val humidity: Int,
            @Json(name = "pop")
            val pop: Double,
            @Json(name = "pressure")
            val pressure: Int,
            @Json(name = "rain")
            val rain: Rain,
            @Json(name = "temp")
            val temp: Double,
            @Json(name = "uvi")
            val uvi: Int,
            @Json(name = "visibility")
            val visibility: Int,
            @Json(name = "weather")
            val weather: List<Weather>,
            @Json(name = "wind_deg")
            val windDeg: Int,
            @Json(name = "wind_speed")
            val windSpeed: Double
    ) {
        @JsonClass(generateAdapter = true)
        data class Rain(
                @Json(name = "1h")
                val h: Double
        )

        @JsonClass(generateAdapter = true)
        data class Weather(
                @Json(name = "description")
                val description: String,
                @Json(name = "icon")
                val icon: String,
                @Json(name = "id")
                val id: Int,
                @Json(name = "main")
                val main: String
        )
    }

    @JsonClass(generateAdapter = true)
    data class Minutely(
            @Json(name = "dt")
            val dt: Int,
            @Json(name = "precipitation")
            val precipitation: Int
    )
}
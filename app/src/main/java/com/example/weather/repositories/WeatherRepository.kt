package com.example.weather.repositories

import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import com.example.weather.*
import com.example.weather.databinding.ActivityMainBinding
import com.example.weather.networking.ForecastClient
import com.example.weather.networking.WeatherClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import java.time.*

object WeatherRepository {
    class Location(){
        var lon:Double = 0.0
        var lat:Double = 0.0
        var context:Context ?= null
    }
    var locationObj = Location()
    var currentWeather: CurrentWeather?= null
    var currentWeatherRecycler:CurrentWeather?= null
    var forecastList = mutableListOf<CurrentForecast.Daily>()
    var currentList = mutableListOf<CurrentForecast.Hourly>()
    var currentTemp:String = ""
    var tempTest = ""
    var testAtCero =""

    fun getShortDate(ts: Long?): String {
        if (ts == null) return ""
        //Get instance of calendar
        val calendar = Calendar.getInstance(Locale.US)
        //get current date from ts
        calendar.timeInMillis = ts * 1000
        //return formatted date
        return android.text.format.DateFormat.format("E, dd MMM yyyy", calendar).toString()
    }

    fun getDateTimeFromEpocLongOfSeconds(epoc: Long): String? {
        try {
            val netDate = Date(epoc*1000)
            return netDate.toString()
        }
        catch (e: Exception) {
            return e.toString()
        }
    }






    private fun WeatherServer.toCurrentWeather(): CurrentWeather {
        return CurrentWeather(
                coord = Coord(
                        coord.lon,
                        coord.lat
                ),
                weather = Weather(
                        id = weather[0].id,
                        main = weather[0].main,
                        description = weather[0].description,
                        icon = weather[0].icon
                ),
                base = base,
                main = Main(
                        temp = main.temp,
                        feelsLike = main.feelsLike,
                        tempMin = main.tempMin,
                        tempMax = main.tempMax,
                        pressure = main.pressure,
                        humidity = main.humidity
                ),
                //visibilityFactor = visibilityFactor,
                wind = Wind(
                        speed = wind.speed,
                        deg = wind.deg
                ),
                clouds = Clouds(
                        all = clouds.all
                ),
                dt = dt,
                sys = Sys(
                        type = sys.type,
                        id = sys.id,
                        message = sys.message,
                        country = sys.country,
                        sunrise = sys.sunrise,
                        sunset = sys.sunset
                ),
                timezone = timezone,
                id = id,
                name = name,
                cod = cod
        )
    }

    fun callGetWeather(context: Context, lat: Double, lon: Double, units: String, apiKey: String) {
        WeatherClient.weatherService.getWeather(lat, lon, units, apiKey).enqueue(object : Callback<WeatherServer> {
            override fun onFailure(call: Call<WeatherServer>, t: Throwable) {
                Toast.makeText(context, "onFailure get Weather $t", Toast.LENGTH_LONG).show()
            }
            override fun onResponse(call: Call<WeatherServer>, response: Response<WeatherServer>) {
                if (response.isSuccessful) {
                    currentTemp = response.body()?.main?.temp.toString()
                    //textWeather.text = response.body()?.main?.temp.toString()
                    Toast.makeText(context, "onResponse get Weather ${response.body()?.main?.tempMax}", Toast.LENGTH_LONG).show()
                    currentWeather = response.body()?.toCurrentWeather()
                    tempTest = currentWeather?.weather?.description.toString()
                }
            }
        })
    }

    fun callGetWeatherRecycler(context: Context, lat: Double, lon: Double, units: String, apiKey: String) {
        ForecastClient.ForecastService.getForecast(lat, lon, units, apiKey).enqueue(object : Callback<ForecastServer> {
            override fun onFailure(call: Call<ForecastServer>, t: Throwable) {
                Toast.makeText(context, "onFailure get Weather $t", Toast.LENGTH_LONG).show()
            }
            override fun onResponse(call: Call<ForecastServer>, response: Response<ForecastServer>) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "onResponse get Weather Day Recycler ", Toast.LENGTH_LONG).show()
                    var hourly = response.body()?.hourly
                    for(item in hourly!!){
                        var current = CurrentForecast.Hourly(
                                item.clouds,
                                item.temp,
                                item.dt,
                                item.feelsLike,
                                item.humidity,
                                item.pop,
                                item.pressure,
                                item.dewPoint,
                                item.weather
                        )
                        currentList.add(current)
                    }

                }
            }
        })
    }

    fun callGetForecast(context : Context,lat : Double,lon : Double,units : String , apikeyFore: String) {
        ForecastClient.ForecastService.getForecast(lat, lon,units ,apikeyFore).enqueue(object : Callback<ForecastServer> {
            override fun onFailure(call: Call<ForecastServer>, t: Throwable) {
                Toast.makeText(context, "onFailure Forecast$t", Toast.LENGTH_LONG).show()
            }
            override fun onResponse(call: Call<ForecastServer>, response: Response<ForecastServer>) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "onResponse Forecast ${response.body()?.current?.temp}", Toast.LENGTH_LONG).show()
                    var daily = response.body()?.daily
                    for(item in daily!!){
                        var current = CurrentForecast.Daily(
                                item.clouds,
                                item.dewPoint,
                                item.dt,
                                item.feelsLike,
                                item.humidity,
                                item.pop,
                                item.pressure,
                                //item.rain,
                                item.sunrise,
                                item.sunset,
                                item.temp,
                                item.uvi,
                                item.weather,
                                item.windDeg,
                                item.windSpeed
                        )
                        forecastList.add(current)
                    }
                    //val inflater = LayoutInflater.from(context)
                    //val binding = ActivityMainBinding.inflate(inflater)
                    //testAtCero = forecastList[0].temp.toString()
                }
            }
        })
    }

    fun ForecastServer.toCurrentForecast(): CurrentForecast.Daily {
        return CurrentForecast.Daily(
                clouds = daily[0].clouds,
                dewPoint = daily[0].dewPoint,
                dt = daily[0].dt,
                feelsLike = daily[0].feelsLike,
                humidity = daily[0].humidity,
                pop = daily[0].pop,
                pressure = daily[0].pressure,
                //rain = daily[0].rain,
                sunrise = daily[0].sunrise,
                sunset = daily[0].sunset,
                temp = daily[0].temp, //CurrentForecast.Daily.Temp,
                uvi = daily[0].uvi,
                weather = daily[0].weather, //List<CurrentForecast.Daily.Weather>,
                windDeg = daily[0].windDeg,
                windSpeed = daily[0].windSpeed
        )
    }
}
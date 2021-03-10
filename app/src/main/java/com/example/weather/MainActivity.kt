package com.example.weather

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weather.databinding.ActivityMainBinding
import com.example.weather.networking.ForecastClient
import com.example.weather.networking.WeatherClient
import com.example.weather.recycleview.ForecastAdapter
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity:AppCompatActivity() {
    private lateinit var fuseLocationProvider : FusedLocationProviderClient
    private val requestLocationCode = 100
    private val apiKey = "ec17ade9a281a44dcef2e94dd8e28fca"
    private val apikeyFore ="1fb88785b228435d667386a70a2eb3b4"
    private var lon = 0.00
    private var lat = 0.00
    private var units = "metric"
    var currentWeather: CurrentWeather? = null
    var forecastList = mutableListOf<CurrentForecast.Daily>()

    override fun onCreate(savedInstanceState: Bundle?)
    {

        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val forecastAdapter = ForecastAdapter()
        binding.forecastItem.apply {
           adapter = forecastAdapter
            forecastAdapter.submitList(forecastList)
           layoutManager = LinearLayoutManager(this@MainActivity)
        }

        fuseLocationProvider = LocationServices.getFusedLocationProviderClient(this)

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
           != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
           (this, Manifest.permission.ACCESS_COARSE_LOCATION
           ) != PackageManager.PERMISSION_GRANTED)
        {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION
                ,Manifest.permission.ACCESS_COARSE_LOCATION), requestLocationCode)
        } else {
            getLocationListener()

        }
    }

    private fun callGetWeather() {
        WeatherClient.weatherService.getWeather(lat, lon,units ,apiKey).enqueue(object : Callback<WeatherServer> {
            override fun onFailure(call: Call<WeatherServer>, t: Throwable) {
                Toast.makeText(this@MainActivity, "onFailure $t", Toast.LENGTH_LONG).show()
            }
            override fun onResponse(call: Call<WeatherServer>, response: Response<WeatherServer>) {
                if (response.isSuccessful) {
                    var textWeather = findViewById<TextView>(R.id.weather_text)
                    textWeather.text = response.body()?.main?.temp.toString()
                    Toast.makeText(this@MainActivity, "onResponse", Toast.LENGTH_LONG).show()
                    currentWeather = response.body()?.toCurrentWeather()
                    var textTest = findViewById<TextView>(R.id.textView)
                    textTest.text = currentWeather?.weather?.description


                }
            }
        })
    }

    private fun callGetForecast() {
        ForecastClient.ForecastService.getForecast(lat, lon,units ,apikeyFore).enqueue(object : Callback<ForecastServer> {
            override fun onFailure(call: Call<ForecastServer>, t: Throwable) {
                Toast.makeText(this@MainActivity, "onFailure Forecast$t", Toast.LENGTH_LONG).show()
            }
            override fun onResponse(call: Call<ForecastServer>, response: Response<ForecastServer>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@MainActivity, "onResponse Forecast", Toast.LENGTH_LONG).show()
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
    private fun WeatherServer.toCurrentWeather(): CurrentWeather {
        return CurrentWeather(
                coord = Coord(
                        coord.lon,
                        coord.lat
                ),
                weather = Weather(
                        id= weather[0].id,
                        main= weather[0].main,
                        description =weather[0].description,
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





    private fun getLocationListener() {
        var locationButton = findViewById<Button>(R.id.location_button).setOnClickListener {
        if (ActivityCompat.checkSelfPermission(
           this, Manifest.permission.ACCESS_FINE_LOCATION)
           != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
           this, Manifest.permission.ACCESS_COARSE_LOCATION)
           != PackageManager.PERMISSION_GRANTED) {
           assert(PackageManager.PERMISSION_GRANTED != null)
                return@setOnClickListener
        }
        fuseLocationProvider.lastLocation
        .addOnSuccessListener{
            if(it.latitude != null && it.longitude!=null) {
                 var textLocation = findViewById<TextView>(R.id.location_text)
                 textLocation.text = "Lat: ${it.latitude} - Lon: ${it.longitude}"
                 lat = it.latitude
                 lon = it.longitude
                 callGetWeather()
                 callGetForecast()
                 }
            }
       }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when{
            requestCode == requestLocationCode -> {
                if(permissions.contains(Manifest.permission.ACCESS_FINE_LOCATION)
                        || permissions.contains(Manifest.permission.ACCESS_COARSE_LOCATION)){
                    getLocationListener()
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}




package com.example.weather

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.databinding.ActivityMainBinding
import com.example.weather.recycleview.ForecastAdapter
import com.example.weather.repositories.WeatherRepository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class MainActivity:AppCompatActivity() {
    private lateinit var fuseLocationProvider : FusedLocationProviderClient
    private val requestLocationCode = 100
    private val apiKey = "ec17ade9a281a44dcef2e94dd8e28fca"
    private val apikeyFore ="1fb88785b228435d667386a70a2eb3b4"
    private var lon = 0.00
    private var lat = 0.00
    private var units = "metric"

    override fun onCreate(savedInstanceState: Bundle?)
    {

        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
                 WeatherRepository.callGetWeather(this,lat,lon,units,apiKey)
                 var weatherText = findViewById<TextView>(R.id.weather_text)
                 weatherText.text = WeatherRepository.currentWeather?.main?.temp.toString()
                 var tempTest = findViewById<TextView>(R.id.textView)
                 tempTest.text = WeatherRepository.currentWeather?.weather?.description
                 var testText = findViewById<TextView>(R.id.test_text)
                 testText.text = WeatherRepository.testAtCero
                 WeatherRepository.callGetForecast(this,lat,lon,units,apikeyFore)
                 val recycler = findViewById<RecyclerView>(R.id.forecast_item)
                 recycler.apply {
                     val forecastAdapter = ForecastAdapter()
                     adapter = TestAdapter(WeatherRepository.forecastList)
                     //forecastAdapter.submitList(forecastList)
                     layoutManager = LinearLayoutManager(this@MainActivity)
                    }
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




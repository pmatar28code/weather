package com.example.weather

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.weather.databinding.ActivityMainBinding
import com.example.weather.repositories.WeatherRepository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices

class MainActivity:AppCompatActivity() {

    private lateinit var fuseLocationProvider : FusedLocationProviderClient
    private val requestLocationCode = 100
    private val apiKey = "ec17ade9a281a44dcef2e94dd8e28fca"
    private val apikeyFore ="1fb88785b228435d667386a70a2eb3b4"
    var lon = 0.00
    var lat = 0.00
    private var units = "metric"
    private var locationRequest: LocationRequest? = null


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fuseLocationProvider = LocationServices.getFusedLocationProviderClient(this)

        if (ActivityCompat.checkSelfPermission(this
                        , Manifest.permission.ACCESS_FINE_LOCATION)
           != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
           (this, Manifest.permission.ACCESS_COARSE_LOCATION
           ) != PackageManager.PERMISSION_GRANTED)
        {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission
                        .ACCESS_FINE_LOCATION
                ,Manifest.permission.ACCESS_COARSE_LOCATION), requestLocationCode)
        } else {
            getLocationListener()
        }

        Handler().postDelayed(
                {
                    swapFragmentsForecast(CurrentRecyclerFragment())
                },
                1000 // value in milliseconds
        )
        Handler().postDelayed(
                {
                    swapFragmentsCurrent(CurrentFragment())
                },
                2500 // value in milliseconds
        )

        binding.button2.setOnClickListener {
            Handler().postDelayed(
                    {
                        swapFragmentsForecast(ForecastFragment())
                    },
                    500 // value in milliseconds
            )
        }
        binding.button.setOnClickListener {
            Handler().postDelayed(
                    {
                        swapFragmentsForecast(CurrentRecyclerFragment())
                    },
                    500 // value in milliseconds
            )
        }
    }

    private fun getLocationListener() {
        if (ActivityCompat.checkSelfPermission(
           this, Manifest.permission.ACCESS_FINE_LOCATION)
           != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
           this, Manifest.permission.ACCESS_COARSE_LOCATION)
           != PackageManager.PERMISSION_GRANTED) {
            return
        }
        fuseLocationProvider.lastLocation
        .addOnSuccessListener{
            if(it.latitude != null && it.longitude!=null) {
                lat = it.latitude
                lon = it.longitude
                WeatherRepository.locationObj.lat = it.latitude
                WeatherRepository.locationObj.lon = it.longitude
                WeatherRepository.locationObj.context = this
                WeatherRepository.callGetForecast(this,lat,lon,units,apikeyFore)
                WeatherRepository.callGetWeather(this,lat,lon,units,apiKey)
                WeatherRepository.callGetWeatherRecycler(this,lat,lon,units,apikeyFore)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions:
    Array<out String>, grantResults: IntArray) {
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

    private fun swapFragmentsForecast(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.forecast_container, fragment)
            .commit()
    }
    private fun swapFragmentsCurrent(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.current_container, fragment)
                .commit()
    }
}




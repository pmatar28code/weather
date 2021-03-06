package com.example.weather

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.weather.databinding.ActivityMainBinding
import com.example.weather.networking.WeatherClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity:AppCompatActivity() {
    private lateinit var fuseLocationProvider : FusedLocationProviderClient
    private val requestLocationCode = 100
    private val apiKey = "ec17ade9a281a44dcef2e94dd8e28fca"
    private var lon = 0
    private var lat = 0


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

        fuseLocationProvider = LocationServices.getFusedLocationProviderClient(this)


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (this, Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION
                    , Manifest.permission.ACCESS_COARSE_LOCATION), requestLocationCode)


        } else {

            getLocationListener()

        }







    }


    private fun getLocationListener()
        {
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
                        lat = it.latitude.toInt()
                        lon = it.longitude.toInt()

//lat,lon,apiKey

                        WeatherClient.weatherService.getWeather(lat,lon,apiKey).enqueue(object : Callback<WeatherServer>{
                            override fun onFailure(call: Call<WeatherServer>, t: Throwable) {
                                Toast.makeText(this@MainActivity,"onFailure $t",Toast.LENGTH_LONG).show()
                            }

                            override fun onResponse(call: Call<WeatherServer>, response: Response<WeatherServer>) {
                                if(response.isSuccessful){
                                    var textWeather = findViewById<TextView>(R.id.weather_text)
                                   // var testobj = response.body()?.main

                                    textWeather.text =response.body()?.main?.temp.toString()
                                    Toast.makeText(this@MainActivity,"onResponse",Toast.LENGTH_LONG).show()

                                }
                            }
                        })


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
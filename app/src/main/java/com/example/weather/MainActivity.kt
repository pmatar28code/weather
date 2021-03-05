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
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationListener;
import org.w3c.dom.Text

class MainActivity:AppCompatActivity() {
    private lateinit var fuseLocationProvider : FusedLocationProviderClient
    private val requestLocationCode = 100

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


    private fun getLocationListener() {
        var locationButton = findViewById<Button>(R.id.location_button).setOnClickListener {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return@setOnClickListener
        }
        fuseLocationProvider.lastLocation
                .addOnSuccessListener {
                    var textLocation = findViewById<TextView>(R.id.location_text)
                    textLocation.text = "Lat: ${it.latitude} - Lon: ${it.longitude}"

                }
    }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when{
            requestCode == requestLocationCode -> {
                if(permissions.contains(Manifest.permission.ACCESS_FINE_LOCATION) || permissions.contains(Manifest.permission.ACCESS_COARSE_LOCATION)){
                    getLocationListener()
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}
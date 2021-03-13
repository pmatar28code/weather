package com.example.weather

import android.os.Handler
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.databinding.ItemForecastBinding
import com.example.weather.repositories.WeatherRepository
import com.squareup.picasso.Picasso
import java.util.*

class ForecastAdapter(
        private val items:List<CurrentForecast.Daily>

): RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            :ForecastViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemForecastBinding.inflate(
                layoutInflater, parent, false)
        return ForecastViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(
            holder: ForecastViewHolder,position: Int) {
        val item = items[position]
        holder.onBind(items[position])
    }

    class ForecastViewHolder(
            private val binding: ItemForecastBinding
    ):RecyclerView.ViewHolder(binding.root){
        fun onBind(Forecast:CurrentForecast.Daily){
            binding.apply {
                tempText.text = Forecast.temp.max.toString()
                iconView
                var dateTime = WeatherRepository.getShortDate(Forecast.dt.toLong())
                dtText.text =dateTime
                Picasso.get().load(getCurrentImage(getImageCode())).into(iconView)
            }
        }
        fun getCurrentImage(iconCode:String):String {
            var iconUrl = "http://openweathermap.org/img/w/$iconCode.png";
            return iconUrl
        }
        fun getImageCode():String{
            val imageCode = WeatherRepository.forecastList[0].weather[0].icon
            return imageCode
        }
    }
}


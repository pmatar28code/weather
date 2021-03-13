package com.example.weather

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.databinding.ItemCurrentBinding
import com.example.weather.databinding.ItemForecastBinding
import com.example.weather.repositories.WeatherRepository
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.time.ExperimentalTime
import kotlin.time.days
import kotlin.time.hours

class CurrentAdapter(
    private val items:List<CurrentForecast.Hourly>


): RecyclerView.Adapter<CurrentAdapter.CurrentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            :CurrentViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemCurrentBinding.inflate(
                layoutInflater, parent, false)
        return CurrentViewHolder(binding)
    }

    override fun getItemCount() = items.size

   // @ExperimentalTime
    override fun onBindViewHolder(
            holder: CurrentViewHolder,position: Int) {
        val item = items[position]
        holder.onBind(items[position])

       var icon = item.weather[0].icon
       fun getCurrentImage(iconCode:String):String {
           var iconUrl = "http://openweathermap.org/img/w/$iconCode.png";
           return iconUrl
       }
       Picasso.get().load(getCurrentImage(icon)).into(holder.itemView.findViewById<ImageView>(R.id.current_recycler_image))
   }

    class CurrentViewHolder(
            private val binding: ItemCurrentBinding
    ):RecyclerView.ViewHolder(binding.root){
        //@ExperimentalTime
        fun onBind(current:CurrentForecast.Hourly){
            binding.apply {
                itemCurrentTempText.text = current.feelsLike.toString()
                var dateTime = WeatherRepository.getDateTimeFromEpocLongOfSeconds(current.dt.toLong())
                dayTimeText.text =dateTime
              //  Picasso.get().load(getCurrentImage(getImageCode())).into(currentRecyclerImage)
            }

        }
        //fun getCurrentImage(iconCode:String):String {
         //   var iconUrl = "http://openweathermap.org/img/w/$iconCode.png";
         //   return iconUrl
       // }
        //fun getImageCode():String{
          //  val imageCode = WeatherRepository.currentList[0].weather[0].icon
           // return imageCode
        //}
    }
}

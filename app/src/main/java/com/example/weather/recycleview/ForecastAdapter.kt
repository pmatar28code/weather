package com.example.weather.recycleview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.ForecastServer
import com.example.weather.databinding.ItemForecastBinding

class ForecastAdapter: androidx.recyclerview.widget.ListAdapter<ForecastServer.Daily, ForecastAdapter.ForecastViewHolder>(diffUtil) {
   companion object {
       private val diffUtil = object : DiffUtil.ItemCallback<ForecastServer.Daily>(){
           override fun areItemsTheSame(oldItem: ForecastServer.Daily, newItem: ForecastServer.Daily) = true

           override fun areContentsTheSame(oldItem: ForecastServer.Daily, newItem: ForecastServer.Daily): Boolean {
               return oldItem == newItem
           }

       }
   }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemForecastBinding.inflate(inflater)
        return ForecastViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        val item = getItem(position)
        holder.onBind(item)
    }

    class ForecastViewHolder(
            private val binding: ItemForecastBinding
    ):RecyclerView.ViewHolder(binding.root){
        fun onBind(Forecast:ForecastServer.Daily){
            binding.apply {
                tempText.text = Forecast.temp.toString()
               // descriptionText.text =Forecast.weather[0].description
            }

        }
    }
}
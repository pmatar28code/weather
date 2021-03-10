package com.example.weather

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.databinding.ItemForecastBinding

class TestAdapter(
        private val items:List<CurrentForecast.Daily>

): RecyclerView.Adapter<TestAdapter.ForecastViewHolder>() {

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
                tempText.text = Forecast.temp.toString()
                // descriptionText.text =Forecast.weather[0].description
            }

        }
    }
}

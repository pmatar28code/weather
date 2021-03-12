package com.example.weather

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.databinding.CurrentFragmentBinding.bind
import com.example.weather.databinding.ForecastFragmentBinding
import com.example.weather.repositories.WeatherRepository

class CurrentRecyclerFragment : Fragment(R.layout.current_recycler_fragment) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //val binding = CurrentRecyclerFragment.bind(view)


        view.findViewById<RecyclerView>(R.id.current_item).apply{
            adapter = CurrentAdapter(WeatherRepository.currentList)
            //WeatherRepository.callGetForecast(WeatherRepository.locationObj.context!!,WeatherRepository.locationObj.lat,WeatherRepository.locationObj.lon,"metric","1fb88785b228435d667386a70a2eb3b4")
            Handler().postDelayed(
                    {
                        adapter?.notifyDataSetChanged()
                        // This method will be executed once the timer is over
                    },
                    5000 // value in milliseconds
            )
            // adapter?.notifyDataSetChanged()
            //Toast.makeText(context, "recycler loaded get Weather ", Toast.LENGTH_LONG).show()
            layoutManager = LinearLayoutManager(context,
                    LinearLayoutManager.VERTICAL,
                    false)
        }
    }

}
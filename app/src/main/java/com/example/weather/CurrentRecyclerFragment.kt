package com.example.weather

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.repositories.WeatherRepository

class CurrentRecyclerFragment : Fragment(R.layout.current_recycler_fragment) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        view.findViewById<RecyclerView>(R.id.current_item).apply{
            adapter = CurrentAdapter(WeatherRepository.currentList)
            Handler().postDelayed(
                    {
                        adapter?.notifyDataSetChanged()
                    },
                    5000 // value in milliseconds
            )
            layoutManager = LinearLayoutManager(context,
                    LinearLayoutManager.VERTICAL,
                    false)
        }
    }

}
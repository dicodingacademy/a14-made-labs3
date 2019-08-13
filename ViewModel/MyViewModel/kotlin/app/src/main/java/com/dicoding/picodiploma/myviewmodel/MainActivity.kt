package com.dicoding.picodiploma.myviewmodel

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: WeatherAdapter
    private lateinit var mainViewModel: MainViewModel

    private val getWeather = Observer<ArrayList<WeatherItems>> { weatherItems ->
        if (weatherItems != null) {
            adapter.setData(weatherItems)
            showLoading(false)
        }
    }

    private var myListener: View.OnClickListener = View.OnClickListener {
        val city = editCity.text.toString()

        if (city.isEmpty()) return@OnClickListener

        mainViewModel.setWeather(city)
        showLoading(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        mainViewModel.weathers.observe(this, getWeather)

        adapter = WeatherAdapter()
        adapter.notifyDataSetChanged()

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        findViewById<View>(R.id.btnCity).setOnClickListener(myListener)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }

}

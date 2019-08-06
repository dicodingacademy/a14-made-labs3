package com.dicoding.picodiploma.myviewmodel

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar

import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: WeatherAdapter
    private lateinit var edtCity: EditText
    private lateinit var progressBar: ProgressBar
    private lateinit var mainViewModel: MainViewModel

    private val getWeather = Observer<ArrayList<WeatherItems>> { weatherItems ->
        if (weatherItems != null) {
            adapter.setData(weatherItems)
            showLoading(false)
        }
    }

    private var myListener: View.OnClickListener = View.OnClickListener {
        val city = edtCity.text.toString()

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

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        edtCity = findViewById(R.id.editCity)
        progressBar = findViewById(R.id.progressBar)

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

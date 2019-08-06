package com.dicoding.picodiploma.myviewmodel

import org.json.JSONObject

import java.text.DecimalFormat

/**
 * Created by Emeth on 10/31/2016.
 */

class WeatherItems internal constructor(`object`: JSONObject) {

    private var id: Int = 0
    var name: String? = null
    private var currentWeather: String? = null
    var description: String? = null
    var temperature: String? = null

    init {

        try {
            val id = `object`.getInt("id")
            val name = `object`.getString("name")
            val currentWeather = `object`.getJSONArray("weather").getJSONObject(0).getString("main")
            val description = `object`.getJSONArray("weather").getJSONObject(0).getString("description")
            val tempInKelvin = `object`.getJSONObject("main").getDouble("temp")

            val tempInCelsius = tempInKelvin - 273
            val temperature = DecimalFormat("##.##").format(tempInCelsius)
            this.id = id
            this.name = name
            this.currentWeather = currentWeather
            this.description = description
            this.temperature = temperature

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
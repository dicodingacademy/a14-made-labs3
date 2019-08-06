package com.dicoding.picodiploma.myviewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Log

import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler

import org.json.JSONArray
import org.json.JSONObject

import java.util.ArrayList

import cz.msebera.android.httpclient.Header

/**
 * Created by Gilang on 04/31/2019.
 */

class MainViewModel : ViewModel() {

    private val listWeathers = MutableLiveData<ArrayList<WeatherItems>>()

    internal val weathers: LiveData<ArrayList<WeatherItems>>
        get() = listWeathers

    companion object {
        private const val API_KEY = "MASUKKAN_API_KEY_DISINI"
//        private const val API_KEY = "cb744b309dbc7c577fe57bde64e8cf3a"
    }

    internal fun setWeather(cities: String) {
        val client = AsyncHttpClient()
        val listItems = ArrayList<WeatherItems>()
        val url = "https://api.openweathermap.org/data/2.5/group?id=$cities&units=metric&appid=$API_KEY"

        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                try {
                    val result = String(responseBody)
                    val responseObject = JSONObject(result)
                    val list = responseObject.getJSONArray("list")

                    for (i in 0 until list.length()) {
                        val weather = list.getJSONObject(i)
                        val weatherItems = WeatherItems(weather)
                        listItems.add(weatherItems)
                    }
                    listWeathers.postValue(listItems)
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }

            }

            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                Log.d("onFailure", error.message.toString())
            }
        })
    }
}

package com.dicoding.picodiploma.myquote

import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast

import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler

import org.json.JSONArray

import java.util.ArrayList

import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.activity_list_quotes.*

class ListQuotesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_quotes)

        supportActionBar?.title = "List of Quotes"

        getListQuotes()
    }

    private fun getListQuotes() {
        progressBar.visibility = View.VISIBLE
        val client = AsyncHttpClient()
        val url = "https://programming-quotes-api.herokuapp.com/quotes/page/1"
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                //jika koneksi berhasil
                progressBar.visibility = View.INVISIBLE

                val listQuote = ArrayList<String>()

                val result = String(responseBody)
                Log.d(TAG, result)
                try {
                    val jsonArray = JSONArray(result)

                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val quote = jsonObject.getString("en")
                        val author = jsonObject.getString("author")
                        listQuote.add("\n$quote\n — $author\n")
                    }

                    val adapter = ArrayAdapter(this@ListQuotesActivity, android.R.layout.simple_list_item_1, listQuote)
                    listQuotes.adapter = adapter
                } catch (e: Exception) {
                    Toast.makeText(this@ListQuotesActivity, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }

            }

            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                //jika koneksi gagal
                progressBar.visibility = View.INVISIBLE
                Toast.makeText(this@ListQuotesActivity, error.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    companion object {

        private val TAG = ListQuotesActivity::class.java.getSimpleName()
    }
}

package com.dicoding.picodiploma.myquote

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import android.util.Log
import android.view.View
import android.widget.Toast

import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler

import org.json.JSONObject

import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        private val TAG = MainActivity::class.java.getSimpleName()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getRandomQuote()

        btnAllQuotes.setOnClickListener {
            startActivity(Intent(this@MainActivity, ListQuotesActivity::class.java))
        }
    }

    private fun getRandomQuote() {
        progressBar.visibility = View.VISIBLE
        val client = AsyncHttpClient()
        val url = "https://programming-quotes-api.herokuapp.com/quotes/random"
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                //jika koneksi berhasil
                progressBar.visibility = View.INVISIBLE

                val result = String(responseBody)
                Log.d(TAG, result)
                try {
                    val responseObject = JSONObject(result)

                    val quote = responseObject.getString("en")
                    val author = responseObject.getString("author")

                    tvQuote.text = quote
                    tvAuthor.text = author

                } catch (e: Exception) {
                    Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }

            }

            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                //jika koneksi gagal
                progressBar.visibility = View.INVISIBLE
                Toast.makeText(this@MainActivity, error.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}

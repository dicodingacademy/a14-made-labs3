package com.dicoding.picodiploma.myasynctask

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.doAsync

class MainActivity : AppCompatActivity() {
    private lateinit var tvStatus: TextView
    private lateinit var tvDesc: TextView
    private var output: String? = null

    companion object {
        private const val INPUT_STRING = "Halo Ini Demo AsyncTask!!"
        private const val LOG_ASYNC = "DemoAsync"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvStatus = findViewById(R.id.tv_status)
        tvDesc = findViewById(R.id.tv_desc)

        tvStatus.setText(R.string.status_pre)
        tvDesc.text = INPUT_STRING

        doAsync {
            Log.d(LOG_ASYNC, "status : doInBackground")
            try {
                val input = INPUT_STRING

                // Input stringnya ditambahkan dengan string ' Selamat Belajar!!"
                output = "$input Selamat Belajar!!"

                /*
                Sleep thread digunakan untuk simulasi bahwa ada proses yang sedang berjalan selama 5 detik
                5000 miliseconds = 5 detik
                */
                Thread.sleep(5000)

            } catch (e: Exception) {
                Log.d(LOG_ASYNC, e.message.toString())
            }
            runOnUiThread {
                //ketika proses background sudah selesai dan ingin merubah UI
                tvStatus.setText(R.string.status_post)
                tvDesc.text = output
            }
        }
    }
}

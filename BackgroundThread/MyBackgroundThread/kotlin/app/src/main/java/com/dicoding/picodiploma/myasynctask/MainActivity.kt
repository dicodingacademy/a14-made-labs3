package com.dicoding.picodiploma.myasynctask

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private var output: String? = null

    companion object {
        private const val INPUT_STRING = "Halo Ini Demo AsyncTask!!"
        private const val LOG_ASYNC = "DemoAsync"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tv_status.setText(R.string.status_pre)
        tv_desc.text = INPUT_STRING

        GlobalScope.launch {
            doInBackground()
        }
    }

    private suspend fun doInBackground(): String {
        //background thread
        Log.d(LOG_ASYNC, "status : doInBackground")
        try {
            val input = INPUT_STRING

            // Input stringnya ditambahkan dengan string ' Selamat Belajar!!"
            output = "$input Selamat Belajar!!"

            /*
                    Sleep thread digunakan untuk simulasi bahwa ada proses yang sedang berjalan selama 5 detik
                    5000 miliseconds = 5 detik
                    */
            delay(5000)

            //pindah ke ui thread untuk update UI
            runOnUiThread {
                tv_status.setText(R.string.status_post)
                tv_desc.text = output
            }

        } catch (e: Exception) {
            Log.d(LOG_ASYNC, e.message.toString())
        }
        return output.toString()
    }
}

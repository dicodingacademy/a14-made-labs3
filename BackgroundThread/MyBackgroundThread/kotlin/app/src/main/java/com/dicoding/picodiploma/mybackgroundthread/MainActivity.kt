package com.dicoding.picodiploma.mybackgroundthread

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    companion object {
        private const val INPUT_STRING = "Halo Ini Demo AsyncTask!!"
        private const val LOG_ASYNC = "DemoAsync"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tv_status.setText(R.string.status_pre)
        tv_desc.text = INPUT_STRING

        GlobalScope.launch(Dispatchers.Default) {
            //background thread
            val input = INPUT_STRING

            var output: String? = null

            Log.d(LOG_ASYNC, "status : doInBackground")
            try {
                // Input stringnya ditambahkan dengan string ' Selamat Belajar!!"
                output = "$input Selamat Belajar!!"

                /*
                Sleep thread digunakan untuk simulasi bahwa ada proses yang sedang berjalan selama 2 detik
                2000 miliseconds = 2 detik
                */
                delay(2000)

                //pindah ke Main thread untuk update UI
                withContext(Dispatchers.Main) {
                    tv_status.setText(R.string.status_post)
                    tv_desc.text = output
                }

            } catch (e: Exception) {
                Log.d(LOG_ASYNC, e.message.toString())
            }
        }
    }
}

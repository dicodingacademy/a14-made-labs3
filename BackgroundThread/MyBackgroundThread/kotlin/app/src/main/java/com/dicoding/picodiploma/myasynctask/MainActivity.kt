package com.dicoding.picodiploma.myasynctask

import android.os.AsyncTask
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.widget.TextView

import java.lang.ref.WeakReference

class MainActivity : AppCompatActivity(), MyAsyncCallback {
    private lateinit var tvStatus: TextView
    private lateinit var tvDesc: TextView

    companion object {
        private const val INPUT_STRING = "Halo Ini Demo AsyncTask!!"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvStatus = findViewById(R.id.tv_status)
        tvDesc = findViewById(R.id.tv_desc)

        val demoAsync = DemoAsync(this)

        // Execute asynctask dengan parameter string 'Halo Ini Demo AsyncTask'
        demoAsync.execute(INPUT_STRING)
    }

    override fun onPreExecute() {
        tvStatus.setText(R.string.status_pre)
        tvDesc.text = INPUT_STRING
    }

    override fun onPostExecute(text: String?) {
        tvStatus.setText(R.string.status_post)
        tvDesc.text = text
    }

    /**
     * 3 parameter generic <String></String>, Void, String>
     * 1. Params, parameter input yang bisa dikirimkan
     * 2. Progress, digunakan untuk publish informasi sudah sampai mana proses background berjalan
     * 3. Result, object yang dikirimkan ke onPostExecute / hasil dari proses doInBackground
     */
    private class DemoAsync internal constructor(myListener: MyAsyncCallback) : AsyncTask<String, Void, String>() {
        companion object {
            private const val LOG_ASYNC = "DemoAsync"
        }

        // Penggunaan weakreference disarankan untuk menghindari memory leaks
        private var myListener: WeakReference<MyAsyncCallback> = WeakReference(myListener)

        /*
        onPreExecute digunakan untuk persiapan asynctask
        berjalan di Main Thread, bisa akses ke view karena masih di dalam Main Thread
         */

        override fun onPreExecute() {
            super.onPreExecute()
            Log.d(LOG_ASYNC, "status : onPreExecute")

            val myListener = this.myListener.get()
            myListener?.onPreExecute()
        }

        /*
        doInBackground digunakan untuk menjalankan proses secara async
        berjalan di background thread, tidak bisa akses ke view karena sudah beda thread
         */
        override fun doInBackground(vararg params: String): String? {
            Log.d(LOG_ASYNC, "status : doInBackground")

            var output: String? = null

            try {

                /*
                params[0] adalah 'Halo Ini Demo AsyncTask'
                */

                val input = params[0]

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

            return output
        }

        /*
        onPostExecute dijalankan ketika proses doInBackground telah selesai
        berjalan di Main Thread
         */
        override fun onPostExecute(result: String) {
            super.onPostExecute(result)
            Log.d(LOG_ASYNC, "status : onPostExecute")

            val myListener = this.myListener.get()
            myListener?.onPostExecute(result)
        }
    }
}

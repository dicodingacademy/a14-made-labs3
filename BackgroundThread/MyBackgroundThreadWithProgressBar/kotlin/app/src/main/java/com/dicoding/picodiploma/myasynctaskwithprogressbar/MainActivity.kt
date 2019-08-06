package com.dicoding.picodiploma.myasynctaskwithprogressbar

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {

    private lateinit var buttonStart: Button
    private lateinit var progressBar: ProgressBar

    companion object {
        private const val DEMO_ASYNC = "DemoAsyncWithProgress"
        // Maksimum nilai dari progress nya 10000
        private const val MAX_PROGRESS = 10000.0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar = findViewById(R.id.progress_bar)
        buttonStart = findViewById(R.id.btn_start)

        buttonStart.setOnClickListener {
            progressBar.progress = 0
            toast("Async masih berjalan, silakan tunggu sampai selesai..")
            doAsync {
                //background thread
                doInBackground()
                runOnUiThread {
                    //ui thread
                    toast("Finish")
                }
            }
        }
    }

    private fun doInBackground() {
        val waitingTime: Long = 20
        var startingTime: Long = 0
        for (x in 0..4) {
            try {
                Thread.sleep(waitingTime)

                // Update progress dengan memanggil publishProgress
                startingTime += waitingTime
                progressBar.progress = startingTime.toInt()

//                publishProgress(startingTime)
            } catch (e: Exception) {
                Log.d(DEMO_ASYNC, e.message.toString())
            }
        }
    }

    private fun publishProgress(value: Long) {
        /*
            Karena maksimal nilai pada view ProgressBar adalah 100,
            maka kita harus mengkonversi value ke dalam skala 100
            */
        val progress = 100 * (value / MAX_PROGRESS)
        progressBar.progress = progress.toInt()
    }
}

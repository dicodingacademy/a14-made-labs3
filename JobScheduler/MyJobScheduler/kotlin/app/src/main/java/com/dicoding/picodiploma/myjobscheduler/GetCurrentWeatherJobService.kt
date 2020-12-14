package com.dicoding.picodiploma.myjobscheduler

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Context
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.text.DecimalFormat

/**
 * Created by sidiqpermana on 9/29/16.
 */

class GetCurrentWeatherJobService : JobService() {

    companion object {
        private val TAG = GetCurrentWeatherJobService::class.java.simpleName
        //internal const val CITY = "ISIKAN DENGAN NAMA KOTA KAMU"
        internal const val CITY = "Jakarta"
        internal const val APP_ID = "93a3696714297ee5a9f65486aa8cb824"
    }

    /**
     * onStartJob berjalan di dalam mainthread, return true jika ada proses yang membuat thread baru
     */
    override fun onStartJob(params: JobParameters): Boolean {
        Log.d(TAG, "onStartJob()")
        getCurrentWeather(params)
        return true
    }

    /**
     * onStopJob akan dipanggil ketika proses belum selesai dikarenakan constraint requirements tidak terpenuhi
     * return true untuk re-scheduler
     */
    override fun onStopJob(params: JobParameters): Boolean {
        Log.d(TAG, "onStopJob()")
        return true
    }

    /**
     * Request data ke API weather, jobFinished dipanggil secara manual ketika proses sudah selesai
     *
     * @param job parameters
     */
    private fun getCurrentWeather(job: JobParameters) {
        Log.d(TAG, "getCurrentWeather: Mulai.....")
        val client = AsyncHttpClient()
        val url = "https://api.openweathermap.org/data/2.5/weather?q=$CITY&appid=$APP_ID"
        Log.d(TAG, "getCurrentWeather: $url")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                val result = String(responseBody)
                Log.d(TAG, result)
                try {
                    val responseObject = JSONObject(result)

                    /*
                    Perlu diperhatikan bahwa angka 0 pada getJSONObject menunjukkan index ke-0
                    Jika data yang ingin kita ambil ada lebih dari satu maka gunakanlah looping
                     */
                    val currentWeather = responseObject.getJSONArray("weather").getJSONObject(0).getString("main")
                    val description = responseObject.getJSONArray("weather").getJSONObject(0).getString("description")
                    val tempInKelvin = responseObject.getJSONObject("main").getDouble("temp")

                    val tempInCelsius = tempInKelvin - 273
                    val temperature = DecimalFormat("##.##").format(tempInCelsius)

                    val title = "Current Weather"
                    val message = "$currentWeather, $description with $temperature celsius"
                    val notifId = 100

                    showNotification(applicationContext, title, message, notifId)

                    Log.d(TAG, "onSuccess: Selesai.....")
                    // ketika proses selesai, maka perlu dipanggil jobFinished dengan parameter false;
                    jobFinished(job, false)

                } catch (e: Exception) {
                    Log.d(TAG, "onSuccess: Gagal.....")
                    // ketika terjadi error, maka jobFinished diset dengan parameter true. Yang artinya job perlu di reschedule
                    jobFinished(job, true)
                    e.printStackTrace()
                }

            }

            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                Log.d(TAG, "onFailure: Gagal.....")
                // ketika proses gagal, maka jobFinished diset dengan parameter true. Yang artinya job perlu di reschedule
                jobFinished(job, true)
            }
        })
    }


    /**
     * Menampilkan datanya ke dalam notification
     *
     * @param context context dari notification
     * @param title   judul notifikasi
     * @param message isi dari notifikasi
     * @param notifId id notifikasi
     */
    private fun showNotification(context: Context, title: String, message: String, notifId: Int) {
        val CHANNEL_ID = "Channel_1"
        val CHANNEL_NAME = "Job scheduler channel"

        val notificationManagerCompat = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle(title)
                .setSmallIcon(R.drawable.ic_replay_30_black_24dp)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.black))
                .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
                .setSound(alarmSound)

        /*
        Untuk android Oreo ke atas perlu menambahkan notification channel
        Materi ini akan dibahas lebih lanjut di modul extended
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            /* Create or update. */
            val channel = NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT)

            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)

            builder.setChannelId(CHANNEL_ID)

            notificationManagerCompat.createNotificationChannel(channel)
        }

        val notification = builder.build()

        notificationManagerCompat.notify(notifId, notification)

    }
}
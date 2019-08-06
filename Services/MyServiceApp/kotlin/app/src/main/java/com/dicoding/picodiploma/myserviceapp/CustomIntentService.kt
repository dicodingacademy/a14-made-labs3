package com.dicoding.picodiploma.myserviceapp

import android.app.IntentService
import android.content.Intent
import android.util.Log

class CustomIntentService : IntentService("IntentService") {

    companion object {
        internal const val EXTRA_DURATION = "extra_duration"
        private val TAG = IntentService::class.java.simpleName
    }

    override fun onHandleIntent(intent: Intent?) {
        Log.d(TAG, "onHandleIntent: Mulai.....")
        val duration = intent?.getIntExtra(EXTRA_DURATION, 0)
        try {
            duration?.toLong()?.let { Thread.sleep(it) }
            Log.d(TAG, "onHandleIntent: Selesai.....")
        } catch (e: InterruptedException) {
            e.printStackTrace()
            Thread.currentThread().interrupt()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy()")
    }
}

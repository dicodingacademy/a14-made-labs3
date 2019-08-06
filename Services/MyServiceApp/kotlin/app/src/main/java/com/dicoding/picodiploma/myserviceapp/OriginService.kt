package com.dicoding.picodiploma.myserviceapp

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.onComplete

class OriginService : Service() {

    companion object {
        internal val TAG = OriginService::class.java.simpleName
    }

    override fun onBind(intent: Intent): IBinder? {
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand: ")

        Log.d(TAG, "onPreExecute: ")
        Log.d(TAG, "preAsync: Mulai.....")
        doAsync {
            Log.d(TAG, "doInBackground: ")
            try {
                Thread.sleep(3000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            onComplete {
                Log.d(TAG, "onPostExecute: ")
                Log.d(TAG, "postAsync: Selesai.....")
                stopSelf()
            }
        }
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: ")
    }
}
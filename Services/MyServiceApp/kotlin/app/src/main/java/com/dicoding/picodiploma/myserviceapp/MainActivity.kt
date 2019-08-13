package com.dicoding.picodiploma.myserviceapp

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.picodiploma.myserviceapp.BoundService.MyBinder
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var mServiceBound = false
    private lateinit var mBoundService: BoundService

    /*
    Service Connection adalah interface yang digunakan untuk menghubungkan antara boundservice dengan activity
     */
    private val mServiceConnection = object : ServiceConnection {

        override fun onServiceDisconnected(name: ComponentName) {
            mServiceBound = false
        }

        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            val myBinder = service as MyBinder
            mBoundService = myBinder.service
            mServiceBound = true
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_start_service.setOnClickListener(this)

        btn_start_intent_service.setOnClickListener(this)

        btn_start_bound_service.setOnClickListener(this)

        btn_stop_bound_service.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_start_service -> {
                val mStartServiceIntent = Intent(this@MainActivity, OriginService::class.java)
                startService(mStartServiceIntent)
            }

            R.id.btn_start_intent_service -> {
                val mStartIntentService = Intent(this@MainActivity, CustomIntentService::class.java)
                mStartIntentService.putExtra(CustomIntentService.EXTRA_DURATION, 5000)
                startService(mStartIntentService)
            }

            R.id.btn_start_bound_service -> {
                val mBoundServiceIntent = Intent(this@MainActivity, BoundService::class.java)
                bindService(mBoundServiceIntent, mServiceConnection, Context.BIND_AUTO_CREATE)
            }

            R.id.btn_stop_bound_service -> unbindService(mServiceConnection)
        }

    }

    override fun onDestroy() {
        super.onDestroy()

        /*
        Pemanggilan unbind di dalam ondestroy ditujukan untuk mencegah memory leaks dari bound services
         */
        if (mServiceBound) {
            unbindService(mServiceConnection)
        }
    }
}
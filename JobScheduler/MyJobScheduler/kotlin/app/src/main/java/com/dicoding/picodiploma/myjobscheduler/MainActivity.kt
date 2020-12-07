package com.dicoding.picodiploma.myjobscheduler

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.picodiploma.myjobscheduler.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {
        private const val JOB_ID = 10
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStart.setOnClickListener { startJob() }
        binding.btnCancel.setOnClickListener { cancelJob() }
    }

    private fun startJob() {

        /*
        Cek running job terlebih dahulu
         */
        if (isJobRunning(this)) {
            Toast.makeText(this, "Job Service is already scheduled", Toast.LENGTH_SHORT).show()
            return
        }


        val mServiceComponent = ComponentName(this, GetCurrentWeatherJobService::class.java)

        val builder = JobInfo.Builder(JOB_ID, mServiceComponent)

        /*
        Kondisi network,
        NETWORK_TYPE_ANY, berarti tidak ada ketentuan tertentu
        NETWORK_TYPE_UNMETERED, adalah network yang tidak dibatasi misalnya wifi
        */
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)

        /*
        Kondisi device, secara default sudah pada false
        false, berarti device tidak perlu idle ketika job ke trigger
        true, berarti device perlu dalam kondisi idle ketika job ke trigger
        */
        builder.setRequiresDeviceIdle(false)

        /*
        Kondisi charging
        false, berarti device tidak perlu di charge
        true, berarti device perlu dicharge
        */
        builder.setRequiresCharging(false)

        /*
        Periode interval sampai ke trigger
        Dalam milisecond, 1000ms = 1detik
        */
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            builder.setPeriodic(900000) //15 menit
        } else {
            builder.setPeriodic(180000) //3 menit
        }

        val scheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        scheduler.schedule(builder.build())
        Toast.makeText(this, "Job Service started", Toast.LENGTH_SHORT).show()
    }

    private fun cancelJob() {
        val scheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        scheduler.cancel(JOB_ID)
        Toast.makeText(this, "Job Service canceled", Toast.LENGTH_SHORT).show()
    }

    /*
    Gunakanlah method ini untuk melakukan pengecekan "apakah job dengan id 10 sudah berjalan ?"
     */
    private fun isJobRunning(context: Context): Boolean {
        var isScheduled = false

        val scheduler = context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler

        for (jobInfo in scheduler.allPendingJobs) {
            if (jobInfo.id == JOB_ID) {
                isScheduled = true
                break
            }
        }

        return isScheduled
    }
}

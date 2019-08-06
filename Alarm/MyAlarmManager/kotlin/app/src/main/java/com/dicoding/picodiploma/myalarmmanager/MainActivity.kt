package com.dicoding.picodiploma.myalarmmanager

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.picodiploma.myalarmmanager.utils.DatePickerFragment
import com.dicoding.picodiploma.myalarmmanager.utils.TimePickerFragment
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener, DatePickerFragment.DialogDateListener, TimePickerFragment.DialogTimeListener {

    private lateinit var tvOnceDate: TextView
    private lateinit var tvOnceTime: TextView
    private lateinit var edtOnceMessage: EditText
    private lateinit var btnOnceDate: ImageButton
    private lateinit var btnOnceTime: ImageButton
    private lateinit var btnSetOnce: Button

    private lateinit var tvRepeatingTime: TextView
    private lateinit var edtRepeatingMessage: EditText
    private lateinit var btnRepeatingTime: ImageButton
    private lateinit var btnSetRepeating: Button
    private lateinit var btnCancelRepeating: Button

    private lateinit var alarmReceiver: AlarmReceiver

    companion object {
        private const val DATE_PICKER_TAG = "DatePicker"
        private const val TIME_PICKER_ONCE_TAG = "TimePickerOnce"
        private const val TIME_PICKER_REPEAT_TAG = "TimePickerRepeat"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inisiasi view untuk one time alarm
        tvOnceDate = findViewById(R.id.tv_once_date)
        btnOnceDate = findViewById(R.id.btn_once_date)
        tvOnceTime = findViewById(R.id.tv_once_time)
        btnOnceTime = findViewById(R.id.btn_once_time)
        edtOnceMessage = findViewById(R.id.edt_once_message)
        btnSetOnce = findViewById(R.id.btn_set_once_alarm)

        // Listener one time alarm
        btnOnceDate.setOnClickListener(this)
        btnOnceTime.setOnClickListener(this)
        btnSetOnce.setOnClickListener(this)

        // Inisiasi view untuk repeating alarm
        tvRepeatingTime = findViewById(R.id.tv_repeating_time)
        btnRepeatingTime = findViewById(R.id.btn_repeating_time)
        edtRepeatingMessage = findViewById(R.id.edt_repeating_message)
        btnSetRepeating = findViewById(R.id.btn_set_repeating_alarm)
        btnCancelRepeating = findViewById(R.id.btn_cancel_repeating_alarm)

        // Listener repeating alarm
        btnRepeatingTime.setOnClickListener(this)
        btnSetRepeating.setOnClickListener(this)
        btnCancelRepeating.setOnClickListener(this)

        alarmReceiver = AlarmReceiver()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_once_date -> {
                val datePickerFragment = DatePickerFragment()
                datePickerFragment.show(supportFragmentManager, DATE_PICKER_TAG)
            }
            R.id.btn_once_time -> {
                val timePickerFragmentOne = TimePickerFragment()
                timePickerFragmentOne.show(supportFragmentManager, TIME_PICKER_ONCE_TAG)
            }
            R.id.btn_set_once_alarm -> {
                val onceDate = tvOnceDate.text.toString()
                val onceTime = tvOnceTime.text.toString()
                val onceMessage = edtOnceMessage.text.toString()

                alarmReceiver.setOneTimeAlarm(this, AlarmReceiver.TYPE_ONE_TIME,
                        onceDate,
                        onceTime,
                        onceMessage)
            }
            R.id.btn_repeating_time -> {
                val timePickerFragmentRepeat = TimePickerFragment()
                timePickerFragmentRepeat.show(supportFragmentManager, TIME_PICKER_REPEAT_TAG)
            }
            R.id.btn_set_repeating_alarm -> {
                val repeatTime = tvRepeatingTime.text.toString()
                val repeatMessage = edtRepeatingMessage.text.toString()
                alarmReceiver.setRepeatingAlarm(this, AlarmReceiver.TYPE_REPEATING,
                        repeatTime, repeatMessage)
            }
            R.id.btn_cancel_repeating_alarm -> alarmReceiver.cancelAlarm(this, AlarmReceiver.TYPE_REPEATING)
        }
    }

    override fun onDialogDateSet(tag: String?, year: Int, month: Int, dayOfMonth: Int) {

        // Siapkan date formatter-nya terlebih dahulu
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        // Set text dari textview once
        tvOnceDate.text = dateFormat.format(calendar.time)
    }

    override fun onDialogTimeSet(tag: String?, hourOfDay: Int, minute: Int) {

        // Siapkan time formatter-nya terlebih dahulu
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minute)

        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        // Set text dari textview berdasarkan tag
        when (tag) {
            TIME_PICKER_ONCE_TAG -> tvOnceTime.text = dateFormat.format(calendar.time)
            TIME_PICKER_REPEAT_TAG -> tvRepeatingTime.text = dateFormat.format(calendar.time)
            else -> {
            }
        }
    }
}

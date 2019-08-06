package com.dicoding.picodiploma.mybroadcastreceiver

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.TextView

class SmsReceiverActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var tvSmsFrom: TextView
    private lateinit var tvSmsMessage: TextView
    private lateinit var btnClose: Button

    companion object {
        const val EXTRA_SMS_NO = "extra_sms_no"
        const val EXTRA_SMS_MESSAGE = "extra_sms_message"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sms_receiver)

        title = "Incoming Message"

        tvSmsFrom = findViewById(R.id.tv_no)
        tvSmsMessage = findViewById(R.id.tv_message)
        btnClose = findViewById(R.id.btn_close)
        btnClose.setOnClickListener(this)

        val senderNo = intent.getStringExtra(EXTRA_SMS_NO)
        val senderMessage = intent.getStringExtra(EXTRA_SMS_MESSAGE)

        tvSmsFrom.text = getString(R.string.from, senderNo)
        tvSmsMessage.text = senderMessage
    }

    override fun onClick(v: View) {
        if (v.id == R.id.btn_close) {
            finish()
        }
    }
}

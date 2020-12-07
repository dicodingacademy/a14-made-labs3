package com.dicoding.picodiploma.mybroadcastreceiver;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.dicoding.picodiploma.mybroadcastreceiver.databinding.ActivitySmsReceiverBinding;

public class SmsReceiverActivity extends AppCompatActivity {

    public static final String EXTRA_SMS_NO = "extra_sms_no";
    public static final String EXTRA_SMS_MESSAGE = "extra_sms_message";

    private ActivitySmsReceiverBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySmsReceiverBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setTitle(getString(R.string.incoming_message));

        binding.btnClose.setOnClickListener(v -> finish());

        String senderNo = getIntent().getStringExtra(EXTRA_SMS_NO);
        String senderMessage = getIntent().getStringExtra(EXTRA_SMS_MESSAGE);

        binding.tvFrom.setText(String.format("from : %s", senderNo));
        binding.tvMessage.setText(senderMessage);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}

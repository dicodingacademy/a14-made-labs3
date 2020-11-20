package com.dicoding.picodiploma.mybackgroundthread;

import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private TextView tvStatus;
    private TextView tvDesc;

    private final static String INPUT_STRING = "Halo Ini Demo AsyncTask!!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvStatus = findViewById(R.id.tv_status);
        tvDesc = findViewById(R.id.tv_desc);

        new Thread(() -> {
            tvStatus.setText(R.string.status_pre);
            tvDesc.setText(INPUT_STRING);

            String output = INPUT_STRING + " Selamat Belajar!!";
            Log.d("LOG_ASYNC", "status : doInBackground");

            try {
                Thread.sleep(2000);
                runOnUiThread(() -> {
                    tvStatus.setText(R.string.status_post);
                    tvDesc.setText(output);
                });

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}

package com.dicoding.picodiploma.myidleresource;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EspressoIdlingResource.increment();

                delay();
            }
        });
    }

    private void delay() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ((TextView) findViewById(R.id.text_view)).setText(getString(R.string.finish));

                if (!EspressoIdlingResource.getEspressoIdlingResourcey().isIdleNow()) {
                    //Memberitahukan bahwa tugas sudah selesai dijalankan
                    EspressoIdlingResource.decrement();
                }
            }
        }, 2000);
    }
}

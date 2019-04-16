package com.dicoding.picodiploma.myidleresource;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.text_view);

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
                textView.setText(getString(R.string.loading));

                if (!EspressoIdlingResource.getEspressoIdlingResourcey().isIdleNow()) {
                    //Memberitahukan bahwa tugas sudah selesai dijalankan
                    EspressoIdlingResource.decrement();
                }

                EspressoIdlingResource.increment();

                loadData();
            }
        }, 2000);
    }

    private void loadData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                textView.setText(getString(R.string.finish));

                if (!EspressoIdlingResource.getEspressoIdlingResourcey().isIdleNow()) {
                    //Memberitahukan bahwa tugas sudah selesai dijalankan
                    EspressoIdlingResource.decrement();
                }
            }
        }, 3000);
    }
}

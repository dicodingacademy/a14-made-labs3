package com.dicoding.picodiploma.myviewmodel;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
    private WeatherAdapter adapter;
    private EditText edtCity;
    private ProgressBar progressBar;
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtCity = findViewById(R.id.editCity);
        progressBar = findViewById(R.id.progressBar);
        Button btnCity = findViewById(R.id.btnCity);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new WeatherAdapter();
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

        mainViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MainViewModel.class);

        btnCity.setOnClickListener(view -> {
            String city = edtCity.getText().toString();

            if (TextUtils.isEmpty(city)) return;

            showLoading(true);
            mainViewModel.setWeather(city);
        });

        mainViewModel.getWeathers().observe(this, weatherItems -> {
            if (weatherItems != null) {
                adapter.setData(weatherItems);
                showLoading(false);
            }
        });
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}

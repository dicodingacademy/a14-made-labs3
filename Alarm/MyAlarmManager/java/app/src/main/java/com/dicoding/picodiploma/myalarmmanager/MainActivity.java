package com.dicoding.picodiploma.myalarmmanager;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.dicoding.picodiploma.myalarmmanager.databinding.ActivityMainBinding;
import com.dicoding.picodiploma.myalarmmanager.utils.DatePickerFragment;
import com.dicoding.picodiploma.myalarmmanager.utils.TimePickerFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, DatePickerFragment.DialogDateListener, TimePickerFragment.DialogTimeListener {

    private ActivityMainBinding binding;
    private AlarmReceiver alarmReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Listener one time alarm
        binding.btnOnceDate.setOnClickListener(this);
        binding.btnOnceTime.setOnClickListener(this);
        binding.btnSetOnceAlarm.setOnClickListener(this);

        // Listener repeating alarm
        binding.btnRepeatingTime.setOnClickListener(this);
        binding.btnSetRepeatingAlarm.setOnClickListener(this);
        binding.btnCancelRepeatingAlarm.setOnClickListener(this);

        alarmReceiver = new AlarmReceiver();
    }

    private final static String DATE_PICKER_TAG = "DatePicker";
    private final static String TIME_PICKER_ONCE_TAG = "TimePickerOnce";
    private final static String TIME_PICKER_REPEAT_TAG = "TimePickerRepeat";

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_once_date) {
            DatePickerFragment datePickerFragment = new DatePickerFragment();
            datePickerFragment.show(getSupportFragmentManager(), DATE_PICKER_TAG);
        } else if (v.getId() == R.id.btn_once_time) {
            TimePickerFragment timePickerFragmentOne = new TimePickerFragment();
            timePickerFragmentOne.show(getSupportFragmentManager(), TIME_PICKER_ONCE_TAG);
        } else if (v.getId() == R.id.btn_set_once_alarm) {
            String onceDate = binding.tvOnceDate.getText().toString();
            String onceTime = binding.tvOnceTime.getText().toString();
            String onceMessage = binding.edtOnceMessage.getText().toString();

            alarmReceiver.setOneTimeAlarm(this, AlarmReceiver.TYPE_ONE_TIME,
                    onceDate,
                    onceTime,
                    onceMessage);
        } else if (v.getId() == R.id.btn_repeating_time) {
            TimePickerFragment timePickerFragmentRepeat = new TimePickerFragment();
            timePickerFragmentRepeat.show(getSupportFragmentManager(), TIME_PICKER_REPEAT_TAG);
        } else if (v.getId() == R.id.btn_set_repeating_alarm) {
            String repeatTime = binding.tvRepeatingTime.getText().toString();
            String repeatMessage = binding.edtRepeatingMessage.getText().toString();
            alarmReceiver.setRepeatingAlarm(this, AlarmReceiver.TYPE_REPEATING,
                    repeatTime, repeatMessage);
        } else if (v.getId() == R.id.btn_cancel_repeating_alarm) {
            alarmReceiver.cancelAlarm(this, AlarmReceiver.TYPE_REPEATING);
        }
    }

    @Override
    public void onDialogDateSet(String tag, int year, int month, int dayOfMonth) {

        // Siapkan date formatter-nya terlebih dahulu
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        // Set text dari textview once
        binding.tvOnceDate.setText(dateFormat.format(calendar.getTime()));
    }

    @Override
    public void onDialogTimeSet(String tag, int hourOfDay, int minute) {

        // Siapkan time formatter-nya terlebih dahulu
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

        // Set text dari textview berdasarkan tag
        switch (tag) {
            case TIME_PICKER_ONCE_TAG:
                binding.tvOnceTime.setText(dateFormat.format(calendar.getTime()));
                break;
            case TIME_PICKER_REPEAT_TAG:
                binding.tvRepeatingTime.setText(dateFormat.format(calendar.getTime()));
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}

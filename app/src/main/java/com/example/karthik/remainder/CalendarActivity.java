package com.example.karthik.remainder;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;
import android.widget.Toast;

public class CalendarActivity extends AppCompatActivity {
    CalendarView calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        calendar = (CalendarView) findViewById(R.id.myCalendar);
        final String[] date = new String[1];
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                int monthT = month+1;
                date[0] = dayOfMonth + "/" + monthT + "/" + year;
                Intent intent = new Intent(CalendarActivity.this, Input.class);
                intent.putExtra("Date",date[0]);
                startActivity(intent);
            }
        });

    }
}

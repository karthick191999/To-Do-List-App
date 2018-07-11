package com.example.karthik.remainder;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

/**
 * Created by karthik on 06-07-2018.
 */

public class Fragment_todo extends Fragment {
    TextView date, time;
    EditText input;
    LinearLayout timeL, dateL;
    Calendar calender;
    String amPm;
    Database_Todo database;
    Button add;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_todo, container, false);
        date = (TextView) view.findViewById(R.id.date);
        time = (TextView) view.findViewById(R.id.time);
        database = new Database_Todo(getActivity());
        add = (Button) view.findViewById(R.id.todoAdd);
        calender = Calendar.getInstance();
        input = (EditText) view.findViewById(R.id.todoInput);
        timeL = (LinearLayout) view.findViewById(R.id.timelayout);
        dateL = (LinearLayout) view.findViewById(R.id.dateLayout);
        timeL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int hour = calender.get(Calendar.HOUR_OF_DAY);
                int minute = calender.get(Calendar.MINUTE);
                TimePickerDialog timePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (hourOfDay >= 12)
                            amPm = "PM";
                        else
                            amPm = "AM";
                        time.setText(String.format("%02d:%02d", hourOfDay, minute) + amPm);
                    }
                }, hour, minute, false);
                timePicker.show();

            }
        });
        dateL.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                int days = calender.get(Calendar.DAY_OF_MONTH);
                int month = calender.get(Calendar.MONTH);
                int year = calender.get(Calendar.YEAR);
                final DatePickerDialog datePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        date.setText(dayOfMonth + "/" + month + "/" + year);

                    }
                }, year, month, days);

                datePicker.show();
            }
        });

        final String[] dtask = new String[1];
        final String[] ddate = new String[1];
        final String[] dtime = new String[1];
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dtask[0] = input.getText().toString();
                ddate[0] = date.getText().toString();
                dtime[0] = time.getText().toString();
                database.addData(dtask[0], ddate[0], dtime[0]);

            }
        });

        return view;

    }
}

package com.example.karthik.remainder;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.icu.text.NumberFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
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
 * Created by karthik on 03-07-2018.
 */

public class FragmentDoctor1 extends android.app.Fragment {
    TextView paid, due, time, date;
    LinearLayout timeL, dateL, paidL, dueL;
    EditText name;
    Button add;
    EditText editText;
    DatabaseBussinessPay database;
    TextWatcher tw = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {

            if (!s.toString().matches("^\\$(\\d{1,3}(\\,\\d{3})*|(\\d+))(\\.\\d{2})?$")) {
                String userInput = "" + s.toString().replaceAll("[^\\d]", "");
                StringBuilder cashAmountBuilder = new StringBuilder(userInput);

                while (cashAmountBuilder.length() > 3 && cashAmountBuilder.charAt(0) == '0') {
                    cashAmountBuilder.deleteCharAt(0);
                }
                while (cashAmountBuilder.length() < 3) {
                    cashAmountBuilder.insert(0, '0');
                }
                cashAmountBuilder.insert(cashAmountBuilder.length() - 2, '.');

                editText.removeTextChangedListener(this);
                editText.setText(cashAmountBuilder.toString());

                editText.setTextKeepState("$" + cashAmountBuilder.toString());
                Selection.setSelection(editText.getText(), cashAmountBuilder.toString().length() + 1);

                editText.addTextChangedListener(this);
            }
        }
    };
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bussiness_due, container, false);
        database = new DatabaseBussinessPay(getActivity());
        paid = (TextView) view.findViewById(R.id.paidMoney);
        due = (TextView) view.findViewById(R.id.dueMoney);
        time = (TextView) view.findViewById(R.id.time);
        date = (TextView) view.findViewById(R.id.date);
        name = (EditText) view.findViewById(R.id.businessInput);
        final Calendar calendar = Calendar.getInstance();
        paidL = (LinearLayout) view.findViewById(R.id.paidL);
        dueL = (LinearLayout) view.findViewById(R.id.dueL);
        timeL = (LinearLayout) view.findViewById(R.id.timeLayout);
        dateL = (LinearLayout) view.findViewById(R.id.dateLayout);
        add = (Button) view.findViewById(R.id.bussinessAdd);
        dateL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        date.setText(dayOfMonth + "/" + month + "/" + year);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
        timeL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] amPm = new String[1];
                int minute = calendar.get(Calendar.MINUTE);
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                TimePickerDialog dialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        if (hourOfDay >= 12)
                            amPm[0] = "PM";
                        else
                            amPm[0] = "AM";
                        time.setText(String.format("%02d:%02d", hourOfDay, minute) + amPm[0]);
                    }
                }, hour, minute, false);

                dialog.show();
            }
        });
        dueL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                final EditText editText = new EditText(getActivity());
                editText.addTextChangedListener(new NumberTextWatcher(editText));
                alertDialog.setTitle("Money Due");
                alertDialog.setView(editText);
                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String string = editText.getText().toString();
                        due.setText(string);
                    }
                });
                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.show();
            }
        });
        paidL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setTitle("Money paid");
                editText = new EditText(getActivity());
                editText.addTextChangedListener(new NumberTextWatcher(editText));
                alertDialog.setView(editText);

                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {




                        String string = editText.getText().toString();
                        paid.setText(string);
                    }
                });
                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.show();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dname = name.getText().toString();
                String dtime = time.getText().toString();
                String ddate = date.getText().toString();
                String mPaid = paid.getText().toString();
                String mDue = due.getText().toString();

                database.addData(dname, dtime, ddate, mPaid, mDue);
            }
        });

        return view;
    }
}

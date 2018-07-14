package com.example.karthik.remainder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Switch;

public class Settings extends AppCompatActivity {
    Switch toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        toggle = (Switch) findViewById(R.id.switch1);
        if (toggle.isChecked()) {
            setTheme(R.style.AppTheme_Dark);
        }
    }
}

package com.example.karthik.remainder;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class Input extends AppCompatActivity {
    LinearLayout todo, buss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        todo = (LinearLayout) findViewById(R.id.todo);
        buss = (LinearLayout) findViewById(R.id.bussiness);
        todo.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onClick(View v) {
                android.app.FragmentManager fm = getFragmentManager();
                Fragment_todo fragment_todo = new Fragment_todo();
                android.app.FragmentTransaction transaction = fm.beginTransaction();
                transaction.replace(R.id.myLayout, fragment_todo, "TODO");
                transaction.commit();
            }
        });
        buss.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onClick(View v) {
                FragmentDoctor1 fragment = new FragmentDoctor1();
                android.app.FragmentManager fm = getFragmentManager();
                android.app.FragmentTransaction transaction = fm.beginTransaction();
                transaction.replace(R.id.myLayout, fragment, "Bussiness");
                transaction.commit();
            }
        });
    }
}

package com.example.karthik.remainder;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class Input extends AppCompatActivity {
    public static String Idate;
    LinearLayout todo, buss;
    Toolbar toolbar;
    Window window;


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Idate = getIntent().getStringExtra("Date");
        window = getWindow();
        window.setStatusBarColor(Color.parseColor("#A0B1BF"));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        todo = (LinearLayout) findViewById(R.id.todo);
        buss = (LinearLayout) findViewById(R.id.bussiness);
        todo.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onClick(View v) {
                android.app.FragmentManager fm = getFragmentManager();
                Fragment_todo fragment_todo = new Fragment_todo();
                android.app.FragmentTransaction transaction = fm.beginTransaction();
                transaction.setCustomAnimations(R.animator.fragment_slide_left_enter,
                        R.animator.fragment_slide_left_exit,
                        R.animator.fragment_slide_right_enter,
                        R.animator.fragment_slide_right_exit);
                transaction.replace(R.id.myLayout, fragment_todo, "TODO");
                transaction.setTransition(android.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE);
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
                transaction.setCustomAnimations(R.animator.fragment_slide_left_enter,
                        R.animator.fragment_slide_left_exit,
                        R.animator.fragment_slide_right_enter,
                        R.animator.fragment_slide_right_exit);

                transaction.replace(R.id.myLayout, fragment, "Bussiness");
                //transaction.setTransition(android.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.commit();
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Input.this, MainActivity.class));
    }
}

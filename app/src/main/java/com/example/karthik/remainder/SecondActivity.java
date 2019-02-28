package com.example.karthik.remainder;

import android.app.Notification;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {
    ListView listView;
    Database_Todo todo;
    NotificationCompat.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        builder = new NotificationCompat.Builder(this);
        builder.setAutoCancel(true);
        todo = new Database_Todo(this);
        ArrayList<String> list = new ArrayList<>();
        listView = (ListView) findViewById(R.id.secondList);
        list = getIntent().getStringArrayListExtra("date");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
    }
}

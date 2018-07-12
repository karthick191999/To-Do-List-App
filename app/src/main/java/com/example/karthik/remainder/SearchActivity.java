package com.example.karthik.remainder;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    AutoCompleteTextView search;
    ImageButton searchButton;
    Database_Todo dt;
    DatabaseBussinessPay db;
    ArrayList<String> todoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        dt = new Database_Todo(this);
        db = new DatabaseBussinessPay(this);
        Cursor toCursor = dt.getData();
        while (toCursor.moveToNext()) {
            todoList.add(toCursor.getString(1));
        }
        Cursor bussiness = db.getData();
        while (bussiness.moveToNext()) {
            todoList.add(bussiness.getString(1));
        }
        ArrayAdapter<String> searchAdapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, todoList);


        search = (AutoCompleteTextView) findViewById(R.id.searchText);
        search.setAdapter(searchAdapter);
        search.setThreshold(1);
        search.setTextColor(Color.RED);
        searchButton = (ImageButton) findViewById(R.id.sButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                String s = search.getText().toString();
                Cursor buss = db.searchData(s);
                Cursor todo = dt.searchData(s);
                Log.d("Name", s);
                Log.d("Position", String.valueOf(todo.getCount()));
                if (todo.getCount() != 0) {
                    while (todo.moveToNext()) {
                        Toast.makeText(SearchActivity.this, "Element todo present", Toast.LENGTH_SHORT).show();
                    }
                }
                if (buss.getCount() != 0) {
                    while (buss.moveToNext()) {
                        Toast.makeText(SearchActivity.this, "Element buss present", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}

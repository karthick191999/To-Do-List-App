package com.example.karthik.remainder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

/**
 * Created by karthik on 15-07-2018.
 */

public class DataFavTodo extends SQLiteOpenHelper {
    private static final String database_name = "ftodoDatabase";
    private static final String table_name = "ftodoData";
    private static final String Col1 = "_id";
    private static final String Col2 = "task";
    private static final String Col3 = "date";
    private static final String Col4 = "time";

    public DataFavTodo(Context context) {
        super(context, database_name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + table_name + "(" + Col1 + " INTEGER PRIMARY KEY AUTOINCREMENT," + Col2 + " VARCHAR(255)," + Col3 + " VARCHAR(255)," + Col4 + " VARCHAR(255)) ;");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addData(int id, String task, String date, String time) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Col1, id);
        contentValues.put(Col2, task);
        contentValues.put(Col3, date);
        contentValues.put(Col4, time);
        database.insert(table_name, null, contentValues);
    }

    public void deleteData(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(table_name, Col1 + "=" + id, null);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public boolean staring(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + table_name + " where " + Col1 + "=?", new String[]{Integer.toString(id)}, null);
        Log.d("Inside Database", String.valueOf(cursor.getCount()));
        if (cursor.getCount() == 0)
            return false;
        else
            return true;
    }
}

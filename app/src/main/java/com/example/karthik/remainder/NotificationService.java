package com.example.karthik.remainder;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RemoteViews;

import java.util.ArrayList;

/**
 * Created by karthik on 20-07-2018.
 */

public class NotificationService extends Service {

    DatabaseBussinessPay buss;
    Database_Todo toDo;
    Cursor todo, cBuss;
    String date, time;
    ListView list;
    ArrayList<String> nlist;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        date = intent.getStringExtra("Date1");
        time = intent.getStringExtra("Time1");
        Log.d("Date,", "Working");
        String string = "";
        nlist = new ArrayList<>();
        list = new ListView(this);
        buss = new DatabaseBussinessPay(getApplicationContext());
        toDo = new Database_Todo(getApplicationContext());
        todo = toDo.dateNotify(date);
        while (todo.moveToNext()) {
            string += todo.getString(1);
            nlist.add(todo.getString(1));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nlist);
        list.setAdapter(adapter);
        RemoteViews rv = new RemoteViews(getPackageName(), R.layout.custoom_notification);


        NotificationCompat.Builder nb = new NotificationCompat.Builder(getApplicationContext());
        nb.setSmallIcon(R.drawable.date).setContentTitle("Notification").setContentText(string)
                .setDefaults(Notification.DEFAULT_LIGHTS).setVibrate(new long[]{1000, 1000});
        nb.setSound(alarmSound);
        NotificationManager notification = (NotificationManager) getApplicationContext().getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
        notification.notify(0, nb.build());
        return START_NOT_STICKY;

    }
}

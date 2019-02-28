package com.example.karthik.remainder;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
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

import static android.provider.LiveFolders.INTENT;

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
    ArrayList<String> name;
    RemoteViews remoteViews;

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
        int count = 0, bc = 0;

        date = intent.getStringExtra("Date1");
        time = intent.getStringExtra("Time1");
        // Log.d("Date,", "Working");
        nlist = new ArrayList<>();
        list = new ListView(this);
        buss = new DatabaseBussinessPay(getApplicationContext());
        toDo = new Database_Todo(getApplicationContext());
        todo = toDo.dateNotify(date, time);
        Log.d("Size", String.valueOf(nlist.size()));
        nlist.clear();

        while (todo.moveToNext()) {
            nlist.add(todo.getString(1));
            //  notify(todo.getInt(0), todo.getString(1));
            count++;
        }

        cBuss = buss.dateNotify(date, time);
        if (name != null)
            name.clear();
        while (cBuss.moveToNext()) {
            bc++;
            name.add(cBuss.getString(1));
        }
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nlist);
        //list.setAdapter(adapter);
        //       RemoteViews rv = new RemoteViews(getPackageName(), R.layout.ncustom_not);
        //     Intent rIntent = new Intent(getApplicationContext(), widgetService.class);
        //   rIntent.putStringArrayListExtra("Check", nlist);
        // PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, rIntent, 0);
        //rv.setOnClickPendingIntent(R.layout.ncustom_not, pendingIntent);
        //rv.setRemoteAdapter(R.id.notifyList, rIntent);
        Intent nIntent = new Intent(this, SecondActivity.class);
        Intent bIntent = new Intent(this, ThirdActivity.class);
        bIntent.putStringArrayListExtra("list", name);
        nIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        nIntent.putStringArrayListExtra("date", nlist);
        nIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(), 0, nIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent sPendingIntent = PendingIntent.getActivity(getBaseContext(), 0, bIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder nb = new NotificationCompat.Builder(getApplicationContext());
        nb.setSmallIcon(R.drawable.todo).setContentTitle("To do List").setContentText(Integer.toString(count))
                .setDefaults(Notification.DEFAULT_LIGHTS).setVibrate(new long[]{1000, 1000});
        nb.setSound(alarmSound);
        nb.setContentIntent(pendingIntent);
        //       for (int i=0;i<nlist.size();i++){
        //    nb.setStyle(new NotificationCompat.InboxStyle().addLine(nlist.get(i)));
        //     }

        NotificationCompat.Builder bussNotification = new NotificationCompat.Builder(getApplicationContext());
        bussNotification.setSmallIcon(R.drawable.bussnew).setContentTitle("Bussiness pay").setContentText(Integer.toString(bc));
        bussNotification.setDefaults(Notification.DEFAULT_LIGHTS).setVibrate(new long[]{1000, 1000}).setSound(alarmSound).setContentIntent(sPendingIntent);


        NotificationManager notification = (NotificationManager) getApplicationContext().getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
        notification.notify(0, nb.build());
        notification.notify(1, bussNotification.build());
        return START_NOT_STICKY;

    }

    //public void notify(int i, String task) {
    //Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    //NotificationCompat.Builder nb = new NotificationCompat.Builder(getApplicationContext());
    //nb.setSmallIcon(R.drawable.todo).setContentTitle("To do List").setContentText(task);
    //nb.setDefaults(Notification.DEFAULT_LIGHTS).setVibrate(new long[]{1000, 1000}).setSound(alarmSound);
    //NotificationManager notification = (NotificationManager) getApplicationContext().getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
    //  notification.notify(i, nb.build());
    //}
}

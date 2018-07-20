package com.example.karthik.remainder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by karthik on 20-07-2018.
 */

public class AlarmReciever extends BroadcastReceiver {
    String date, time;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("Inside", "ALarm Reciever");
        date = intent.getStringExtra("Date");
        Log.d("Inside Date", date);
        time = intent.getStringExtra("Time");
        Intent myIntent = new Intent(context, NotificationService.class);
        myIntent.putExtra("Date1", date);
        myIntent.putExtra("Time1", time);
        context.startService(myIntent);
    }
}

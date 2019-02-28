package com.example.karthik.remainder;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViewsService;

import java.util.ArrayList;

/**
 * Created by karthik on 21-07-2018.
 */

public class widgetService extends RemoteViewsService {
    ArrayList<String> list = new ArrayList<>();
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Log.d("Inside getView", "getView");
        list = intent.getStringArrayListExtra("Check");
        int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,AppWidgetManager.INVALID_APPWIDGET_ID);
        return (new ListProvider(this.getApplicationContext(), intent,list));
    }
}

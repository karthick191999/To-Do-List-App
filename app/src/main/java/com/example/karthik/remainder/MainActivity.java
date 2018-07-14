package com.example.karthik.remainder;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import static com.example.karthik.remainder.R.id.imageView;

public class MainActivity extends AppCompatActivity {
    Button button, exp;
    TabLayout tab;
    FloatingActionButton fab;
    EditText edit;
    ViewPager viewPager;
    DrawerLayout mDrawerLayout;
    NavigationView navigationView;
    Window window;
    ImageView imageView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.my_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.search) {
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            startActivity(intent);
            return true;
        }
        if (item.getItemId() == android.R.id.home) {
            mDrawerLayout.openDrawer(GravityCompat.START);
            return true;
        }

        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        window = getWindow();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.dLayout);
        navigationView = (NavigationView) findViewById(R.id.nView);
        View header = navigationView.getHeaderView(0);
        TextView textView = (TextView) header.findViewById(R.id.nav_header_textView);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE");
        Date d = new Date();
        int date = calendar.get(Calendar.DAY_OF_MONTH);
        SimpleDateFormat month_date = new SimpleDateFormat("MMM");
        String month_name = month_date.format(calendar.getTime());
        String dayOfTheWeek = sdf.format(d);
        textView.setText(dayOfTheWeek + ",  " + date + month_name);
        imageView = (ImageView) header.findViewById(R.id.nav_header_imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CalendarActivity.class);
                startActivity(intent);
                Toast.makeText(MainActivity.this, "Successful", Toast.LENGTH_LONG).show();
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_item_delete) {
                    Intent intent = new Intent(MainActivity.this, DeleteActivity.class);
                    startActivity(intent);
                }
                if (id == R.id.nav_item_three) {
                    Intent intent = new Intent(MainActivity.this, Settings.class);
                    startActivity(intent);
                }
                item.setChecked(true);
                mDrawerLayout.closeDrawers();
                return true;
            }
        });


        window.setStatusBarColor(Color.parseColor("#045A70"));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        exp = (Button) findViewById(R.id.expButton);
        exp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ExpActivity.class);
                startActivity(intent);
            }
        });
        getSupportActionBar().setTitle("DashBoard");
        viewPager = (ViewPager) findViewById(R.id.myPager);
        //edit = (EditText) findViewById(R.id.tryC);
        //edit.addTextChangedListener(tw);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        tab = (TabLayout) findViewById(R.id.tabs);
        MyPager pager = new MyPager(getSupportFragmentManager());
        viewPager.setAdapter(pager);
        tab.setupWithViewPager(viewPager);
        //button = (Button) findViewById(R.id.fragAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Input.class);
                startActivity(intent);
                notifications();
            }
        });


    }

    public void notifications() {
        android.support.v4.app.NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.date1)
                .setContentTitle("My Notification")
                .setContentText("U have an unread message");
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }

    class MyPager extends FragmentPagerAdapter {

        public MyPager(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Log.d("Position", String.valueOf(position));
            if (position == 0)
                return new FragmentA();
            if (position == 1)
                return new FragmentB();

            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0)
                return "ToDo";
            if (position == 1)
                return "BussinessPay";
            return null;
        }
    }


}

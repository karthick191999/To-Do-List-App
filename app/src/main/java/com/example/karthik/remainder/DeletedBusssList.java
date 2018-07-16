package com.example.karthik.remainder;

import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DeletedBusssList extends AppCompatActivity {
    DeleteBuss bussD;
    ListView list;
    ArrayList<BussinesspayClass> blist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deleted_busss_list);
        bussD = new DeleteBuss(this);
        list = (ListView) findViewById(R.id.bussList);
        Cursor data = bussD.getData();
        while (data.moveToNext()) {
            blist.add(new BussinesspayClass(data.getString(1), data.getString(2), data.getString(3), data.getString(4), data.getString(5), data.getInt(0)));
        }
        bussinessAdapter madapter = new bussinessAdapter(DeletedBusssList.this, R.layout.bussinesspay_singlerow, blist);
        list.setAdapter(madapter);
    }

    class bussinessAdapter extends ArrayAdapter<BussinesspayClass> {
        public bussinessAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<BussinesspayClass> objects) {
            super(context, resource, objects);
        }


        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View row = convertView;
            final BussinesspayClass bvar = getItem(position);
            if (row == null) {
                LayoutInflater inflater = getLayoutInflater();
                row = inflater.inflate(R.layout.bussinesspay_singlerow, parent, false);
            }


            ImageView delete;
            delete = (ImageView) row.findViewById(R.id.bussiness_delete);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id = bvar.getId();
                    blist.remove(position);
                    notifyDataSetChanged();
                }
            });
            TextView name, date, time, paid, due;
            name = (TextView) row.findViewById(R.id.listBname);
            date = (TextView) row.findViewById(R.id.listBdate);
            time = (TextView) row.findViewById(R.id.listBtime);
            paid = (TextView) row.findViewById(R.id.listBpaid);
            due = (TextView) row.findViewById(R.id.listBdue);
            name.setText(bvar.getName());
            date.setText(bvar.getDate());
            time.setText(bvar.getTime());
            paid.setText(bvar.getPaid());
            due.setText(bvar.getDue());

            return row;
        }
    }


}

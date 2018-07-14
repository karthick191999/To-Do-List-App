package com.example.karthik.remainder;

import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by karthik on 09-07-2018.
 */

public class FragmentB extends android.support.v4.app.Fragment {
    ListView list;
    DatabaseBussinessPay database;
    DataFavouriteBuss favData;
    ArrayList<BussinesspayClass> blist = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_b, container, false);
        database = new DatabaseBussinessPay(getActivity());
        favData = new DataFavouriteBuss(getActivity());
        list = (ListView) view.findViewById(R.id.bussinessList);
        Cursor data = database.getData();
        while (data.moveToNext()) {
            blist.add(new BussinesspayClass(data.getString(1), data.getString(2), data.getString(3), data.getString(4), data.getString(5), data.getInt(0)));
        }
        bussinessAdapter adapter = new bussinessAdapter(getActivity(), R.layout.bussinesspay_singlerow, blist);
        list.setAdapter(adapter);
        return view;
    }
    boolean checked[] = new boolean[20];
    class bussinessAdapter extends ArrayAdapter<BussinesspayClass> {
        public bussinessAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<BussinesspayClass> objects) {
            super(context, resource, objects);
        }



        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View row = convertView;
            final int[] flag = {0};
            final BussinesspayClass bvar = getItem(position);
            if (row == null) {
                LayoutInflater inflater = getActivity().getLayoutInflater();
                row = inflater.inflate(R.layout.bussinesspay_singlerow, parent, false);
            }
            final ImageView favImage = (ImageView) row.findViewById(R.id.favourite);

Log.d("StringingFav", String.valueOf(favData.staring(bvar.getId())));

            if (checked[position]) {
                favImage.setImageResource(R.drawable.fav_yes);
            } else
                favImage.setImageResource(R.drawable.fav_no);
            if (favData.staring(bvar.getId())) {
                favImage.setImageResource(R.drawable.fav_yes);
                checked[position] = true;
            }
            favImage.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onClick(View v) {
                    int id = bvar.getId();
                    if (checked[position]) {
                        favImage.setImageResource(R.drawable.fav_no);
                        checked[position] = false;
                    } else {
                        favImage.setImageResource(R.drawable.fav_yes);
                        checked[position] = true;

                    }
                    if (favData.staring(id)) {
                        flag[0] = 1;
                    }
                }
            });

            if (flag[0] == 1) {
                favData.deleteData(bvar.getId());

                flag[0] = 0;
                Toast.makeText(getActivity(), "Favourite added", Toast.LENGTH_SHORT).show();
                favImage.setImageResource(R.drawable.fav_no);
            } else {
                favData.addData(bvar.getName(), bvar.getDate(), bvar.getTime(), bvar.getPaid(), bvar.getDue());

            }
            ImageView delete;
            delete = (ImageView) row.findViewById(R.id.bussiness_delete);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id = bvar.getId();
                    database.deleteData(id);
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

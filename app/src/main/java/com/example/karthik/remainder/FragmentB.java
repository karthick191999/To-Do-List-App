package com.example.karthik.remainder;

import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by karthik on 09-07-2018.
 */

public class FragmentB extends android.support.v4.app.Fragment {
    ListView list;
    DatabaseBussinessPay database;
    ArrayList<BussinesspayClass> blist = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_b, container, false);
        database = new DatabaseBussinessPay(getActivity());
        list = (ListView) view.findViewById(R.id.bussinessList);
        Cursor data = database.getData();
        while (data.moveToNext()) {
            blist.add(new BussinesspayClass(data.getString(1), data.getString(2), data.getString(3), data.getString(4), data.getString(5)));
        }
        bussinessAdapter adapter = new bussinessAdapter(getActivity(), R.layout.bussinesspay_singlerow, blist);
        list.setAdapter(adapter);
        return view;
    }

    class bussinessAdapter extends ArrayAdapter<BussinesspayClass> {
        public bussinessAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<BussinesspayClass> objects) {
            super(context, resource, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View row = convertView;
            BussinesspayClass bvar = getItem(position);
            if (row == null) {
                LayoutInflater inflater = getActivity().getLayoutInflater();
                row = inflater.inflate(R.layout.bussinesspay_singlerow, parent, false);
            }
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

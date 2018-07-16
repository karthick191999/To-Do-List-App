package com.example.karthik.remainder;

import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DeleteActivity extends AppCompatActivity {
    DeleteTodo otodo;
    DeleteBuss bussD;
    ListView list;
    ArrayList<TodoClass> tlist = new ArrayList<>();
    ArrayList<BussinesspayClass> blist = new ArrayList<>();
    View bottomLayout;
    Button del, delBuss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        del = (Button) findViewById(R.id.delTodoB);
        delBuss = (Button) findViewById(R.id.delBuss);
        otodo = new DeleteTodo(DeleteActivity.this);
        bussD = new DeleteBuss(DeleteActivity.this);
        Cursor data = otodo.getData();
        if (data.getCount() != 0) {
            while (data.moveToNext()) {
                tlist.add(new TodoClass(data.getInt(0), data.getString(1), data.getString(2), data.getString(3)));
            }
        }
        Cursor mdata = bussD.getData();
        if (mdata.getCount() != 0) {
            while (mdata.moveToNext()) {
                blist.add(new BussinesspayClass(mdata.getString(1), mdata.getString(2), mdata.getString(3), mdata.getString(4), mdata.getString(5), mdata.getInt(0)));
            }
        }

        bottomLayout = findViewById(R.id.bLayout);
        final BottomSheetBehavior mBottomSheetBehavior1 = BottomSheetBehavior.from(bottomLayout);

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mBottomSheetBehavior1.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                    mBottomSheetBehavior1.setState(BottomSheetBehavior.STATE_EXPANDED);


                } else {
                    mBottomSheetBehavior1.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    mBottomSheetBehavior1.setPeekHeight(0);
                }


                list = (ListView) findViewById(R.id.deleteTodoList);

                toDoAdapter adapter = new toDoAdapter(DeleteActivity.this, R.layout.todo_singlerow, tlist);
                list.setAdapter(adapter);

            }
        });


        // Not Working apparently :(

        delBuss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mBottomSheetBehavior1.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                    mBottomSheetBehavior1.setState(BottomSheetBehavior.STATE_EXPANDED);


                } else {
                    mBottomSheetBehavior1.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    mBottomSheetBehavior1.setPeekHeight(0);
                }

                list = (ListView) findViewById(R.id.deleteTodoList);

                bussinessAdapter madapter = new bussinessAdapter(DeleteActivity.this, R.layout.bussinesspay_singlerow, blist);
                list.setAdapter(madapter);
            }
        });
    }

    class toDoAdapter extends ArrayAdapter<TodoClass> {
        public toDoAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<TodoClass> objects) {
            super(context, resource, objects);
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            final TodoClass todo = getItem(position);
            View row = convertView;
            if (row == null) {
                LayoutInflater inflater = getLayoutInflater();
                row = inflater.inflate(R.layout.todo_singlerow, parent, false);
            }
            ImageButton delete = (ImageButton) row.findViewById(R.id.delete);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id = todo.getId();
                    otodo.deleteData(id);
                    tlist.remove(position);
                    notifyDataSetChanged();
                }
            });
            TextView task, date, time;
            task = (TextView) row.findViewById(R.id.listTodoTask);
            date = (TextView) row.findViewById(R.id.listTodoDate);
            time = (TextView) row.findViewById(R.id.listTodoTime);
            task.setText(todo.getTask());
            date.setText(todo.getDate());
            time.setText(todo.getTime());
            return row;
        }
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
                    bussD.deleteData(bvar.getId());
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

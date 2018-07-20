package com.example.karthik.remainder;

import android.database.Cursor;
import android.graphics.Color;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class FavsActivity extends AppCompatActivity {
    DataFavouriteBuss favB;
    DataFavTodo favTodo;
    RecyclerView list;
    Button favT, favBuss;
    ArrayList<TodoClass> getTodoList = new ArrayList<>();
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favs);
        favB = new DataFavouriteBuss(this);
        favTodo = new DataFavTodo(this);
        layoutManager = new LinearLayoutManager(this);
        list = (RecyclerView) findViewById(R.id.favsTodoList);
        Cursor todoData = favTodo.getData();
        while (todoData.moveToNext()) {
            getTodoList.add(new TodoClass(todoData.getInt(1), todoData.getString(1), todoData.getString(2), todoData.getString(3), todoData.getString(4)));
        }
        mAdapter = new rTodo(getTodoList);
        list.setLayoutManager(layoutManager);

        list.setAdapter(mAdapter);
      /*  LinearLayout layout = (LinearLayout) findViewById(R.id.bLayout);
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(layout);
        favT = (Button) findViewById(R.id.favTodo);
        favBuss = (Button) findViewById(R.id.favBuss);
        favT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        favBuss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/


    }

    class rTodo extends RecyclerView.Adapter<rTodo.ViewHolder> {
        ArrayList<TodoClass> rTlist;

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView task, date, time;
            View layout;
            ImageButton delete;

            public ViewHolder(View itemView) {
                super(itemView);
                delete = (ImageButton) itemView.findViewById(R.id.delete);
                task = (TextView) itemView.findViewById(R.id.listTodoTask);
                date = (TextView) itemView.findViewById(R.id.listTodoDate);
                time = (TextView) itemView.findViewById(R.id.listTodoTime);
                layout = itemView;


            }
        }

        public rTodo(ArrayList<TodoClass> tList) {

            rTlist = tList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(
                    parent.getContext());
            View row = inflater.inflate(R.layout.todo_singlerow, parent, false);
            ViewHolder vh = new ViewHolder(row);
            return vh;

        }

        @Override
        public void onBindViewHolder(rTodo.ViewHolder holder, int position) {
            final TodoClass todoItem = rTlist.get(position);
            String colour = todoItem.getColour();
            if (colour.trim().equalsIgnoreCase("red".trim())) {
                holder.task.setTextColor(Color.parseColor("#EE3C00"));
            }
            if (colour.trim().equalsIgnoreCase("yellow".trim())) {
                holder.task.setTextColor(Color.parseColor("#EEC900"));
            }
            if (colour.trim().equalsIgnoreCase(("green").trim())) {
                holder.task.setTextColor(Color.parseColor("#38EE00"));
            }
            holder.task.setText(todoItem.getTask());
            holder.date.setText(todoItem.getDate());
            holder.time.setText(todoItem.getTime());

            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    favTodo.deleteData(todoItem.getId());
                }
            });


        }

        @Override
        public int getItemCount() {
            return rTlist.size();
        }
    }


}

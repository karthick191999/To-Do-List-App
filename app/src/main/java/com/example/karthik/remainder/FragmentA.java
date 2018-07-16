package com.example.karthik.remainder;

import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by karthik on 09-07-2018.
 */

public class FragmentA extends android.support.v4.app.Fragment {
    ListView list;
    LinearLayout layout;
    ArrayList<TodoClass> tlist = new ArrayList<>();
    Database_Todo database;
    DeleteTodo todoDatabase;
    DataFavTodo favTodo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_a, container, false);
        layout = (LinearLayout) view.findViewById(R.id.fragaId);
        list = (ListView) view.findViewById(R.id.todo_list);
        favTodo = new DataFavTodo(getActivity());
        database = new Database_Todo(getActivity());
        todoDatabase = new DeleteTodo(getActivity());
        Cursor data = database.getData();
        while (data.moveToNext()) {
            tlist.add(new TodoClass(data.getInt(0), data.getString(1), data.getString(2), data.getString(3), data.getString(4)));
        }
        toDoAdapter adapter = new toDoAdapter(getActivity(), R.layout.todo_singlerow, tlist);
        list.setAdapter(adapter);
        Log.d("Listing", String.valueOf(list));
        if (list.getAdapter().getCount() == 0) {
            layout.setBackgroundResource(R.drawable.editbacktodo);
        }
        return view;

    }


    class toDoAdapter extends ArrayAdapter<TodoClass> {
        public toDoAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<TodoClass> objects) {
            super(context, resource, objects);
        }

        boolean checked[] = new boolean[20];

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            final TodoClass todo = getItem(position);
            View row = convertView;
            if (row == null) {
                LayoutInflater inflater = getActivity().getLayoutInflater();
                row = inflater.inflate(R.layout.todo_singlerow, parent, false);
            }
            final ImageView fav = (ImageView) row.findViewById(R.id.fav_todo);

            if (checked[position]) {
                fav.setImageResource(R.drawable.fav_yes);
            } else {

                fav.setImageResource(R.drawable.fav_no);
            }
            if (favTodo.staring(todo.getId())) {
                Log.d("Starring", "yeah");
                fav.setImageResource(R.drawable.fav_yes);
                checked[position] = true;
            }

            fav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id = todo.getId();
                    if (checked[position]) {

                        favTodo.deleteData(id);
                        fav.setImageResource(R.drawable.fav_no);
                        checked[position] = false;
                    } else {
                        favTodo.addData(todo.getId(), todo.getTask(), todo.getDate(), todo.getTime(),todo.getColour());
                        fav.setImageResource(R.drawable.fav_yes);
                        checked[position] = true;


                    }
                }
            });
            ImageButton delete = (ImageButton) row.findViewById(R.id.delete);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    todoDatabase.addData(todo.getTask(), todo.getDate(), todo.getTime(), todo.getColour());
                    int id = todo.getId();
                    database.deleteData(id);
                    tlist.remove(position);
                    notifyDataSetChanged();
                }
            });
            TextView task, date, time;
            task = (TextView) row.findViewById(R.id.listTodoTask);
            date = (TextView) row.findViewById(R.id.listTodoDate);
            time = (TextView) row.findViewById(R.id.listTodoTime);
            String colour = todo.getColour();
            Log.d("Finding the colour", colour);
            task.setText(todo.getTask());
            if (colour.trim().equalsIgnoreCase("red".trim())) {
                task.setTextColor(Color.parseColor("#EE3C00"));
            }
            if (colour.trim().equalsIgnoreCase("yellow".trim())) {
                task.setTextColor(Color.parseColor("#EEC900"));
            }
            if (colour.trim().equalsIgnoreCase("green".trim())) {
                task.setTextColor(Color.parseColor("#38EE00"));
            }
            date.setText(todo.getDate());
            time.setText(todo.getTime());
            return row;
        }

    }


}

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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by karthik on 09-07-2018.
 */

public class FragmentA extends android.support.v4.app.Fragment {
    ListView list;
    ArrayList<TodoClass> tlist = new ArrayList<>();
    Database_Todo database;
    DeleteTodo todoDatabase;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_a, container, false);
        list = (ListView) view.findViewById(R.id.todo_list);
        database = new Database_Todo(getActivity());
        todoDatabase = new DeleteTodo(getActivity());
        Cursor data = database.getData();
        while (data.moveToNext()) {
            tlist.add(new TodoClass(data.getInt(0), data.getString(1), data.getString(2), data.getString(3)));
        }
        toDoAdapter adapter = new toDoAdapter(getActivity(), R.layout.todo_singlerow, tlist);
        list.setAdapter(adapter);
        return view;

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
                LayoutInflater inflater = getActivity().getLayoutInflater();
                row = inflater.inflate(R.layout.todo_singlerow, parent, false);
            }
            ImageButton delete = (ImageButton) row.findViewById(R.id.delete);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    todoDatabase.addData(todo.getTask(), todo.getDate(), todo.getTime());
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
            task.setText(todo.getTask());
            date.setText(todo.getDate());
            time.setText(todo.getTime());
            return row;
        }

    }


}

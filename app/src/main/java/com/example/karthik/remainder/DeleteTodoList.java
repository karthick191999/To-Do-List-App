package com.example.karthik.remainder;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DeleteTodoList extends AppCompatActivity {
    DeleteTodo todo;
    ListView list;
    ArrayList<TodoClass> tlist = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_todo_list);
        list = (ListView) findViewById(R.id.list);
        todo = new DeleteTodo(this);

        Cursor data = todo.getData();
        while (data.moveToNext()) {
            tlist.add(new TodoClass(data.getInt(0), data.getString(1), data.getString(2), data.getString(3)));
        }
       toDoAdapter adapter = new toDoAdapter(DeleteTodoList.this, R.layout.todo_singlerow, tlist);
        list.setAdapter(adapter);

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

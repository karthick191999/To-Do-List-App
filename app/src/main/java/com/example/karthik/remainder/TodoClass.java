package com.example.karthik.remainder;

/**
 * Created by karthik on 09-07-2018.
 */

public class TodoClass {
    String task;
    int id;
    String date;
    String time;

    public TodoClass(int id, String task, String date, String time) {
        this.task = task;
        this.date = date;
        this.time = time;
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

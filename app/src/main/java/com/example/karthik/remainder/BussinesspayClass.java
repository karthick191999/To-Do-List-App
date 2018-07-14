package com.example.karthik.remainder;

/**
 * Created by karthik on 09-07-2018.
 */

public class BussinesspayClass {
    String name;
    String date;
    String time;
    String paid;
    String due;
    int id;

    public BussinesspayClass(String name, String date, String time, String paid, String due, int id) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.paid = paid;
        this.due = due;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }

    public String getDue() {
        return due;
    }

    public void setDue(String due) {
        this.due = due;
    }


}

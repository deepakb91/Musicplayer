package com.example.deepak.musicservice;

public class Requests {
    int ID;
    String name;
    String date;

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

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
    public Requests(){

    }
    public Requests(int ID, String name, String date) {
        this.ID = ID;
        this.name = name;
        this.date = date;
    }
}

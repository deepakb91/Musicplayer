package com.example.deepak.musicclient;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] ID;
    private final String[] name;
    private final String[] date;
    public CustomAdapter(Activity context, String[] ID, String[] name, String[] date){
        super(context,R.layout.content_music_client,ID);
        this.context = context;
        this.ID=ID;
        this.name = name;
        this.date = date;
    }
    public View getView(int position, View view, ViewGroup parent){
        //inflate the view and populate the list with texts.
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.content_music_client, null, false);
        TextView iD = (TextView) rowView.findViewById(R.id.iD);
        TextView Name = (TextView) rowView.findViewById(R.id.name);
        TextView Date = (TextView) rowView.findViewById(R.id.date);
        iD.setText(ID[position]);
        Name.setText(name[position]);
        Date.setText(date[position]);
        return rowView;
    }

}
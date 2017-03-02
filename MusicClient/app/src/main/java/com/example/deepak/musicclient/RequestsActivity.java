package com.example.deepak.musicclient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

public class RequestsActivity extends Activity {
    String ID[];
    String name[];
    String date[];
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests);
        Intent intent = getIntent();
        ID = new String[intent.getStringArrayListExtra("ID").size()];
        ID = intent.getStringArrayListExtra("ID").toArray(ID);
        name = new String[intent.getStringArrayListExtra("name").size()];
        name = intent.getStringArrayListExtra("name").toArray(name);
        date = new String[intent.getStringArrayListExtra("date").size()];
        date = intent.getStringArrayListExtra("date").toArray(date);
        CustomAdapter adapter = new CustomAdapter(this, ID, name, date);
        lv = (ListView) findViewById(R.id.list);
        lv.setAdapter(adapter);
    }
}

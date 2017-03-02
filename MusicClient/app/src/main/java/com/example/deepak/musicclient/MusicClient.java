package com.example.deepak.musicclient;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.deepak.music.Music;

import java.util.ArrayList;

public class MusicClient extends Activity implements View.OnClickListener {

    EditText mFirst;
    Button mPlay,mPause,mStop,mFetch;
    protected Music mService;
    ServiceConnection mServiceConnection;
    private boolean isPlaying;
    private String musicNumber;
    private static int count=0;
    private static ArrayList<String> ID = new ArrayList<String>();
    private static ArrayList<String> name = new ArrayList<String>();
    private static ArrayList<String> date = new ArrayList<String>();
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_client);
        // determines the state of the music player
        isPlaying=false;
        mFirst = (EditText) findViewById(R.id.firstValue);
        mPlay = (Button) findViewById(R.id.play);
        mPlay.setOnClickListener(this);
        mPause = (Button) findViewById(R.id.pause);
        mPause.setOnClickListener(this);
        mStop = (Button) findViewById(R.id.stop);
        mStop.setOnClickListener(this);
        mFetch = (Button) findViewById(R.id.requests);
        mFetch.setOnClickListener(this);
        initConnection();

    }
    void initConnection(){
        mServiceConnection = new ServiceConnection() {



            @Override
            public void onServiceDisconnected(ComponentName name) {
                mService = null;
            }

            @Override
            public void onServiceConnected(ComponentName name, IBinder service)
            {
                mService = Music.Stub.asInterface((IBinder) service);
            }
        };
        if(mService == null)
        {
            Intent it = new Intent("MusicPlayer");
            ResolveInfo info = getPackageManager().resolveService(it,Service.BIND_AUTO_CREATE);
            it.setComponent(new ComponentName(info.serviceInfo.packageName,info.serviceInfo.name));

            //binding to remote service
            bindService(it, mServiceConnection, Service.BIND_AUTO_CREATE);
        }
    }
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnection);
        // stop the music player and record that the player has stopped
        java.util.Date dt = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = sdf.format(dt);
        count++;
        ID.add(String.valueOf(count));
        name.add("Stopped music " + musicNumber);
        date.add(currentTime);
    };

    @Override
    public void onClick(View v) {
        String a = mFirst.getText().toString();
        switch(v.getId()){
            case R.id.play:{
                if(a.equals("1")||a.equals("2")||a.equals("3")) {
                    try {
                        /* if the user has selected any of the available track then play
                         the music and record the activity */
                        isPlaying=true;
                        musicNumber=a;
                        java.util.Date dt = new java.util.Date();
                        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String currentTime = sdf.format(dt);
                        count++;
                        ID.add(String.valueOf(count));
                        name.add("Playing music " + a);
                        date.add(currentTime);
                        mService.play(a);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "Enter a song number between 1 and 3", Toast.LENGTH_LONG).show();
                }
                break;
            }
            case R.id.pause:{
                if(isPlaying) {
                    try {
                        /* if the player is already playing a music then pause it
                          and record the activity */
                        mService.pauseMusic();
                        java.util.Date dt = new java.util.Date();
                        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String currentTime = sdf.format(dt);
                        count++;
                        ID.add(String.valueOf(count));
                        name.add("Paused music "+musicNumber);
                        date.add(currentTime);
                        isPlaying=false;
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "No music is playing", Toast.LENGTH_LONG).show();
                }
                break;
            }
            case R.id.stop: {
                if(isPlaying) {
                    try {
                        /* if the player is already playing a music then stop it
                          and record the activity */
                        mService.stopMusic();
                        java.util.Date dt = new java.util.Date();
                        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String currentTime = sdf.format(dt);
                        count++;
                        ID.add(String.valueOf(count));
                        name.add("Stopped music "+musicNumber);
                        date.add(currentTime);
                        isPlaying=false;
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "No music is playing", Toast.LENGTH_LONG).show();
                }
                break;
            }
            case R.id.requests:{
                    Intent intent = new Intent(this,RequestsActivity.class);
                    intent.putStringArrayListExtra("ID",ID);
                    intent.putStringArrayListExtra("name",name);
                    intent.putStringArrayListExtra("date",date);
                    startActivity(intent);
                break;
            }
        }
    }
}

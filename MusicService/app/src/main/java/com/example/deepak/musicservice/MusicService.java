package com.example.deepak.musicservice;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.RemoteException;

import com.example.deepak.music.Music;

public class MusicService extends Service{
    private MediaPlayer mPlayer;
    private int pos=0;
    private String musicNumber;
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
    /**
     * Music definition is available here
     */
    private final Music.Stub mBinder = new Music.Stub() {

        public void play(String a) throws RemoteException {
            /* if the music player is already playing a music then stop it
             before playing the current music
            */
            if(mPlayer!=null && mPlayer.isPlaying()) {
                pos=0;
                mPlayer.stop();
            }
            /* if the user tries to play the same music again, then start playing it
               from the beginning
            */
            if(mPlayer!=null && !musicNumber.equals(a)){
                pos=0;
            }
            if(pos==0) {
                // play the music that the user has chosen
                if(a.equals("1"))
                    mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.badnews);
                else if(a.equals("2"))
                    mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.brahms);
                else if(a.equals("3"))
                    mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.largo);
                mPlayer.start();
                musicNumber = a;
            }
            else{
                /* the user has paused the music and wants to resume it.
                 So, play the music from the point where it was paused */
                mPlayer.seekTo(pos);
                mPlayer.start();
                musicNumber = a;
            }
        }

        public void pauseMusic() throws RemoteException{
            // if the music player is playing a music, then pause it
            if(mPlayer!=null && mPlayer.isPlaying()){
                pos=0;
                mPlayer.stop();
            }
        }
        public void stopMusic() throws RemoteException{
            // if the music player is playing a music, then stop it
            if(mPlayer!=null && mPlayer.isPlaying()){
                pos = mPlayer.getCurrentPosition();
                mPlayer.pause();
            }
        }
    };

    @Override
    public boolean onUnbind(Intent intent) {
        /* before unbinding, check if music player is playing a music.
           If it is playing a music, then stop it */
        if(mPlayer!=null && mPlayer.isPlaying()){
            pos=0;
            mPlayer.stop();
        }
        return super.onUnbind(intent);
    }
}
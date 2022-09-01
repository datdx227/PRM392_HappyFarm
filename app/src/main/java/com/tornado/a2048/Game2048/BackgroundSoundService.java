package com.tornado.a2048.Game2048;

import android.app.Service;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;

public class BackgroundSoundService extends Service {
    private static final String TAG = null;
    MediaPlayer m;

    public IBinder onBind(Intent arg0) {

        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("service", "onCreate");
        playBeep();

    }

    public void playBeep() {
        try {
            m = new MediaPlayer();
            if (m.isPlaying()) {
                m.stop();
                m.release();
                m = new MediaPlayer();
            }

            AssetFileDescriptor descriptor = getAssets().openFd("music.mp3");
            m.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
            descriptor.close();

            m.prepare();
            m.setVolume(1f, 1f);
            m.setLooping(true);
            m.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("service", "onStartCommand");
      /*  try {
            m.prepare();
            m.start();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        return START_NOT_STICKY;
    }

    public void onStart(Intent intent, int startId) {
        // TO DO
    }

    public IBinder onUnBind(Intent arg0) {
        // TO DO Auto-generated method
        return null;
    }

    public void onStop() {

    }

    public void onPause() {

    }

    @Override
    public void onDestroy() {
        m.stop();
        m.release();
    }

    @Override
    public void onLowMemory() {

    }
}

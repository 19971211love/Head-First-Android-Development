package com.example.lenovo.stopwatch;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class StopwatchActivity extends Activity {
//  record the number of seconds passed
    private int seconds = 0;
//  whether the stopwatch is running
    private boolean running;
//  whether the stopwatch was running before the activity was paused
    private boolean wasRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
        if(savedInstanceState != null){
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRuning");
        }
        runTimer();
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(wasRunning){
            running = true;
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        wasRunning = running;
        running = false;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putInt("seconds",seconds);
        savedInstanceState.putBoolean("running",running);
        savedInstanceState.putBoolean("wasRunning",wasRunning);

    }

    private void runTimer(){
        final TextView timeView = findViewById(R.id.time_view);
        final Handler handler = new Handler();
        handler.post(new Runnable(){
           public void run(){
               int hours = seconds/3600;
               int minutes = (seconds%3600)/60;
               int secs = seconds%60;

               String time = String.format(Locale.getDefault(),"%d:%02d:%02d",hours,minutes,secs);
               timeView.setText(time);
               if(running){
                   seconds ++;
               }
               handler.postDelayed(this,1000);
           }
        });
    }

    public void onClickStart(View view){
        running = true;

    }

    public void onClickStop(View view){
        running = false;
    }

    public void onClickReset(View view){
        running = false;
        seconds = 0;
    }

}

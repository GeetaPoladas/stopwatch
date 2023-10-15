package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import java.text.MessageFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    TextView t1;
    MaterialButton start,reset,stop;
    int sec,min,milliSecs;
    long millisecond,startTime,timeBuff,updateTime=0L;
    Handler handler;
    private final Runnable runnable=new Runnable() {
        @Override
        public void run() {
            millisecond= SystemClock.uptimeMillis()-startTime;
            updateTime=timeBuff+millisecond;
            sec=(int) (updateTime/1000);
            min=sec/60;
            sec=sec%60;
            milliSecs=(int) (updateTime%1000);

            t1.setText(MessageFormat.format("{0}:{1}:{2}",min,String.format(Locale.getDefault(),"%02d",sec),
                    String.format(Locale.getDefault(),"%02d",milliSecs)));
            handler.postDelayed(this,0);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        t1=findViewById(R.id.textView);
        start=findViewById(R.id.start);
        reset=findViewById(R.id.reset);
        stop=findViewById(R.id.stop);

        handler=new Handler(Looper.getMainLooper());

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime=SystemClock.uptimeMillis();
                handler.postDelayed(runnable,0);
                reset.setEnabled(false);
                stop.setEnabled(true);
                start.setEnabled(false);
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                millisecond=0L;
                startTime=0L;
                timeBuff=0L;
                updateTime=0L;
                sec=0;
                min=0;
                milliSecs=0;
                t1.setText("00:00:00");
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeBuff+=millisecond;
                handler.removeCallbacks(runnable);
                reset.setEnabled(true);
                stop.setEnabled(false);
                start.setEnabled(true);
            }
        });

        t1.setText("00:00:00");
    }
}
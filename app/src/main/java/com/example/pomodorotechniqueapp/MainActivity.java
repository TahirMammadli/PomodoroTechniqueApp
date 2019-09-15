package com.example.pomodorotechniqueapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private int seconds = 0;
    private boolean running;

    public static final int NUMBER_OF_MILLISECS = 5000;
    public static final int BREAK_MILLISECS = 5000;

    private CountDownTimer countDownTimer;
    private long timeLeftInMillis;
    private long timeLeftInMillis1;

    Button breakBtn;
    TextView timer_textView;
    Button startBtn;
    Button pauseBtn;
    Button resumeBtn;
    Button resetBtn;
//    TextView break_text;
//    TextView start_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeLeftInMillis = NUMBER_OF_MILLISECS;

        timer_textView = findViewById(R.id.timer_textView);
        breakBtn = findViewById(R.id.breakBtn);
        pauseBtn = findViewById(R.id.pauseBtn);
        startBtn = findViewById(R.id.startBtn);
        resumeBtn = findViewById(R.id.resumeBtn);
        resetBtn = findViewById(R.id.resetBtn);
//        start_text = findViewById(R.id.start_button_text);
//        break_text = findViewById(R.id.break_button_text);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeLeftInMillis = NUMBER_OF_MILLISECS;
                running = true;

                startCountDown();
                //start_text.setVisibility(View.INVISIBLE);
                resetBtn.setVisibility(View.VISIBLE);
                startBtn.setVisibility(View.INVISIBLE);
                timer_textView.setVisibility(View.VISIBLE);
                pauseBtn.setVisibility(View.VISIBLE);
                resetBtn.setVisibility(View.VISIBLE);
            }
        });

        breakBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startBreak();
                //resetBtn.setVisibility(View.VISIBLE);
                pauseBtn.setVisibility(View.VISIBLE);
            }
        });

        pauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pauseTimer();
            }
        });

        resumeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resumeTimer();
            }
        });

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetTimer();
                startBtn.setVisibility(View.VISIBLE);
                resetBtn.setVisibility(View.INVISIBLE);
                pauseBtn.setVisibility(View.INVISIBLE);
            }
        });
    }
    private void startCountDown() {
            countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
                @Override
                public void onTick(long millisLeft) {
                    timeLeftInMillis = millisLeft;
                    if(running){
                        seconds++;
                    }
                    updateCountDownText();
                }

                @Override
                public void onFinish() {
                    breakBtn.setVisibility(View.VISIBLE);
                    pauseBtn.setVisibility(View.INVISIBLE);
                    resetBtn.setVisibility(View.INVISIBLE);
                    //break_text.setVisibility(View.VISIBLE);
                }
            }.start();
        }


    private void updateCountDownText(){
        int minutes = (int)(timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        timer_textView.setText(timeFormatted);

        if(timeLeftInMillis < 5000){
            timer_textView.setTextColor(Color.RED);
        }else{
            timer_textView.setTextColor(Color.GREEN);
        }

    }

    private void startBreak(){
        running = false;
        breakBtn.setVisibility(View.INVISIBLE);
        //break_text.setVisibility(View.INVISIBLE);
        timeLeftInMillis = BREAK_MILLISECS;
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisLeft) {
                timeLeftInMillis = millisLeft;
                timer_textView.setVisibility(View.VISIBLE);
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                startBtn.setVisibility(View.VISIBLE);
                pauseBtn.setVisibility(View.INVISIBLE);
                resetBtn.setVisibility(View.INVISIBLE);
                //start_text.setVisibility(View.VISIBLE);
            }
        }.start();
    }

    private void pauseTimer(){
        countDownTimer.cancel();
        pauseBtn.setVisibility(View.INVISIBLE);
        resumeBtn.setVisibility(View.VISIBLE);
        resumeBtn.setEnabled(true);
    }

   private void resumeTimer(){
       startCountDown();
       resumeBtn.setVisibility(View.INVISIBLE);
       pauseBtn.setVisibility(View.VISIBLE);
    }

    private void resetTimer(){
        countDownTimer.cancel();
    }
   }


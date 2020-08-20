package com.example.timer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    SeekBar timerSeekBar;
    TextView timerTextView;
    Button controllerButton;
    Boolean counterIsActive = false;
    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timerTextView = findViewById(R.id.timerTextView);
        controllerButton = findViewById(R.id.controllerButton);
        mediaPlayer = MediaPlayer.create(this, R.raw.timer);
        timerSeekBar = findViewById(R.id.seekBarTimer);
        timerSeekBar.setMax(600);
        timerSeekBar.setProgress(0);

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                updateTimer(i);
                btnOperation();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        btnOperation();
    }

    public void onBtnClick(View view) {
        if (counterIsActive == false) {
            counterIsActive = true;
            timerSeekBar.setEnabled(false);
            controllerButton.setText("STOP");

        /* 1) Added 1/10th of a second to our total contdownTimer time (i.e 100).
           2) Added 100 becuase there was delay between 0.01 -0.00 interval,
              which gradually caused delay in playing the sound.
         */
            countDownTimer = new CountDownTimer(timerSeekBar.getProgress() * 1000 + 100, 1000) {
                @Override
                public void onTick(long l) {
                    updateTimer((int) l / 1000);// divide to convert milliseconds into seconds
                }

                @Override
                public void onFinish() {
                    mediaPlayer.start();
                    onReset();
                }
            }.start();
        } else {
            onReset();
        }
    }

    public void updateTimer(int secondsLeft) {
        int minutes = secondsLeft / 60;
        int seconds = secondsLeft - minutes * 60;
        String secondString = Integer.toString(seconds);
        if (seconds <= 9) {
            secondString = "0" + secondString;

        }
        timerTextView.setText(Integer.toString(minutes) + ":" + secondString);
    }

    public void onReset() {
        counterIsActive = false;
        timerTextView.setText("0:00");
        timerSeekBar.setProgress(0);
        timerSeekBar.setEnabled(true);
        controllerButton.setText("Go !");
        countDownTimer.cancel();
    }

    public void btnOperation(){
        if(timerTextView.getText().toString().equals("0:00")){
            controllerButton.setEnabled(false);
        }else{
            controllerButton.setEnabled(true);
        }

    }


}
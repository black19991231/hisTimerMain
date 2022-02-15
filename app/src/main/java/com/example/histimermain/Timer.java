package com.example.histimermain;

import android.os.CountDownTimer;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.TextView;

import java.util.Locale;

public class Timer extends AppCompatActivity {
    private static final long START_TIME_IN_MILLIS = 600000;

    private TextView mTextViewCountDown;//カウントダウンビュー
    private Button mButtonStartPause;//ストップボタン変数
    private Button mButtonReset;//リセットボタン変数

    private CountDownTimer mCountDownTimer;//カウントダウン変数

    private boolean mTimerRunning;//動いているかの判定

    private long mTimeLeftInMillis = 1500000;//カウントダウン時間設定

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        mTextViewCountDown = findViewById(R.id.text_view_countdown);
        mButtonStartPause = findViewById(R.id.button_start_pause);
        mButtonReset = findViewById(R.id.button_reset);

        Intent intent = this.getIntent();

        mButtonStartPause.setOnClickListener(new View.OnClickListener() {//pauseリスナ生成
            @Override
            public void onClick(View v) {
                if(mTimerRunning){
                    pauseTimer();//タイマーが動いていたらストップタイマーメソッドが実行　つまりカウントが止まる
                }else{
                    startTimer();//タイマーが動いていたらスタートタイマーメソッドが実行　つまりカウントが動き続ける
                }
            }
        });

        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restTimer();//リセットタイマーメソッドが実行され、カウントダウンが初期の値に戻る
            }
        });
        updateCountDownText();//ビュー表示の更新（動き続けるのか、止まるのか、リセットされるのか）
    }
    private void startTimer(){
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis,1000) {//(秒間,速さ※1000が通常)
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();//ビュー表示の更新
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                mButtonStartPause.setText("スタート");//mButtonStartPauseにて文字列をセット
                mButtonStartPause.setVisibility(View.INVISIBLE);//mButtonStartPauseボタンの非表示
                mButtonReset.setVisibility(View.VISIBLE);//mButtonResetボタンの表示
            }
        }.start();

        mTimerRunning = true;
        mButtonStartPause.setText("ストップ");
        mButtonReset.setVisibility(View.INVISIBLE);
    }

    private void pauseTimer(){
        mCountDownTimer.cancel();
        mTimerRunning = false;
        mButtonStartPause.setText("スタート");
        mButtonReset.setVisibility(View.VISIBLE);
    }

    private void restTimer(){
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        //
        mButtonReset.setVisibility(View.INVISIBLE);
        mButtonStartPause.setVisibility(View.VISIBLE);
    }

    private void updateCountDownText(){
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timerLeftFomatted = String.format(Locale.getDefault(),"%02d:%02d", minutes, seconds);
        mTextViewCountDown.setText(timerLeftFomatted);
    }
}
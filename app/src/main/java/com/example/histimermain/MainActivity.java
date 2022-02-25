package com.example.histimermain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
//レイアウトファイルにてフッターに背景画像（タイマーであれば時計の画像）を挿入する
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void onTimerClick(View v){
        Intent intent = new Intent(MainActivity.this, Timer.class);
        startActivity(intent);
    }

    public void onMemoClick(View v){
        Intent intent = new Intent(MainActivity.this, Memo.class);
        startActivity(intent);
    }

    public void onProjectClick(View v){
        Intent intent = new Intent(MainActivity.this, Project.class);
        startActivity(intent);
    }

}
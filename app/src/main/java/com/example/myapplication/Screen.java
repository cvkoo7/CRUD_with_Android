package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class Screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Handler handler;
        int theme = MainActivity.theme;
        super.onCreate(savedInstanceState);
        setTheme(theme);
        setContentView(R.layout.activity_screen);
        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(Screen.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        },3000);

    }
}
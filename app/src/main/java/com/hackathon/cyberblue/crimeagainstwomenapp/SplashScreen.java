package com.hackathon.cyberblue.crimeagainstwomenapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Intent i = new Intent(SplashScreen.this,MainActivity.class);
        startActivity(i);
    }
}

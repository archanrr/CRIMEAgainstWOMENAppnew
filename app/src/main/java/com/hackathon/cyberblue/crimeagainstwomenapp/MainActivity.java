package com.hackathon.cyberblue.crimeagainstwomenapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void reg(View view) {
        Intent intent=new Intent(this,MapsActivity.class);
        startActivity(intent);
    }
    public  void  cam(View v)
    {
        Intent i=new Intent(MainActivity.this,Main3Activity.class);
        startActivity(i);
    }

}

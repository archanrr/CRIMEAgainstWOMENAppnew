package com.hackathon.cyberblue.crimeagainstwomenapp;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;

import static java.lang.Math.sqrt;

public class Main3Activity extends AppCompatActivity {


    private SensorManager sm;
    private float acelVal; // current acceleration including gravity
    private float acelLast; // last acceleration including gravity
    private float shake; // acceleration apart from gravity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sm.registerListener(sensorListener, sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        shake = 0.00f;
        acelVal = SensorManager.GRAVITY_EARTH;
        acelLast = SensorManager.GRAVITY_EARTH;
    }

    private final SensorEventListener sensorListener = new SensorEventListener() {
        public void onSensorChanged(SensorEvent se) {
            float x = se.values[0];
            float y = se.values[1];
            float z = se.values[2];
            acelLast = acelVal;
            acelVal = (float) Math.sqrt((double) (x * x + y * y + z * z));
            float delta = acelVal - acelLast;
            shake = shake * 0.9f + delta; // perform low-cut filter
            if (shake > 12) {
                Toast toast = Toast.makeText(getApplicationContext(), "DO NOT SHAKE ME", Toast.LENGTH_SHORT);
                toast.show();

               takePictureNoPreview(getApplicationContext());

            }
        }

        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    public void takePictureNoPreview(Context context) {
        // open back facing camera by default
        Camera myCamera = Camera.open();


        if (myCamera != null) {
            try {
                //set camera parameters if you want to
                //...

                // here, the unused surface view and holder
                SurfaceView dummy = new SurfaceView(context);
                myCamera.setPreviewDisplay(dummy.getHolder());
                myCamera.startPreview();

                myCamera.takePicture(null, null, getJpegCallback());

            } catch (IOException e) {
                e.printStackTrace();
            } finally {

            }
        } else {
            //booo, failed!
        }
    }


    private Camera.PictureCallback getJpegCallback() {
        Camera.PictureCallback jpeg = new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                FileOutputStream fos;
                try {
                    fos = new FileOutputStream("test.jpeg");
                    fos.write(data);
                    fos.close();
                } catch (IOException e) {
                    //do something about it
                }
            }
        };


        return jpeg;
    }
}
package com.vilas.sensorrotation;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SensorActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor rotationSensor;
    private Handler handler;
    private boolean flag = false;
    private TextView text_x;
    private TextView text_y;
    private TextView text_z;
    private Button secondButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get an instance of sensor service ,and use that to get an instance of particular sensor
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        rotationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        text_x = findViewById(R.id.text_x);
        text_y = findViewById(R.id.text_y);
        text_z = findViewById(R.id.text_z);

        handler = new Handler();
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, rotationSensor, SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_UI);
        handler.post(processSensors);
    }

    @Override
    protected void onPause() {
        //unregister listener
        sensorManager.unregisterListener(this);
        handler.removeCallbacks(processSensors);
        super.onPause();
    }

    private final Runnable processSensors = new Runnable() {
        @Override
        public void run() {
            flag = true;
            //read sensor data after 8ms.
            int interval = 8;
            handler.postDelayed(this, interval);
        }
    };

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (flag) {
            // Update UI for one reading every 8 millisecond...
            text_x.setText(String.format("Sensor Data of X%s", event.values[0]));
            text_y.setText(String.format("Sensor Data of Y%s", event.values[1]));
            text_z.setText(String.format("Sensor Data of Z%s", event.values[2]));
            //
            flag = false;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
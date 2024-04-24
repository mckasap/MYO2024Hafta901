package com.example.myo2024hafta901;


import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
 TextView tvX;
 TextView tvY;
 TextView tvZ;
 SensorManager sm;
 Sensor sensor;
long lastUpdate;

    @Override
    protected void onResume() {

        sm.registerListener(this,
                sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        sm= (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor=sm.getDefaultSensor(SensorManager.SENSOR_ACCELEROMETER);
        tvX= (TextView) findViewById(R.id.textView);
        tvY=(TextView) findViewById(R.id.textView2);
        tvZ=(TextView) findViewById(R.id.textView3);
        lastUpdate=System.currentTimeMillis();

    }

    @Override
    protected void onPause() {
        sm.unregisterListener(this);
        super.onPause();
    }
boolean color=true;
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float[] values = sensorEvent.values;
        // Movement
        float x = values[0];
        float y = values[1];
        float z = values[2];

        tvX.setText("X:"+x+"");
        tvY.setText("Y:"+y+"");
        tvZ.setText("Z:"+z+"");




        float accelationSquareRoot = (x * x + y * y + z * z)
                / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
        long actualTime = sensorEvent.timestamp;
        if (accelationSquareRoot >= 2) //
        {
            if (actualTime - lastUpdate < 200) {
                return;
            }
            lastUpdate = actualTime;
            Toast.makeText(this, "Device was shuffed", Toast.LENGTH_SHORT)
                    .show();
            if (color) {
                tvX.setBackgroundColor(Color.GREEN);
                tvY.setBackgroundColor(Color.GREEN);
                tvZ.setBackgroundColor(Color.GREEN);
            } else {
                tvX.setBackgroundColor(Color.RED);

                tvY.setBackgroundColor(Color.RED);
                tvZ.setBackgroundColor(Color.RED);
            }
            color = !color;
        }


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
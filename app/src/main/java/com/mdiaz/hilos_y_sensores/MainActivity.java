package com.mdiaz.hilos_y_sensores;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    TextView tvSensors, tvAcelerometro, tvAcelerometroDes;

    List<Sensor> sensors;
    Sensor sp, slx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvSensors = (TextView) findViewById(R.id.tv_Sensors);
        tvAcelerometro = (TextView) findViewById(R.id.tv_Acelerometro);
        tvAcelerometroDes = (TextView) findViewById(R.id.tv_AcelerometroDes);

        SensorManager sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensors = sm.getSensorList(Sensor.TYPE_ALL);

        int i = 1;
        for(Iterator<Sensor> it = sensors.iterator(); it.hasNext(); i++){
            Sensor s = it.next();
            tvSensors.append(String.format("%d: %s, %d ,%s\n", i,s.getName(),s.getType(),s.getVendor()));
        }

        sp = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sm.registerListener((SensorEventListener) this,sp,SensorManager.SENSOR_DELAY_NORMAL);

        slx = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER_UNCALIBRATED);
        sm.registerListener((SensorEventListener) this,slx, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        switch (sensorEvent.sensor.getType()){
            case Sensor.TYPE_ACCELEROMETER:
                tvAcelerometro.setText(String.format("%f",sensorEvent.values[0]));
                break;

            case Sensor.TYPE_ACCELEROMETER_UNCALIBRATED:
                tvAcelerometroDes.setText(String.format("%f", sensorEvent.values[0]));
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public void primerHilo(View v){
        Toast.makeText(this,"Iniciando primer hilo", Toast.LENGTH_LONG).show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),"Finalizando primer hilo", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }).start();
    }

    public void segundoHilo(View v){
        Toast.makeText(this,"Iniciando segundo hilo", Toast.LENGTH_LONG).show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),"Finalizando segundo hilo", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }).start();
    }
}
package com.virtualart.virtualart;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Activity for testing the behavior of accelerometer/compass/GPS
 *
 */
public class SensorActivity extends Activity implements SensorEventListener {

    //Thread responsible for updating our sensor status
    private Thread mThread;

    SensorManager sMgr;
    Sensor sAccelrometerSensor;
    
    private TextView mAccelXView;
    private TextView mAccelYView;
    private TextView mAccelZView;
    
    private TextView mCompassView;
    private TextView mPitchView;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set up sensors
        sMgr = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sAccelrometerSensor = sMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //sMgr.registerListener(this, accelrometerSensor, SensorManager.SENSOR_DELAY_UI);
        sMgr.registerListener(this, sAccelrometerSensor, SensorManager.SENSOR_DELAY_GAME);
        
        //Set up view
        setContentView(R.layout.sensor);

        mAccelXView = (TextView)this.findViewById(R.id.accel_x_value);
        mAccelYView = (TextView)this.findViewById(R.id.accel_y_value);
        mAccelZView = (TextView)this.findViewById(R.id.accel_z_value);

        mCompassView = (TextView)this.findViewById(R.id.compass_value);
        mPitchView = (TextView)this.findViewById(R.id.pitch_value);

    }

    public void onSensorUpdate() {

        mAccelXView.setText("Yahoo");
        mAccelYView.setText("Yahoo");
        mAccelZView.setText("Yahoo");
        
        mPitchView.setText("Yahoo");
        mCompassView.setText("Yahoo");

    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO Auto-generated method stub

    }

    public void onSensorChanged(SensorEvent event) {
        // TODO Auto-generated method stub
        float[] sensorValues = event.values;
        if (event.sensor == sAccelrometerSensor) {
            //Toast.makeText(
            //    AndAccelorometer.this,
            //    "X = " + sensorValues[0] + " Y = " + sensorValues[1]
            //    + " Z = " + sensorValues[2], Toast.LENGTH_SHORT
            //).show();

            
            mAccelXView.setText("" + sensorValues[0]);
            mAccelYView.setText("" + sensorValues[1]);
            mAccelZView.setText("" + sensorValues[2]);
        }
    }
}

package com.virtualart.virtualart;

import com.virtualart.virtualart.render.Matrix;

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
    
    // Model

    SensorManager sMgr;
    Sensor sAccelerometerSensor;
    Sensor sMagneticSensor;

    private float[] mAccelerometerValues = new float[3];
    private float[] mCompassValues       = new float[3];

    // UI
    private TextView mAccelXView;
    private TextView mAccelYView;
    private TextView mAccelZView;
    
    private TextView mCompassXView;
    private TextView mCompassYView;
    private TextView mCompassZView;
    
    private TextView mPitchView;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set up sensors
        sMgr = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sAccelerometerSensor = sMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sMagneticSensor      = sMgr.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        sMgr.registerListener(this, sAccelerometerSensor, SensorManager.SENSOR_DELAY_GAME);
        sMgr.registerListener(this, sMagneticSensor, SensorManager.SENSOR_DELAY_GAME);
        
        //Set up view
        setContentView(R.layout.sensor);

        mAccelXView = (TextView)this.findViewById(R.id.accel_x_value);
        mAccelYView = (TextView)this.findViewById(R.id.accel_y_value);
        mAccelZView = (TextView)this.findViewById(R.id.accel_z_value);

        mCompassXView = (TextView)this.findViewById(R.id.compass_x_value);
        mCompassYView = (TextView)this.findViewById(R.id.compass_y_value);
        mCompassZView = (TextView)this.findViewById(R.id.compass_z_value);
        
        
        mPitchView = (TextView)this.findViewById(R.id.pitch_value);

    }

    public void updateOrientation() {
        float[] rotationMatrix    = new float[16];
        float[] inclinationMatrix = new float[16];

        //getRotationMatrix (float[] R, float[] I, float[] gravity, float[] geomagnetic)
        sMgr.getRotationMatrix(
            rotationMatrix,
            inclinationMatrix,
            mAccelerometerValues,
            mCompassValues
        );

        Matrix matrix = new Matrix(
            //rotationMatrix[0][0], rotationMatrix[0][1], rotationMatrix[0][2],
            //rotationMatrix[1][0], rotationMatrix[1][1], rotationMatrix[1][2],
            //rotationMatrix[2][0], rotationMatrix[2][1], rotationMatrix[2][2],
            rotationMatrix[0], rotationMatrix[1], rotationMatrix[2],
            rotationMatrix[4], rotationMatrix[5], rotationMatrix[6],
            rotationMatrix[8], rotationMatrix[9], rotationMatrix[10]
        );

        mPitchView.setText(
            matrix.toString()
            //String.format("%.1f", sensorValues[0])
        );
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO Auto-generated method stub
    }

    public void onSensorChanged(SensorEvent event) {
        float[] sensorValues = event.values;
        if (event.sensor == sAccelerometerSensor) {
            mAccelerometerValues = sensorValues;
            
            //Show accel values
            mAccelXView.setText(String.format("%.1f", sensorValues[0]));
            mAccelYView.setText(String.format("%.1f", sensorValues[1]));
            mAccelZView.setText(String.format("%.1f", sensorValues[2]));
            
        } else if (event.sensor == sMagneticSensor) {
            mCompassValues = sensorValues;

            //Show compass
            for (int i =0; i < sensorValues.length; i++) { 
                mCompassXView.setText(String.format("%.1f", sensorValues[0]));
                mCompassYView.setText(String.format("%.1f", sensorValues[1]));
                mCompassZView.setText(String.format("%.1f", sensorValues[2]));
                //Log.v( "Sensor", String.format( "Sensor %d, value: %f", i, sensorValues[i]));
            }
        }

        updateOrientation();

        //SensorManager.getRotationMatrix(
        //);
    }
}

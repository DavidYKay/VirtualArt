package com.virtualart.virtualart.util;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.virtualart.virtualart.render.Matrix;

/**
 * Class to help out with out sensor needs
 */
public class SensorHelper implements SensorEventListener {

	public interface SensorHelperListener extends java.util.EventListener {
		public void SensorHelperDidUpdate(SensorHelper sensorHelper);
	}

	//private Context mContext;
    private SensorManager sMgr;
    private Sensor sAccelerometerSensor;
    private Sensor sMagneticSensor;

    private float[] mAccelerometerValues = new float[3];
    private float[] mCompassValues       = new float[3];

	public SensorHelper(Context context) {
		//mContext = context;
		
        //Set up sensors
        sMgr = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sAccelerometerSensor = sMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sMagneticSensor      = sMgr.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        sMgr.registerListener(this, sAccelerometerSensor, SensorManager.SENSOR_DELAY_GAME);
        sMgr.registerListener(this, sMagneticSensor, SensorManager.SENSOR_DELAY_GAME);
	}
	
	public float[] getAccelerometerValues() {
		return mAccelerometerValues;
	}
	
	public float[] getCompassValues() {
		return mCompassValues;
	}

	public float[] getRotationMatrix() {
        //float[] rotationMatrix    = new float[16];
        //float[] inclinationMatrix = new float[16];
        float[] rotationMatrix    = new float[9];
        float[] inclinationMatrix = new float[9];

        sMgr.getRotationMatrix(
            rotationMatrix,
            inclinationMatrix,
            mAccelerometerValues,
            mCompassValues
        );

        //Matrix matrix = new Matrix(
        //    //rotationMatrix[0][0], rotationMatrix[0][1], rotationMatrix[0][2],
        //    //rotationMatrix[1][0], rotationMatrix[1][1], rotationMatrix[1][2],
        //    //rotationMatrix[2][0], rotationMatrix[2][1], rotationMatrix[2][2],
        //    rotationMatrix[0], rotationMatrix[1], rotationMatrix[2],
        //    rotationMatrix[4], rotationMatrix[5], rotationMatrix[6],
        //    rotationMatrix[8], rotationMatrix[9], rotationMatrix[10]
        //);
        return rotationMatrix;
	}

    @Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO Auto-generated method stub
    }
    
    @Override
	public void onSensorChanged(SensorEvent event) {
        float[] sensorValues = event.values;
        if (event.sensor == sAccelerometerSensor) {
            mAccelerometerValues = sensorValues;
        } else if (event.sensor == sMagneticSensor) {
            mCompassValues = sensorValues;
        }

		//Notify Listeners
        notifyListeners();
    }


	//Listener management

	//private EventListenerList<SensorHelperListener> mEventListenerList = new EventListenerList<SensorHelperListener>();
	private EventListenerList mEventListenerList = new EventListenerList();

	public void addListener(SensorHelperListener listener) {
		mEventListenerList.add(
			SensorHelperListener.class,
			listener
		);
	}
	
	public void removeListener(SensorHelperListener listener) {
		mEventListenerList.remove(
			SensorHelperListener.class,
			listener
		);
	}

	private void notifyListeners() {
		//For each listener, call the event
		for (SensorHelperListener listener : mEventListenerList.getListeners(SensorHelperListener.class)) {
			listener.SensorHelperDidUpdate(this);
		}
	}
}

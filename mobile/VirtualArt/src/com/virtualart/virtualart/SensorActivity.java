package com.virtualart.virtualart;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.virtualart.virtualart.render.Matrix;
import com.virtualart.virtualart.util.SensorHelper;
import com.virtualart.virtualart.util.SensorHelper.SensorHelperListener;

/**
 * Activity for testing the behavior of accelerometer/compass/GPS
 *
 */
public class SensorActivity extends Activity implements SensorHelperListener {
    
    // Model
    private SensorHelper mSensorHelper;

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
        
        //Set up view
        setContentView(R.layout.sensor);

        mSensorHelper = new SensorHelper(this);
        mSensorHelper.addListener(this);

        mAccelXView = (TextView)this.findViewById(R.id.accel_x_value);
        mAccelYView = (TextView)this.findViewById(R.id.accel_y_value);
        mAccelZView = (TextView)this.findViewById(R.id.accel_z_value);

        mCompassXView = (TextView)this.findViewById(R.id.compass_x_value);
        mCompassYView = (TextView)this.findViewById(R.id.compass_y_value);
        mCompassZView = (TextView)this.findViewById(R.id.compass_z_value);
        
        mPitchView = (TextView)this.findViewById(R.id.pitch_value);
    }
    
    public void SensorHelperDidUpdate(SensorHelper sensorHelper) {
        float[] accelerometerValues = sensorHelper.getAccelerometerValues();
        float[] compassValues = sensorHelper.getCompassValues();

        mAccelXView.setText(String.format("%.1f", accelerometerValues[0]));
        mAccelYView.setText(String.format("%.1f", accelerometerValues[1]));
        mAccelZView.setText(String.format("%.1f", accelerometerValues[2]));

        mCompassXView.setText(String.format("%.1f", compassValues[0]));
        mCompassYView.setText(String.format("%.1f", compassValues[1]));
        mCompassZView.setText(String.format("%.1f", compassValues[2]));

        mPitchView.setText(
            //sensorHelper.getRotationMatrix().toString()
            createDebugMatrix(
                sensorHelper.getRotationMatrix()
            ).toString()
        );
    }
   
    
    public static Matrix createDebugMatrix(float[] rotationMatrix) {
    	if (rotationMatrix.length == 16) {
    		return new Matrix(
    				rotationMatrix[0], rotationMatrix[1], rotationMatrix[2],
    				rotationMatrix[4], rotationMatrix[5], rotationMatrix[6],
    				rotationMatrix[8], rotationMatrix[9], rotationMatrix[10]
    				); 
    	} else if (rotationMatrix.length == 9) {

    		return new Matrix(
    				rotationMatrix[0], rotationMatrix[1], rotationMatrix[2],
    				rotationMatrix[3], rotationMatrix[4], rotationMatrix[5],
    				rotationMatrix[6], rotationMatrix[7], rotationMatrix[8]
    				);
    	} else {
    		return null;
    	}
    }
}

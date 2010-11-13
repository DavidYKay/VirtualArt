package com.virtualart.virtualart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Just a menu. In the future we may scrap this activity entirely.
 */
public class MainActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

		final Button browserButton  = (Button)this.findViewById(R.id.browser_button);
        browserButton.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v) {
        		Intent myIntent = new Intent(MainActivity.this, BrowserActivity.class);
        		MainActivity.this.startActivity(myIntent);
        	}
        });

		final Button paintButton  = (Button)this.findViewById(R.id.paint_button);
        paintButton.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v) {
        		Intent myIntent = new Intent(MainActivity.this, PaintActivity.class);        
        		MainActivity.this.startActivity(myIntent);
        	}        	
        });

		final Button sensorButton  = (Button)this.findViewById(R.id.sensor_button);
        sensorButton.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v) {
        		Intent myIntent = new Intent(MainActivity.this, SensorActivity.class);
        		MainActivity.this.startActivity(myIntent);
        	}        	
        });

		final Button cameraButton  = (Button)this.findViewById(R.id.camera_button);
        cameraButton.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v) {
        		Intent myIntent = new Intent(MainActivity.this, CameraActivity.class);
        		MainActivity.this.startActivity(myIntent);
        	}        	
        });
    }
}

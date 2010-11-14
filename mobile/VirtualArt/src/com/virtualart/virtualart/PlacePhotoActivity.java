package com.virtualart.virtualart;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Once the art has been made, let's place it on the wall.
 *
 * We need to show a live camera feed in the background, the image in the foreground.
 *
 */
public class PlacePhotoActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TextView tv = new TextView(this);
		tv.setText("Place Photo!");
		setContentView(tv);
	}
	
}

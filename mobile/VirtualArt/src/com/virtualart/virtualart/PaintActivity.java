package com.virtualart.virtualart;

import android.app.Activity;
import android.os.Bundle;

/**
 * The graffiti/fingerpainting activity.
 */
public class PaintActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paint);
    }
}

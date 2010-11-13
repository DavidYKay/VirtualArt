package com.virtualart.virtualart;

import android.app.Activity;
import android.os.Bundle;

/**
 * The Activity that houses the AR browser itself.
 */
public class BrowserActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browser);
    }
}

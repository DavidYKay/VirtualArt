package com.virtualart.virtualart;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup.LayoutParams;

import com.virtualart.virtualart.model.ArtItem;
import com.virtualart.virtualart.model.ArtModel;
import com.virtualart.virtualart.model.ArtModel.ArtModelListener;
import com.virtualart.virtualart.render.image.ImageRenderer;

/**
 * The graffiti/fingerpainting activity.
 */
public class BrowserActivity extends Activity implements ArtModelListener {
    private GLSurfaceView mGLSurfaceView;
    private Preview mPreview; 
    private ImageRenderer mRenderer;

    private ArtModel mModel;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ////////////////////////////////////////
        // MODEL
        ////////////////////////////////////////

        mModel = new ArtModel(this);
        mModel.addListener(this);

        ////////////////////////////////////////
        // OPEN GL
        ////////////////////////////////////////

        // Create our Preview view and set it as the content of our
        // Activity
        mGLSurfaceView = new GLSurfaceView(this);
        
        // We want an 8888 pixel format because that's required for
        // a translucent window.
        // And we want a depth buffer.
        mGLSurfaceView.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        
        mRenderer = new ImageRenderer(this);
        mGLSurfaceView.setRenderer(mRenderer);
        
        // Use a surface format with an Alpha channel:
        mGLSurfaceView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
        
        setContentView(mGLSurfaceView);
        
        // To preview camera
        mPreview = new Preview(this); 
        addContentView(mPreview, new LayoutParams(LayoutParams.WRAP_CONTENT, 
        LayoutParams.WRAP_CONTENT)); 
    }
        
    ////////////////////////////////////////
    // NOTIFICATIONS
    ////////////////////////////////////////
        
    public void artModelAddedItem(ArtModel model, ArtItem item) {
        //Need to add item to scene graph

    }
    
    public void artModelClearedItems(ArtModel model) {
        //Need to clear scene graph

    }

    public void artModelUpdatedPosition(ArtModel model) {
        //Need to render frame with new rotation matrix
        Log.v("BrowserActivity", "artModelUpdatedPosition");

        model.getRotationMatrix();

        //Pass rotation matrix into imageRenderer
        
    }

    @Override
    protected void onResume() {
        // Ideally a game should implement onResume() and onPause()
        // to take appropriate action when the activity looses focus
        super.onResume();
        mGLSurfaceView.onResume();
    }

    @Override
    protected void onPause() {
        // Ideally a game should implement onResume() and onPause()
        // to take appropriate action when the activity looses focus
        super.onPause();
        mGLSurfaceView.onPause();
    }
    
    // ------------------------------------------------------------------
    class Preview extends SurfaceView implements SurfaceHolder.Callback { 
    	  SurfaceHolder mHolder; 
    	  Camera mCamera; 
    	  Preview(Context context) { 
    	      super(context); 
    	      // Install a SurfaceHolder.Callback so we get notified when the 
    	      // underlying surface is created and destroyed. 
    	      mHolder = getHolder(); 
    	      mHolder.addCallback(this); 
    	      mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS); 
    	  } 
    	  public void surfaceCreated(SurfaceHolder holder) { 
    	      // The Surface has been created, acquire the camera and tell it where 
    	      // to draw. 
    	      mCamera = Camera.open(); 
    	      try {
				mCamera.setPreviewDisplay(holder);
			} catch (IOException e) {
				e.printStackTrace();
			} 
    	  } 
    	  public void surfaceDestroyed(SurfaceHolder holder) { 
    	      // Surface will be destroyed when we return, so stop the preview. 
    	      // Because the CameraDevice object is not a shared resource, it's very 
    	      // important to release it when the activity is paused. 
    	      mCamera.stopPreview(); 
    	      mCamera = null; 
    	  } 
    	  public void surfaceChanged(SurfaceHolder holder, int format, int w, 
    	int h) { 
    	      // Now that the size is known, set up the camera parameters and begin 
    	      // the preview. 
    	      Camera.Parameters parameters = mCamera.getParameters(); 
    	      parameters.setPreviewSize(w, h); 
    	      //parameters.setPreviewSize(320, 240);
    	      mCamera.setParameters(parameters); 
    	      mCamera.startPreview(); 
    	  } 
    	} 
}

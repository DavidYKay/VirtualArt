package com.virtualart.virtualart.render.image;

import static android.opengl.GLES10.GL_CLAMP_TO_EDGE;
import static android.opengl.GLES10.GL_DEPTH_TEST;
import static android.opengl.GLES10.GL_DITHER;
import static android.opengl.GLES10.GL_LINEAR;
import static android.opengl.GLES10.GL_MODULATE;
import static android.opengl.GLES10.GL_NEAREST;
import static android.opengl.GLES10.GL_REPLACE;
import static android.opengl.GLES10.GL_SMOOTH;
import static android.opengl.GLES10.GL_TEXTURE_2D;
import static android.opengl.GLES10.GL_TEXTURE_ENV;
import static android.opengl.GLES10.GL_TEXTURE_ENV_MODE;
import static android.opengl.GLES10.GL_TEXTURE_MAG_FILTER;
import static android.opengl.GLES10.GL_TEXTURE_MIN_FILTER;
import static android.opengl.GLES10.GL_TEXTURE_WRAP_S;
import static android.opengl.GLES10.GL_TEXTURE_WRAP_T;
import static android.opengl.GLES10.glBindTexture;
import static android.opengl.GLES10.glDisable;
import static android.opengl.GLES10.glEnable;
import static android.opengl.GLES10.glGenTextures;
import static android.opengl.GLES10.glShadeModel;
import static android.opengl.GLES10.glTexEnvf;
import static android.opengl.GLES10.glTexEnvx;
import static android.opengl.GLES10.glTexParameterf;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;

/**
 * This class will not be used.
 */
public class BlockRenderer implements GLSurfaceView.Renderer {

	private boolean mTranslucentBackground;
	private Context mContext;
    private int mTextureID;
	
	public BlockRenderer(Context ctx, boolean useTranslucentBackground) {
		mContext = ctx;
		mTranslucentBackground = useTranslucentBackground;
	}
	
	// new object variables we need
	// a raw buffer to hold indices
	private ShortBuffer _indexBuffer;
	 
	// a raw buffer to hold the vertices
	private FloatBuffer _vertexBuffer; 
	
	// a raw buffer to hold the colors
	private FloatBuffer _colorBuffer;
	
	private float[] coords = {
	        -0.5f, -0.5f, 0f, // (x1, y1, z1)
	        0.5f, -0.5f, 0f, // (x2, y2, z2)
	        0.5f, 0.5f, 0f, // (x3, y3, z3)
	        -0.5f, 0.5f, 0f // (x4, y4, z4)
	};
	    
	private short[] _indicesArray = {0, 1, 2, 0, 2, 3};
	
	private float colors[] = {
			1f, 0f, 0f, 1f,
			1f, 0f, 0f, 1f,
			1f, 0f, 0f, 1f,
			1f, 0f, 0f, 1f
    };
	
	
	private void initSquare() {
		
	    // float has 4 bytes
	    ByteBuffer vbb = ByteBuffer.allocateDirect(coords.length * 4);
	    vbb.order(ByteOrder.nativeOrder());
	    _vertexBuffer = vbb.asFloatBuffer();
	 
	    // short has 2 bytes
	    ByteBuffer ibb = ByteBuffer.allocateDirect(_indicesArray.length * 2);
	    ibb.order(ByteOrder.nativeOrder());
	    _indexBuffer = ibb.asShortBuffer();
	    
	    // color buffer
	    ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length*4);
        cbb.order(ByteOrder.nativeOrder());
        _colorBuffer = cbb.asFloatBuffer();
        
             
        // fill buffers
	    _vertexBuffer.put(coords);
	    _indexBuffer.put(_indicesArray);
	    _colorBuffer.put(colors);
	    
	 
	    // set positions
	    _vertexBuffer.position(0);
	    _indexBuffer.position(0);
	    _colorBuffer.position(0);
	}
	
	
	@Override
	public void onDrawFrame(GL10 gl) {
		/*
         * By default, OpenGL enables features that improve quality
         * but reduce performance. One might want to tweak that
         * especially on software renderer.
         */
        glDisable(GL_DITHER);

//        glTexEnvx(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE,
//                GL_MODULATE);
		
		/*
         * Usually, the first thing one might want to do is to clear
         * the screen. The most efficient way of doing this is to use
         * glClear().
         */
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
	
        // set the color of our element
        gl.glColor4f(0.5f, 0f, 0f, 1.0f);
     
        // define the coords we want to draw
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, _vertexBuffer);
         
        gl.glColorPointer(4, GL10.GL_FLOAT, 0, _colorBuffer);
        
        // finally draw the vertices
        gl.glDrawElements(GL10.GL_TRIANGLES, _indicesArray.length, GL10.GL_UNSIGNED_SHORT, _indexBuffer);
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int w, int h) {
		gl.glViewport(0, 0, w, h);
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        /*
         * Some one-time OpenGL initialization can be made here
         * probably based on features of this particular context
         */
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,
                 GL10.GL_FASTEST);
        
        // Set translucent background
        if (mTranslucentBackground) {
            gl.glClearColor(0,0,0,0);
        } else {
            gl.glClearColor(1,1,1,1);
        }
        glShadeModel(GL_SMOOTH);
        glEnable(GL_DEPTH_TEST);
//        glEnable(GL_TEXTURE_2D);
        
        // Enable pipeline function
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		initSquare();
		
		/*
         * Create our texture. This has to be done each time the
         * surface is created.
         */
		
//        int[] textures = new int[1];
//        glGenTextures(1, textures, 0);
//
//        mTextureID = textures[0];
//        glBindTexture(GL_TEXTURE_2D, mTextureID);
//
//        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER,
//                GL_NEAREST);
//        glTexParameterf(GL_TEXTURE_2D,
//                GL_TEXTURE_MAG_FILTER,
//                GL_LINEAR);
//
//        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S,
//                GL_CLAMP_TO_EDGE);
//        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T,
//                GL_CLAMP_TO_EDGE);
//
//        glTexEnvf(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE,
//                GL_REPLACE);
//
//        // FIXME: Image pulled from database
//        InputStream is = mContext.getResources()
//        					     .openRawResource(R.drawable.star_big_on);
//        Bitmap bitmap;
//        try {
//            bitmap = BitmapFactory.decodeStream(is);
//        } finally {
//            try {
//                is.close();
//            } catch(IOException e) {
//                // Ignore.
//            }
//        }
//
//        GLUtils.texImage2D(GL_TEXTURE_2D, 0, bitmap, 0);
//        bitmap.recycle(); 
	}
}

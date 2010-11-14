/*
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.virtualart.virtualart.render.image;

import static android.opengl.GLES10.GL_BLEND;
import static android.opengl.GLES10.GL_CCW;
import static android.opengl.GLES10.GL_CLAMP_TO_EDGE;
import static android.opengl.GLES10.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES10.GL_DEPTH_BUFFER_BIT;
import static android.opengl.GLES10.GL_DEPTH_TEST;
import static android.opengl.GLES10.GL_DITHER;
import static android.opengl.GLES10.GL_FASTEST;
import static android.opengl.GLES10.GL_FLOAT;
import static android.opengl.GLES10.GL_LINEAR;
import static android.opengl.GLES10.GL_MODELVIEW;
import static android.opengl.GLES10.GL_MODULATE;
import static android.opengl.GLES10.GL_ONE_MINUS_SRC_ALPHA;
import static android.opengl.GLES10.GL_PERSPECTIVE_CORRECTION_HINT;
import static android.opengl.GLES10.GL_PROJECTION;
import static android.opengl.GLES10.GL_REPEAT;
import static android.opengl.GLES10.GL_REPLACE;
import static android.opengl.GLES10.GL_SMOOTH;
import static android.opengl.GLES10.GL_SRC_ALPHA;
import static android.opengl.GLES10.GL_TEXTURE0;
import static android.opengl.GLES10.GL_TEXTURE_2D;
import static android.opengl.GLES10.GL_TEXTURE_COORD_ARRAY;
import static android.opengl.GLES10.GL_TEXTURE_ENV;
import static android.opengl.GLES10.GL_TEXTURE_ENV_MODE;
import static android.opengl.GLES10.GL_TEXTURE_MAG_FILTER;
import static android.opengl.GLES10.GL_TEXTURE_MIN_FILTER;
import static android.opengl.GLES10.GL_TEXTURE_WRAP_S;
import static android.opengl.GLES10.GL_TEXTURE_WRAP_T;
import static android.opengl.GLES10.GL_TRIANGLE_STRIP;
import static android.opengl.GLES10.GL_UNSIGNED_SHORT;
import static android.opengl.GLES10.GL_VERTEX_ARRAY;
import static android.opengl.GLES10.glActiveTexture;
import static android.opengl.GLES10.glBindTexture;
import static android.opengl.GLES10.glBlendFunc;
import static android.opengl.GLES10.glClear;
import static android.opengl.GLES10.glClearColor;
import static android.opengl.GLES10.glDisable;
import static android.opengl.GLES10.glDisableClientState;
import static android.opengl.GLES10.glDrawArrays;
import static android.opengl.GLES10.glDrawElements;
import static android.opengl.GLES10.glEnable;
import static android.opengl.GLES10.glEnableClientState;
import static android.opengl.GLES10.glFrontFace;
import static android.opengl.GLES10.glFrustumf;
import static android.opengl.GLES10.glGenTextures;
import static android.opengl.GLES10.glHint;
import static android.opengl.GLES10.glLoadIdentity;
import static android.opengl.GLES10.glMatrixMode;
import static android.opengl.GLES10.glShadeModel;
import static android.opengl.GLES10.glTexCoordPointer;
import static android.opengl.GLES10.glTexEnvf;
import static android.opengl.GLES10.glTexEnvx;
import static android.opengl.GLES10.glTexParameterf;
import static android.opengl.GLES10.glTexParameterx;
import static android.opengl.GLES10.glVertexPointer;
import static android.opengl.GLES10.glViewport;

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
import android.opengl.GLU;
import android.opengl.GLUtils;

/**
 * Renders a block with a textured image.
 */
public class ImageRenderer implements GLSurfaceView.Renderer {

    private Context mContext;
    private Square mSquare;
    private int mTextureID;
    
    
    public ImageRenderer(Context context) {
        mContext = context;
        mSquare = new Square();
    }

    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        /*
         * By default, OpenGL enables features that improve quality
         * but reduce performance. One might want to tweak that
         * especially on software renderer.
         */
        glDisable(GL_DITHER);

        /*
         * Some one-time OpenGL initialization can be made here
         * probably based on features of this particular context
         */
        glHint(GL_PERSPECTIVE_CORRECTION_HINT,
                GL_FASTEST);

        glClearColor(0,0,0,0);	  // set the background color to clear so we can see the camera view
        glShadeModel(GL_SMOOTH);
        glEnable(GL_DEPTH_TEST);  // does depth comparisons and updates the buffer
        glEnable(GL_TEXTURE_2D);  // enables 2D texture
        glEnable(GL_BLEND);		  // enables blending
        
        //TODO: specify a blending function
//        glBlendFunc();

        /*
         * Create our texture. This has to be done each time the
         * surface is created.
         */
        int[] textures = new int[1];
        glGenTextures(1, textures, 0); 							// generates texture names

        mTextureID = textures[0];								// gets the texture name
        glBindTexture(GL_TEXTURE_2D, mTextureID);

        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER,
                GL_LINEAR);
        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER,
                GL_LINEAR);

        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S,
                GL_CLAMP_TO_EDGE);
        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T,
                GL_CLAMP_TO_EDGE);

        glTexEnvf(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE,
                GL_REPLACE);

        InputStream is = mContext.getResources()
                .openRawResource(R.drawable.star_big_on);
        Bitmap bitmap;
        try {
            bitmap = BitmapFactory.decodeStream(is);
        } finally {
            try {
                is.close();
            } catch(IOException e) {
                // Ignore.
            }
        }

        //THE BIG CALL!
        GLUtils.texImage2D(GL_TEXTURE_2D, 0, bitmap, 0);
        bitmap.recycle();
    }
    
    public void onDrawFrame(GL10 gl) {
        newOnDrawFrame(gl);
    }

    /**
     * Based on Ben Newhouse's AR Slides
     */
    public void newOnDrawFrame(GL10 gl) {
        float triangleArray[] = {
            1.0f  , -1.0f , -3.0f , 
            -1.0f , -1.0f , -3.0f , 
            1.0f  , 1.0f  , -3.0f , 
            -1.0f , 1.0f  , -3.0f
        };

        float coordinatesArray[] = {  
            1,1,  
            0,1,  
            1,0,  
            0,0  
        };

        FloatBuffer triangle = createBufferFromArray(triangleArray);

        FloatBuffer coordinates = createBufferFromArray(coordinatesArray);

        glTexParameterx(GL_TEXTURE_2D , GL_TEXTURE_MIN_FILTER , GL_LINEAR);
        glTexParameterx(GL_TEXTURE_2D , GL_TEXTURE_MAG_FILTER , GL_LINEAR);
        glTexParameterx(GL_TEXTURE_2D , GL_TEXTURE_WRAP_S     , GL_REPEAT);
        glTexParameterx(GL_TEXTURE_2D , GL_TEXTURE_WRAP_T     , GL_REPEAT);

        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_BLEND);

        //for (int ndx = 0; ndx < 4; ndx++) {
        for (int ndx = 0; ndx < 1; ndx++) {

            glLoadIdentity();
            //glMultMatrix(self.orientation);

            glEnableClientState(GL_TEXTURE_COORD_ARRAY);
            glEnableClientState(GL_VERTEX_ARRAY);
            glEnable(GL_TEXTURE_2D);

            //glBindTexture(GL_TEXTURE_2D, textures[ndx]);
            glBindTexture(GL_TEXTURE_2D, mTextureID);
            glVertexPointer(3, GL_FLOAT, 0, triangle);
            glTexCoordPointer(2, GL_FLOAT, 0, coordinates);
            glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);

        } 
    }

    public void oldOnDrawFrame(GL10 gl) {
        /*
         * By default, OpenGL enables features that improve quality
         * but reduce performance. One might want to tweak that
         * especially on software renderer.
         */
        glDisable(GL_DITHER);

        glTexEnvx(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE,
                GL_MODULATE);

        /*
         * Usually, the first thing one might want to do is to clear
         * the screen. The most efficient way of doing this is to use
         * glClear().
         */

        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        
        /*
         * Now we're ready to draw some 3D objects
         */
        // FIXME: need to replace this
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();

        GLU.gluLookAt(gl, 0, 0, -5, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        glEnableClientState(GL_VERTEX_ARRAY);
        glEnableClientState(GL_TEXTURE_COORD_ARRAY);

        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, mTextureID);
        glTexParameterx(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S,
                GL_REPEAT);
        glTexParameterx(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T,
                GL_REPEAT);

//        long time = SystemClock.uptimeMillis() % 4000L;
//        float angle = 0.090f * ((int) time);

//        glRotatef(angle, 0, 0, 1.0f);

        mSquare.draw(gl);
        
        // To avoid messing up the rendering of multiple objects, disable GL_VERTEX_ARRAY
        glDisableClientState(GL_VERTEX_ARRAY);
    }

    public void onSurfaceChanged(GL10 gl, int w, int h) {
        glViewport(0, 0, w, h);

        /*
        * Set our projection matrix. This doesn't have to be done
        * each time we draw, but usually a new projection needs to
        * be set when the viewport is resized.
        */

        float ratio = (float) w / h;
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glFrustumf(-ratio, ratio, -1, 1, 3, 7);
    }
   
    
    static class Square {

        private final static int VERTS = 4;

        private FloatBuffer mFVertexBuffer;
        private FloatBuffer mTexBuffer;
        private ShortBuffer mIndexBuffer;
        
        private float[] coords = {
        		-0.75f, -0.75f, 0f, // (x1, y1, z1)
        		0.75f, -0.75f, 0f, // (x2, y2, z2)
        		0.75f, 0.75f, 0f, // (x3, y3, z3)
    	        -0.75f, 0.75f, 0f // (x4, y4, z4)
        };
        
        private int[] indices = {0, 1, 2, 0, 2, 3};
        
        public Square() {        	
            // Buffers to be passed to gl*Pointer() functions
            // must be direct, i.e., they must be placed on the
            // native heap where the garbage collector cannot
            // move them.
            //
            // Buffers with multi-byte datatypes (e.g., short, int, float)
            // must have their byte order set to native order

            ByteBuffer vbb = ByteBuffer.allocateDirect(coords.length * 4);
            vbb.order(ByteOrder.nativeOrder());
            mFVertexBuffer = vbb.asFloatBuffer();

            ByteBuffer tbb = ByteBuffer.allocateDirect(VERTS * 2 * 4);
            tbb.order(ByteOrder.nativeOrder());
            mTexBuffer = tbb.asFloatBuffer();

            ByteBuffer ibb = ByteBuffer.allocateDirect(indices.length * 2);
            ibb.order(ByteOrder.nativeOrder());
            mIndexBuffer = ibb.asShortBuffer();
            
            mFVertexBuffer.put(coords);

            for (int i = 0; i < VERTS; i++) {
                for(int j = 0; j < 2; j++) {
                    mTexBuffer.put(coords[i*3+j] * 2.0f + 0.5f);
                }
            }

            for(int i = 0; i < VERTS; i++) {
                mIndexBuffer.put((short) i);
            }

            mFVertexBuffer.position(0);
            mTexBuffer.position(0);
            mIndexBuffer.position(0);
        }

        public void draw(GL10 gl) {
            glFrontFace(GL_CCW);
            glVertexPointer(3, GL_FLOAT, 0, mFVertexBuffer);
            glEnable(GL_TEXTURE_2D);
            glTexCoordPointer(2, GL_FLOAT, 0, mTexBuffer);
            glDrawElements(GL10.GL_TRIANGLE_STRIP, indices.length,
                    GL_UNSIGNED_SHORT, mIndexBuffer);
        }
    }

    public FloatBuffer createBufferFromArray(float[] array) {
        ByteBuffer vbb = ByteBuffer.allocateDirect(
            array.length * 4
        );
        vbb.order(ByteOrder.nativeOrder());
        return vbb.asFloatBuffer();
    }
}

package com.virtualart.virtualart;

import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import com.virtualart.virtualart.render.Matrix4f;
import com.virtualart.virtualart.render.cubes.Cube;
import com.virtualart.virtualart.util.SensorHelper;
import com.virtualart.virtualart.util.SensorHelper.SensorHelperListener;

/**
 * Activity for testing the behavior of accelerometer/compass/GPS
 *
 */
public class CubeActivity extends Activity {

  // Model
  private SensorHelper mSensorHelper;

  // UI
  private GLSurfaceView mGLSurfaceView;

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // Create our Preview view and set it as the content of our
    // Activity
    mGLSurfaceView = new TouchSurfaceView(this);
    setContentView(mGLSurfaceView);
    mGLSurfaceView.requestFocus();
    mGLSurfaceView.setFocusableInTouchMode(true);

    // Listen for sensor updates
    mSensorHelper = new SensorHelper(this);
    mSensorHelper.addListener((SensorHelperListener) mGLSurfaceView);
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
}

/**
 * Implement a simple rotation control.
 *
 */
class TouchSurfaceView extends GLSurfaceView implements SensorHelperListener {

  private static final String TAG = "TouchSurfaceView";
  public TouchSurfaceView(Context context) {
    super(context);
    mRenderer = new CubeRenderer();
    setRenderer(mRenderer);
    setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
  }

  @Override public boolean onTrackballEvent(MotionEvent e) {
    mRenderer.mAngleX += e.getX() * TRACKBALL_SCALE_FACTOR;
    mRenderer.mAngleY += e.getY() * TRACKBALL_SCALE_FACTOR;
    requestRender();
    return true;
  }

  @Override public boolean onTouchEvent(MotionEvent e) {
    float x = e.getX();
    float y = e.getY();
    switch (e.getAction()) {
      case MotionEvent.ACTION_MOVE:
        float dx = x - mPreviousX;
        float dy = y - mPreviousY;
        mRenderer.mAngleX += dx * TOUCH_SCALE_FACTOR;
        mRenderer.mAngleY += dy * TOUCH_SCALE_FACTOR;
        requestRender();
    }
    mPreviousX = x;
    mPreviousY = y;
    return true;
  }

  public void SensorHelperDidUpdate(SensorHelper sensorHelper) {
    float[] accelerometerValues = sensorHelper.getAccelerometerValues();
    float[] compassValues = sensorHelper.getCompassValues();

    //mAccelXView.setText(String.format("%.1f", accelerometerValues[0]));
    //mAccelYView.setText(String.format("%.1f", accelerometerValues[1]));
    //mAccelZView.setText(String.format("%.1f", accelerometerValues[2]));

    //mCompassXView.setText(String.format("%.1f", compassValues[0]));
    //mCompassYView.setText(String.format("%.1f", compassValues[1]));
    //mCompassZView.setText(String.format("%.1f", compassValues[2]));

    //mPitchView.setText(
    //    createDebugMatrix(
    //        sensorHelper.getRotationMatrix()).toString());

    // Pass matrix into cube renderer.
    //Matrix rawMatrix = MatrixFactory.createMatrixFromArray(sensorHelper.getRotationMatrix());
    Matrix4f rawMatrix = new Matrix4f(sensorHelper.getRotationMatrix());
    //Matrix4f realMatrix = rawMatrix.invert();
    Matrix4f realMatrix = rawMatrix.transpose();

    Log.v(TAG, "New Rotation Matrix: " + realMatrix);
    mRenderer.setRotationMatrix(realMatrix);
    requestRender();
  }

  /**
   * Render a cube.
   */
  private class CubeRenderer implements GLSurfaceView.Renderer {
    private Cube mCube;
    public float mAngleX;
    public float mAngleY;
    private Matrix4f mRotationMatrix;

    public CubeRenderer() {
      mCube = new Cube();
      mRotationMatrix = new Matrix4f();
    }

    public void setRotationMatrix(Matrix4f matrix) {
      mRotationMatrix = matrix;
    }

    public Matrix4f getRotationMatrix() {
      return mRotationMatrix;
    }

    public void onDrawFrame(GL10 gl) {
      /*
       * Usually, the first thing one might want to do is to clear
       * the screen. The most efficient way of doing this is to use
       * glClear().
       */

      gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

      /*
       * Now we're ready to draw some 3D objects
       */

      gl.glMatrixMode(GL10.GL_MODELVIEW);
      gl.glLoadIdentity();

      // Load our rotation matrix
      gl.glLoadMatrixf(
          FloatBuffer.wrap(mRotationMatrix.getArray())
          );

      gl.glTranslatef(0, 0, -3.0f);

      gl.glRotatef(mAngleX, 0, 1, 0);
      gl.glRotatef(mAngleY, 1, 0, 0);

      gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
      gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

      mCube.draw(gl);
    }

    public void onSurfaceChanged(GL10 gl, int width, int height) {
      gl.glViewport(0, 0, width, height);

      /*
       * Set our projection matrix. This doesn't have to be done
       * each time we draw, but usually a new projection needs to
       * be set when the viewport is resized.
       */

      float ratio = (float) width / height;
      gl.glMatrixMode(GL10.GL_PROJECTION);
      gl.glLoadIdentity();
      gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);
    }

    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
      /*
       * By default, OpenGL enables features that improve quality
       * but reduce performance. One might want to tweak that
       * especially on software renderer.
       */
      gl.glDisable(GL10.GL_DITHER);

      /*
       * Some one-time OpenGL initialization can be made here
       * probably based on features of this particular context
       */
      gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,
                GL10.GL_FASTEST);


      gl.glClearColor(1,1,1,1);
      gl.glEnable(GL10.GL_CULL_FACE);
      gl.glShadeModel(GL10.GL_SMOOTH);
      gl.glEnable(GL10.GL_DEPTH_TEST);
    }
  }

  private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
  private final float TRACKBALL_SCALE_FACTOR = 36.0f;
  private CubeRenderer mRenderer;
  private float mPreviousX;
  private float mPreviousY;
}

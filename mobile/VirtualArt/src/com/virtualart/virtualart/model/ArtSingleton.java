package com.virtualart.virtualart.model;

import android.content.Context;

import com.virtualart.virtualart.util.SensorHelper;
import com.wilson.android.library.DrawableManager;

/**
 * Abstraction layer that UI deals with
 */
public class ArtSingleton {
	private ArtModel artModel;
	private ArtUpdateManager updateManager;
	private DrawableManager drawableManager;
	private SensorHelper sensorHelper;

	// Private constructor prevents instantiation from other classes
	private ArtSingleton(Context context) {
		sensorHelper = new SensorHelper(context);
		artModel = new ArtModel(sensorHelper);
		
		updateManager = new ArtUpdateManager(artModel);
		drawableManager = new DrawableManager();
		
		updateManager.addListener(artModel);
	}

	/**
	 * SingletonHolder is loaded on the first execution of Singleton.getInstance() 
	 * or the first access to SingletonHolder.INSTANCE, not before.
	 */
	private static class SingletonHolder { 
		static ArtSingleton INSTANCE = null;
	}
	
    /**
     * We need to init it first. Ugly, I know
     */
	public static void initSingleton(Context context) {
		SingletonHolder.INSTANCE = new ArtSingleton(context);
	}

	public static ArtSingleton getInstance() {
		return SingletonHolder.INSTANCE;
	}

	public void setArtModel(ArtModel artModel) {
		this.artModel = artModel;
	}

	public ArtModel getArtModel() {
		return artModel;
	}

	public void setUpdateManager(ArtUpdateManager updateManager) {
		this.updateManager = updateManager;
	}

	public ArtUpdateManager getUpdateManager() {
		return updateManager;
	}

	public void setDrawableManager(DrawableManager drawableManager) {
		this.drawableManager = drawableManager;
	}

	public DrawableManager getDrawableManager() {
		return drawableManager;
	}

	public void setSensorHelper(SensorHelper sensorHelper) {
		this.sensorHelper = sensorHelper;
	}

	public SensorHelper getSensorHelper() {
		return sensorHelper;
	}

}

package com.virtualart.virtualart.model;

/**
 * Abstraction layer that UI deals with
 */
public class ArtSingleton {
	private ArtModel artModel;
	private UpdateManager updateManager;

	// Private constructor prevents instantiation from other classes
	private ArtSingleton() {
	}

	/**
	 * SingletonHolder is loaded on the first execution of Singleton.getInstance() 
	 * or the first access to SingletonHolder.INSTANCE, not before.
	 */
	private static class SingletonHolder { 
		public static final ArtSingleton INSTANCE = new ArtSingleton();
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

	public void setUpdateManager(UpdateManager updateManager) {
		this.updateManager = updateManager;
	}

	public UpdateManager getUpdateManager() {
		return updateManager;
	}

}
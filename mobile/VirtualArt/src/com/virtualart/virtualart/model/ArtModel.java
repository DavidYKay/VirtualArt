package com.virtualart.virtualart.model;

import java.util.ArrayList;
import java.util.EventListener;

import android.content.Context;
import android.location.Location;

import com.virtualart.virtualart.model.ArtUpdateManager.UpdateManagerListener;
import com.virtualart.virtualart.util.EventListenerList;
import com.virtualart.virtualart.util.SensorHelper;

/**
 * Responsible for maintaining the local set of art
 *
 */
public class ArtModel implements UpdateManagerListener {
    public interface ArtModelListener extends EventListener {
        public void artModelAddedItem(ArtModel model, ArtItem item);
        public void artModelClearedItems(ArtModel model);
        public void artModelUpdatedPosition(ArtModel model);
    }

    public enum Action {
        ADD,
        CLEAR
    }

    //HELPERS 
    private ArtUpdateManager updateManager = new ArtUpdateManager(this);

    //STATE VARIABLES
	private ArrayList<ArtItem> artItems = new ArrayList<ArtItem>();
	private Location currentLocation;
	private float[] rotationMatrix;

	private SensorHelper sensorHelper;

    /**
     * 
     */
    public ArtModel(SensorHelper helper) {
    	this.sensorHelper = helper;               
    }
	
	public ArrayList<ArtItem> getArtItems() {
		return artItems;
	}
	public Location getCurrentLocation() {
		return currentLocation;
	}
	public float[] getRotationMatrix() {
//        return null;
		return sensorHelper.getRotationMatrix();
	}

    public void addItem(ArtItem item) {
    	artItems.add(item);
        notifyListenersOfAdd(item);
    }
    
    public void clearItems() {

        artItems.clear();

        notifyListenersOfClear();
    }

	/**
	 * Dial up the foreign API, ask for new stuff
	 */
	public void getNewArt() {
		//Fetch new content descriptor
        updateManager.getNewArt();
		//Fire an event when we're done
	}

	private EventListenerList mEventListenerList = new EventListenerList();

	public void addListener(ArtModelListener listener) {
		mEventListenerList.add(
			ArtModelListener.class,
			listener
		);
	}
	
	public void removeListener(ArtModelListener listener) {
		mEventListenerList.remove(
			ArtModelListener.class,
			listener
		);
	}

	private void notifyListenersOfAdd(ArtItem newItem) {
        for (ArtModelListener listener : mEventListenerList.getListeners(ArtModelListener.class)) {
            listener.artModelAddedItem(this, newItem);
        }
    }

	private void notifyListenersOfClear() {
        for (ArtModelListener listener : mEventListenerList.getListeners(ArtModelListener.class)) {
            listener.artModelClearedItems(this);
        }
    }
	
    private void notifyListenersOfPositionUpdate() {
        for (ArtModelListener listener : mEventListenerList.getListeners(ArtModelListener.class)) {
            listener.artModelUpdatedPosition(this);
        }
    }

	@Override
	 public void newArtFinishedUpdating(ArrayList<ArtItem> newItems) {
        //Out with the old
        clearItems();

        //In with the new
        for (ArtItem newItem : newItems) {
            addItem(newItem);
        }
	}

}

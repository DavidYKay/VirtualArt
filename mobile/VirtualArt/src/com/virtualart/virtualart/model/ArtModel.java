package com.virtualart.virtualart.model;

import java.util.ArrayList;
import java.util.EventListener;

import android.location.Location;

import com.virtualart.virtualart.model.UpdateManager.UpdateManagerListener;
import com.virtualart.virtualart.util.EventListenerList;

/**
 * Responsible for maintaining the local set of art
 *
 */
public class ArtModel implements UpdateManagerListener {
    public interface ArtModelListener extends EventListener {
        public void artModelAddedItem(ArtModel model, ArtItem item);
        public void artModelClearedItems(ArtModel model);
    }

    public enum Action {
        ADD,
        CLEAR
    }

    private UpdateManager updateManager = new UpdateManager(this);

	private ArrayList<ArtItem> artItems = new ArrayList<ArtItem>();
	private Location currentLocation;
	
	public ArrayList<ArtItem> getArtItems() {
		return artItems;
	}
	public Location getCurrentLocation() {
		return currentLocation;
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

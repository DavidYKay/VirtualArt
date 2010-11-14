package com.virtualart.virtualart.model;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.EventListener;

import org.json.JSONArray;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.virtualart.virtualart.model.ArtItem.APIConstants;
import com.virtualart.virtualart.util.EventListenerList;
import com.virtualart.virtualart.util.FileHelper;
import com.virtualart.virtualart.util.RestClient;
import com.wilson.android.library.DrawableManager;

public class ArtUpdateManager extends FetchManager {
    public static final int ELEVATION = 1;

    public interface UpdateManagerListener extends EventListener {
        public void newArtFinishedUpdating(ArrayList<ArtItem> newItems);
    }

    //private UpdateManagerListener updateManagerListener;
    //public UpdateManager(UpdateManagerListener updateManagerListener) {
    //    this.updateManagerListener = updateManagerListener;
    //}
	
	//Ugly. This should be removed
    private ArtModel artModel;
    public ArtUpdateManager(ArtModel artModel) {
        this.artModel = artModel;
    }

	/**
	 * Dial up the foreign API, ask for new stuff
	 */
	public void getNewArt() {
		//Fetch new content descriptor
		String fetchUrl = "http://davidykay.com:8080/virtualart/art";

		RestClient restClient = new RestClient();
        DrawableManager drawableManager = restClient.getDrawableManager();
		JSONArray array = restClient.fetchJson(fetchUrl);
	
		ArrayList<ArtItem> newItems = new ArrayList<ArtItem>();
        /**
         * Do we want to do this sequentially or parallel?
         */
		for (int i = 0; i < array.length(); i++) {
			
			//"direction": 3.0, 
			//"elevation": 5.0, 
			//"name": "test", 
			//"image": "imgs/2010/11/13/hdr_logo.gif", 
			//"longitude": 1.0, 
			//"pitch": 4.0, 
			//"latitude": 2.0
			try {
				JSONObject object = array.getJSONObject(i);

                //Fetch drawable
				String imageName = object.getString(APIConstants.Image);
				String name = object.getString(APIConstants.Name);
				double lat = object.getDouble(APIConstants.Latitude);
				double lng = object.getDouble(APIConstants.Longitude);
				int elevation = ELEVATION;
				int artId = object.getInt(APIConstants.Longitude);

                Drawable drawable = drawableManager.fetchDrawable(imageName);

				//Save to disk
				FileHelper.saveDrawableById(
					drawable,
					artId
				);

				//Save to model
				newItems.add(
                    new ArtItem(
                        name,
                        drawable,
                        lat,
                        lng,
                        elevation
                    )
				);
                //artModel.addItem(
                //);
			} catch (Exception e) {
				
			}
		}
		
		//For each item, fire up a fetchDrawable on a background thread

		//Fire an event when we're done
		notifyListeners(
			newItems
		);
	}

    public Bitmap fetchBitmap(String urlString) {
    	Log.d(this.getClass().getSimpleName(), "image url:" + urlString);
    	try {
    		InputStream is = fetch(urlString);
    		//BitmapDrawable drawable = BitmapDrawable.createFromStream(is, "src");
    		BitmapDrawable drawable = new BitmapDrawable(is);
    		return drawable.getBitmap();
    	} catch (MalformedURLException e) {
    		Log.e(this.getClass().getSimpleName(), "fetchBitmap failed", e);
    		return null;
    	} catch (IOException e) {
    		Log.e(this.getClass().getSimpleName(), "fetchBitmap failed", e);
    		return null;
    	} catch (NullPointerException e) {
    		Log.e(this.getClass().getSimpleName(), "Null image. Bad url?", e);
    		return null;
    	}    	
    }

	/**
     * One background item finished retrieving
     */
    public void backgroundFetchFinished() {
        //Increment counter

        boolean done = true;
        if (done) {
        	ArrayList<ArtItem> newItems = new ArrayList<ArtItem>();
            //TODO: Add the new junk

            //We're done. Let the model know.
            artModel.newArtFinishedUpdating(newItems);
        }
    }

    ///**
    // * WTF is this? Return value seems cumbersome.
    // */
	//@Override
	//public Drawable handleResponse(HttpResponse response)
	//		throws ClientProtocolException, IOException {
    //    //When we get the drawable back
	//	return null;
	//}
	
	private EventListenerList mEventListenerList = new EventListenerList();

	public void addListener(UpdateManagerListener listener) {
		mEventListenerList.add(
			UpdateManagerListener.class,
			listener
		);
	}
	
	public void removeListener(UpdateManagerListener listener) {
		mEventListenerList.remove(
			UpdateManagerListener.class,
			listener
		);
	}

	private void notifyListeners( ArrayList<ArtItem> newItems) {
        for (UpdateManagerListener listener : mEventListenerList.getListeners(UpdateManagerListener.class)) {
            listener.newArtFinishedUpdating(newItems);
        }
    }
}

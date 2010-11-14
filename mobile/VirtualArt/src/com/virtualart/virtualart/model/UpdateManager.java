package com.virtualart.virtualart.model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.graphics.drawable.Drawable;

import com.virtualart.virtualart.model.ArtItem.APIConstants;
import com.virtualart.virtualart.util.RestClient;
import com.wilson.android.library.DrawableManager;

public class UpdateManager {
    public static final int ELEVATION = 1;

    public interface UpdateManagerListener {
        public void newArtFinishedUpdating(ArrayList<ArtItem> newItems);
    }

    //private UpdateManagerListener updateManagerListener;
    //public UpdateManager(UpdateManagerListener updateManagerListener) {
    //    this.updateManagerListener = updateManagerListener;
    //}

    private ArtModel artModel;
    public UpdateManager(ArtModel artModel) {
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

                Drawable drawable = drawableManager.fetchDrawable(imageName);
                
                artModel.addItem(
                    new ArtItem(
                        object.getString(APIConstants.Name),
                        drawable,
                        object.getDouble(APIConstants.Latitude),
                        object.getDouble(APIConstants.Longitude),
                        ELEVATION
                    )
                );
                //Save the item to disk?
			} catch (Exception e) {
				
			}
		}
		
		//For each item, fire up a fetchDrawable on a background thread

		//Fire an event when we're done
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

}
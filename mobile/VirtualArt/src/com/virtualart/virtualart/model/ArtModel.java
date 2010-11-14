package com.virtualart.virtualart.model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.location.Location;

import com.virtualart.virtualart.model.ArtItem.APIConstants;
import com.virtualart.virtualart.util.RestClient;

/**
 * Responsible for maintaining the local set of art
 *
 */
public class ArtModel {
	private ArrayList<ArtItem> artItems = new ArrayList<ArtItem>();
	private Location currentLocation;
	
	public ArrayList<ArtItem> getArtItems() {
		return artItems;
	}
	public Location getCurrentLocation() {
		return currentLocation;
	}

	/**
	 * Called when it's time to migrate to the new art
	 */
	public void newArtFinishedUpdating() {
		
	}
	
	/**
	 * Dial up the foreign API, ask for new stuff
	 */
	private void getNewArt() {
		//Fetch new content descriptor
		String fetchUrl = "http://davidykay.com:8080/virtualart/art";

		RestClient restClient = new RestClient();
		JSONArray array = restClient.fetchJson(fetchUrl);
	
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
				
				object.getString(APIConstants.Name);
				object.getString(APIConstants.Image);
				object.getDouble(APIConstants.Latitude);
				object.getDouble(APIConstants.Longitude);
					
			} catch (Exception e) {
				
			}
		
		}
		
		//For each item, fire up a fetchDrawable on a background thread

		//Fire an event when we're done
	}



}

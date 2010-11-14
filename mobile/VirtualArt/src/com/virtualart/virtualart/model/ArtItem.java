package com.virtualart.virtualart.model;

import android.graphics.drawable.Drawable;

/**
 * Represents one geo-tagged piece of art
 *
 */
public class ArtItem {
	public abstract class APIConstants {
		//public static final String Direction = "direction";
		public static final String Name      = "name";
		public static final String Longitude = "longitude";
		public static final String Latitude  = "latitude";
		public static final String Elevation = "elevation";
		public static final String Image     = "image";
	}
	private String name;
	private Drawable image;
	private long longitude;
	private long latitude;
	private long elevation;
	
	public ArtItem(String name, Drawable image, long longitude, long latitude,
			long elevation) {
		super();
		this.name = name;
		this.image = image;
		this.longitude = longitude;
		this.latitude = latitude;
		this.elevation = elevation;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Drawable getImage() {
		return image;
	}
	public void setImage(Drawable image) {
		this.image = image;
	}
	public long getLongitude() {
		return longitude;
	}
	public void setLongitude(long longitude) {
		this.longitude = longitude;
	}
	public long getLatitude() {
		return latitude;
	}
	public void setLatitude(long latitude) {
		this.latitude = latitude;
	}
	public long getElevation() {
		return elevation;
	}
	public void setElevation(long elevation) {
		this.elevation = elevation;
	}

	//Add saveToDB Method?
	
	
}

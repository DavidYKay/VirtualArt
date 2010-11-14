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
		public static final String Latitude  = "latitude";
		public static final String Longitude = "longitude";
		public static final String Elevation = "elevation";
		public static final String Image     = "image";
	}
	private String name;
	private Drawable image;
	private double latitude;
	private double longitude;
	private double elevation;
	
	public ArtItem(String name, Drawable image, double latitude, double doubleitude, double elevation) {
		super();
		this.name = name;
		this.image = image;
		this.longitude = doubleitude;
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
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(long longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getElevation() {
		return elevation;
	}
	public void setElevation(double elevation) {
		this.elevation = elevation;
	}

	//Add saveToDB Method?
	
	
}

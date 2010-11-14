package com.virtualart.virtualart.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.wilson.android.library.DrawableManager;

/**
 * Class for making JSON calls over REST
 */
public class RestClient {

	private DefaultHttpClient mHttpClient = new DefaultHttpClient();
	private DrawableManager mDrawableManager = new DrawableManager();

	public RestClient() {
	}

	public DrawableManager getDrawableManager() {
		return mDrawableManager;
	}

	public static String convertStreamToString(InputStream is) {
		/*
		 * To convert the InputStream to String we use the BufferedReader.readLine()
		 * method. We iterate until the BufferedReader returns null which means
		 * there's no more data to read. Each line will appended to a StringBuilder
		 * and returned as String.
		 */
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

    /**
     * Utility method to parse JSON
     */
	public JSONArray jsonArrayFromString(String contentString) {
		JSONArray valArray = null;

		try {
			Log.i("REST", contentString);

			// A Simple JSONObject Creation
			JSONObject json = new JSONObject(contentString);
			Log.i("REST","<jsonobject>\n"+json.toString()+"\n</jsonobject>");

			// A Simple JSONObject Parsing
			JSONArray nameArray = json.names();
			valArray  = json.toJSONArray(nameArray);
			for(int i=0; i<valArray.length(); i++) {
				Log.i("REST","<jsonname"+i+">\n"+nameArray.getString(i)+"\n</jsonname"+i+">\n"
						+"<jsonvalue"+i+">\n"+valArray.getString(i)+"\n</jsonvalue"+i+">");
			}

			// A Simple JSONObject Value Pushing
			json.put("sample key", "sample value");
			Log.i("REST","<jsonobject>\n"+json.toString()+"\n</jsonobject>");
		} catch (JSONException e) {
			e.printStackTrace();
		}

        return valArray;
    }

	public String fetchString(String url) {
		// TODO Grab actual credentials
		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("unregistered", "abc");
		mHttpClient.getCredentialsProvider().setCredentials(AuthScope.ANY, credentials);

		// Prepare a request object
		HttpGet httpget = new HttpGet(url);

		// Execute the request
		HttpResponse response; 
		String responseString = null;

		InputStream inStream  = null;
		try {
			//This is a synchronous call
			response = mHttpClient.execute(httpget);
			//This is an asynchronous call
			//            response = httpclient.execute(httpget, responseHandler);
			// Examine the response status
			Log.i("REST",response.getStatusLine().toString());

			// Get hold of the response entity
			HttpEntity entity = response.getEntity();

			if (entity != null) {
				// A Simple JSON Response Read
				inStream = entity.getContent();
				String result = convertStreamToString(inStream);

				responseString = result;           
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();        
		} finally {
			try {
				inStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return responseString;
	}

	/* 
	 * Shortcut method for fetching json from a URL
	 */
	public JSONArray fetchJson(String url) {
		String response = fetchString(url);
        
		return jsonArrayFromString(response);
	}

	/**
	 * Trim the result to the first curly brace
	 * @param result
	 * @return trimmedResult
	 */
	@SuppressWarnings("unused")
	private String trimResult(String result) {		
		return result.replaceFirst(".*[{]", "{");
	}
}

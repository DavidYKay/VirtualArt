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

import android.graphics.drawable.Drawable;
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
 
    /* This is a test function which will connects to a given rest service 
     * and returns a JSON array decoded from the response.
     */
    public JSONArray fetchJson(String url) {
      
        // TODO Grab actual credentials
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("unregistered", "abc");
        mHttpClient.getCredentialsProvider().setCredentials(AuthScope.ANY, credentials);
 
        // Prepare a request object
        HttpGet httpget = new HttpGet(url);
        
        // Execute the request
        HttpResponse response; 
        JSONArray valArray  = null;
        
        try {
        	//This is a synchronous call
            response = mHttpClient.execute(httpget);
        	//This is an asynchronous call
//            response = httpclient.execute(httpget, responseHandler);
            // Examine the response status
            Log.i("REST",response.getStatusLine().toString());
 
            // Get hold of the response entity
            HttpEntity entity = response.getEntity();
            
            // If the response does not enclose an entity, there is no need
            // to worry about connection release
            if (entity != null) {
 
                // A Simple JSON Response Read
                InputStream instream = entity.getContent();
                String result = convertStreamToString(instream);
//                Log.i("REST－Raw",result);
//                result = trimResult(result);
                Log.i("REST",result);
 
                // A Simple JSONObject Creation
                JSONObject json = new JSONObject(result);
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
 
                // Closing the input stream will trigger connection release
                instream.close();
                
                //return valArray;
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return valArray;
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

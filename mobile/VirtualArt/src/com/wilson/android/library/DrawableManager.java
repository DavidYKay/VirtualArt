package com.wilson.android.library;

/*
 Licensed to the Apache Software Foundation (ASF) under one
or more contributor license agreements.  See the NOTICE file
distributed with this work for additional information
regarding copyright ownership.  The ASF licenses this file
to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance
with the License.  You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied.  See the License for the
specific language governing permissions and limitations
under the License.    
*/
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

import com.virtualart.virtualart.model.FetchManager;

public class DrawableManager extends FetchManager {
	public interface DrawableManagerListener {
		public void setFetchedDrawable(Drawable drawable);
	}
	
    private final Map<String, Drawable> drawableMap;

    public DrawableManager() {
    	drawableMap = new HashMap<String, Drawable>();
    }
    
    public Drawable fetchDrawable(String urlString) {
    	if (drawableMap.containsKey(urlString)) {
    		return drawableMap.get(urlString);
    	}

    	Log.d(this.getClass().getSimpleName(), "image url:" + urlString);
    	try {
    		InputStream is = fetch(urlString);
    		Drawable drawable = Drawable.createFromStream(is, "src");
    		drawableMap.put(urlString, drawable);
    		Log.d(this.getClass().getSimpleName(), "got a thumbnail drawable: " + drawable.getBounds() + ", "
    				+ drawable.getIntrinsicHeight() + "," + drawable.getIntrinsicWidth() + ", "
    				+ drawable.getMinimumHeight() + "," + drawable.getMinimumWidth());
    		return drawable;
    	} catch (MalformedURLException e) {
    		Log.e(this.getClass().getSimpleName(), "fetchDrawable failed", e);
    		return null;
    	} catch (IOException e) {
    		Log.e(this.getClass().getSimpleName(), "fetchDrawable failed", e);
    		return null;
    	} catch (NullPointerException e) {
    		Log.e(this.getClass().getSimpleName(), "Null image. Bad url?", e);
    		return null;
    	}
    }

    public void fetchDrawableOnThread(final String urlString, final ImageView imageView) {
    	if (drawableMap.containsKey(urlString)) {
    		imageView.setImageDrawable(drawableMap.get(urlString));
    	}

    	final Handler handler = new Handler() {
    		@Override
    		public void handleMessage(Message message) {
    			imageView.setImageDrawable((Drawable) message.obj);
    		}
    	};

    	Thread thread = new Thread() {
    		@Override
    		public void run() {
    			//TODO : set imageView to a "pending" image
    			Drawable drawable = fetchDrawable(urlString);
    			Message message = handler.obtainMessage(1, drawable);
    			handler.sendMessage(message);
    		}
    	};
    	thread.start();
    }

    public void fetchDrawableOnThreadForListener(final String urlString, final DrawableManagerListener artItem) {
    	if (drawableMap.containsKey(urlString)) {
    		artItem.setFetchedDrawable(drawableMap.get(urlString));
    	}

    	final Handler handler = new Handler() {
    		@Override
    		public void handleMessage(Message message) {
    			artItem.setFetchedDrawable((Drawable) message.obj);
    		}
    	};

    	Thread thread = new Thread() {
    		@Override
    		public void run() {
    			//TODO : set artItem to a "pending" image
    			Drawable drawable = fetchDrawable(urlString);
    			Message message = handler.obtainMessage(1, drawable);
    			handler.sendMessage(message);
    		}
    	};
    	thread.start();
	}
}

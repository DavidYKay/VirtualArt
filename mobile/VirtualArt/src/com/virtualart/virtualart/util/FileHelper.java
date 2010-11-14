package com.virtualart.virtualart.util;

import java.io.File;
import java.io.FileOutputStream;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;

public class FileHelper {

	public static String getDrawableFilename(int artId) {
		return String.format(
			"artmented-%d.png",
			artId
		);
	}

	public static boolean saveDrawableById(Drawable drawable, int artId) {
		return saveDrawableWithFilename(
			drawable,
			FileHelper.getDrawableFilename(artId)
		);
	}
	
	public static boolean saveDrawableWithFilename(Drawable drawable, String filename) {

		boolean result = false;
		
		//Convert drawable to bitmap

		//TODO: convert!
		Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
		saveBitmapWithFilename(
			bitmap,
			filename
		);
		return result;
	}

	public static boolean saveBitmapWithFilename(Bitmap bitmap, String filename) {
		boolean result = false;
	
		File extDir = Environment.getExternalStorageDirectory();
		File file = new File(
            extDir,
            filename
        );
		if (file.exists()) {
			file.delete();
		}
	
		FileOutputStream out = null;
		try {
			//if (file.createNewFile()) {
			if (true) {
				//out = openFileOutput(
				//	filename, 
				//	Context.MODE_PRIVATE
				//);
				out = new FileOutputStream(file.getPath());
				result = bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
			} 
//			else {
//				Log.v (
//						"Save",
//						"couldn't create file at: " + file.getAbsolutePath()
//					  );
//			}
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	
		if (result) {
			Log.v("Save", "Success: " + file.getPath());
		} else {
			Log.v("Save", "Fail");
		}
		return result;
	}

}

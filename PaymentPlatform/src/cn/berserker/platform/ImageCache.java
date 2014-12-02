package cn.berserker.platform;

/*
 * ImageCache class used to collect all image you use in memory. 
 * I use it in every UI instance to collect image, and recycle them
 * in the destroy function. It also could use global imagecache class. and you 
 * need to care about the memory it use.
 *	
 *
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.NinePatch;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.NinePatchDrawable;
import android.graphics.drawable.ColorDrawable;
import android.content.Context;

import java.io.InputStream;
import java.util.Set;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;

import cn.berserker.utils.Tools;


public class ImageCache {
	//save image resource in res/drawable-hdpi/.
	private static final String PREFIX_RES = "res";
	private static final String PREFIX_BITMAP = PREFIX_RES + "/drawable-hdpi/";
	//save the img in the memory.
	HashMap<String, Bitmap> imgMap = new HashMap<String, Bitmap>();
	ArrayList<Drawable> drawList = new ArrayList<Drawable>();

	Bitmap getImageResource(Context context, String fileName) {
		Bitmap bitmap = null;
		if(imgMap.containsKey(fileName)) {
			bitmap = imgMap.get(fileName);
			if(null != bitmap) {
				return bitmap;
			}
			else {
				imgMap.remove(fileName);
			}
		}
		bitmap = getJarBitmap(context,fileName);
		if(null != bitmap) {
			imgMap.put(fileName, bitmap);
		}
		return bitmap;

	}

	public Drawable getImageDrawable(Context context, String fileName) {
		Bitmap bitmap = getImageResource(context, fileName);
		//check whether it was ninepatch.
		if(null != bitmap) {
			byte[] chunk = bitmap.getNinePatchChunk();
			boolean result = NinePatch.isNinePatchChunk(chunk);
			if(result) {
				NinePatchDrawable drawable = new NinePatchDrawable(bitmap, chunk, new Rect(), null); 
				drawList.add(drawable);
				return drawable;
			}
			else {
				BitmapDrawable drawable = new BitmapDrawable(context.getResources(), bitmap);
				drawList.add(drawable);
				return drawable;
			}
		}
		Tools.LOG(fileName + " not found");
		return null;
	}

	public Drawable getColorDrawable(Context context , int color) {
		return new ColorDrawable(color);
	}
	/*
	Drawable getNinePatchDrawable(Context context, String fileName) {
		Bitmap bitmap = getImageResource(context, filename);
		if(null != bitmap) {
			byte[] chunk = bitmap.getNinePatchChunk();
			boolean result = NinePatch.isNinePatchChunk(chunk);
			
		}
		return null;
	}
	*/

	//clear the resource.
	public void clear() {
		Bitmap bitmap = null;
		Iterator it = imgMap.keySet().iterator();
		while(it.hasNext()) {
			String key = (String)it.next();
			it.remove();
		}
		/*
		Set<String> keys = imgMap.keySet();
		for(String key: keys) {
			bitmap = imgMap.get(key);
			if(null != bitmap) {
				bitmap.recycle();
			}
			imgMap.remove(key);
		}
		*/
		for(Drawable drawable: drawList) {
			drawable.setCallback(null);
		}
	}


	//get resource file from apk.
	protected static Bitmap getJarBitmap(Context context, String fileName) {
		InputStream is = context.getClassLoader().getResourceAsStream(PREFIX_BITMAP + fileName);
		if(null != is) {
			Bitmap bitmap = BitmapFactory.decodeStream(is);
			return bitmap;
		}
		return null;
	}
}

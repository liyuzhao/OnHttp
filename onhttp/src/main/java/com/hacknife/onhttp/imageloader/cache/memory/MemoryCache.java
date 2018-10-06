
package com.hacknife.onhttp.imageloader.cache.memory;

import android.graphics.Bitmap;

import java.util.Collection;


public interface MemoryCache {
	
	boolean put(String key, Bitmap value);

	
	Bitmap get(String key);

	
	Bitmap remove(String key);

	
	Collection<String> keys();

	
	void clear();
}

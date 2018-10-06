
package com.hacknife.onhttp.imageloader.utils;

import com.hacknife.onhttp.imageloader.cache.disc.DiskCache;

import java.io.File;


public final class DiskCacheUtils {

	private DiskCacheUtils() {
	}

	
	public static File findInCache(String imageUri, DiskCache diskCache) {
		File image = diskCache.get(imageUri);
		return image != null && image.exists() ? image : null;
	}

	
	public static boolean removeFromCache(String imageUri, DiskCache diskCache) {
		File image = diskCache.get(imageUri);
		return image != null && image.exists() && image.delete();
	}
}

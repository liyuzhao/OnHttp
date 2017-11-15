
package com.aliletter.onhttp.imageloader.cache.disc;

import android.graphics.Bitmap;

import com.aliletter.onhttp.imageloader.utils.IoUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;


public interface DiskCache {
	
	File getDirectory();

	
	File get(String imageUri);

	
	boolean save(String imageUri, InputStream imageStream, IoUtils.CopyListener listener) throws IOException;

	
	boolean save(String imageUri, Bitmap bitmap) throws IOException;

	
	boolean remove(String imageUri);

	
	void close();

	
	void clear();
}

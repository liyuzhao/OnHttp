
package com.blackchopper.onhttp.imageloader.cache.disc.impl;


import com.blackchopper.onhttp.imageloader.cache.disc.naming.FileNameGenerator;

import java.io.File;


public class UnlimitedDiskCache extends BaseDiskCache {
 	public UnlimitedDiskCache(File cacheDir) {
		super(cacheDir);
	}


	public UnlimitedDiskCache(File cacheDir, File reserveCacheDir) {
		super(cacheDir, reserveCacheDir);
	}


	public UnlimitedDiskCache(File cacheDir, File reserveCacheDir, FileNameGenerator fileNameGenerator) {
		super(cacheDir, reserveCacheDir, fileNameGenerator);
	}
}

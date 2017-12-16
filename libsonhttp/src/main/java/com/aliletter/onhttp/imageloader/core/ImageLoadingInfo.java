
package com.aliletter.onhttp.imageloader.core;

import com.aliletter.onhttp.imageloader.core.assist.ImageSize;
import com.aliletter.onhttp.imageloader.core.imageaware.ImageAware;
import com.aliletter.onhttp.imageloader.core.listener.ImageLoadingListener;
import com.aliletter.onhttp.imageloader.core.listener.ImageLoadingProgressListener;

import java.util.concurrent.locks.ReentrantLock;


final class ImageLoadingInfo {

	final String uri;
	final String memoryCacheKey;
	final ImageAware imageAware;
	final ImageSize targetSize;
	final DisplayImageOptions options;
	final ImageLoadingListener listener;
	final ImageLoadingProgressListener progressListener;
	final ReentrantLock loadFromUriLock;

	public ImageLoadingInfo(String uri, ImageAware imageAware, ImageSize targetSize, String memoryCacheKey,
							DisplayImageOptions options, ImageLoadingListener listener,
							ImageLoadingProgressListener progressListener, ReentrantLock loadFromUriLock) {
		this.uri = uri;
		this.imageAware = imageAware;
		this.targetSize = targetSize;
		this.options = options;
		this.listener = listener;
		this.progressListener = progressListener;
		this.loadFromUriLock = loadFromUriLock;
		this.memoryCacheKey = memoryCacheKey;
	}
}
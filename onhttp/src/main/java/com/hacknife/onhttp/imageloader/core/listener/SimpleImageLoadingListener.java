
package com.hacknife.onhttp.imageloader.core.listener;

import android.graphics.Bitmap;
import android.view.View;

import com.hacknife.onhttp.imageloader.core.assist.FailReason;


public class SimpleImageLoadingListener implements ImageLoadingListener {
	@Override
	public void onLoadingStarted(String imageUri, View view) {
		// Empty implementation
	}

	@Override
	public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
		// Empty implementation
	}

	@Override
	public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
		// Empty implementation
	}

	@Override
	public void onLoadingCancelled(String imageUri, View view) {
		// Empty implementation
	}
}

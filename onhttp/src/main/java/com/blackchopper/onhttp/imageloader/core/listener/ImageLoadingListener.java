
package com.blackchopper.onhttp.imageloader.core.listener;

import android.graphics.Bitmap;
import android.view.View;

import com.blackchopper.onhttp.imageloader.core.assist.FailReason;

public interface ImageLoadingListener {

	
	void onLoadingStarted(String imageUri, View view);


	void onLoadingFailed(String imageUri, View view, FailReason failReason);

	
	void onLoadingComplete(String imageUri, View view, Bitmap loadedImage);

	
	void onLoadingCancelled(String imageUri, View view);
}

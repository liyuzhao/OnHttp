
package com.hacknife.onhttp.imageloader.core.listener;

import android.view.View;


public interface ImageLoadingProgressListener {

	
	void onProgressUpdate(String imageUri, View view, int current, int total);
}

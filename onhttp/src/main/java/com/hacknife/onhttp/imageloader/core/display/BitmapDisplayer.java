
package com.hacknife.onhttp.imageloader.core.display;

import android.graphics.Bitmap;

import com.hacknife.onhttp.imageloader.core.assist.LoadedFrom;
import com.hacknife.onhttp.imageloader.core.imageaware.ImageAware;


public interface BitmapDisplayer {

	void display(Bitmap bitmap, ImageAware imageAware, LoadedFrom loadedFrom);
}


package com.aliletter.onhttp.imageloader.core.display;

import android.graphics.Bitmap;

import com.aliletter.onhttp.imageloader.core.assist.LoadedFrom;
import com.aliletter.onhttp.imageloader.core.imageaware.ImageAware;


public interface BitmapDisplayer {

	void display(Bitmap bitmap, ImageAware imageAware, LoadedFrom loadedFrom);
}

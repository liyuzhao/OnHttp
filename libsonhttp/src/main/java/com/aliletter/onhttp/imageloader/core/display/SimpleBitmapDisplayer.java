
package com.aliletter.onhttp.imageloader.core.display;

import android.graphics.Bitmap;

import com.aliletter.onhttp.imageloader.core.assist.LoadedFrom;
import com.aliletter.onhttp.imageloader.core.imageaware.ImageAware;


public final class SimpleBitmapDisplayer implements BitmapDisplayer {
	@Override
	public void display(Bitmap bitmap, ImageAware imageAware, LoadedFrom loadedFrom) {
		imageAware.setImageBitmap(bitmap);
	}
}

package com.hacknife.onhttp.imageloader.core.imageaware;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.hacknife.onhttp.imageloader.core.assist.ViewScaleType;


public interface ImageAware {
	
	int getWidth();

	
	int getHeight();


	ViewScaleType getScaleType();

	
	View getWrappedView();


	boolean isCollected();

	
	int getId();


	boolean setImageDrawable(Drawable drawable);


	boolean setImageBitmap(Bitmap bitmap);
}

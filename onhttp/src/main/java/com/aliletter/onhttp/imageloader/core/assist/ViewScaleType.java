
package com.aliletter.onhttp.imageloader.core.assist;

import android.widget.ImageView;
import android.widget.ImageView.ScaleType;


public enum ViewScaleType {
	
	FIT_INSIDE,
	
	CROP;

	
	public static ViewScaleType fromImageView(ImageView imageView) {
		switch (imageView.getScaleType()) {
			case FIT_CENTER:
			case FIT_XY:
			case FIT_START:
			case FIT_END:
			case CENTER_INSIDE:
				return FIT_INSIDE;
			case MATRIX:
			case CENTER:
			case CENTER_CROP:
			default:
				return CROP;
		}
	}
}

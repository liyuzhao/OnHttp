
package com.blackchopper.onhttp.imageloader.core.decode;

import android.annotation.TargetApi;
import android.graphics.BitmapFactory.Options;
import android.os.Build;

import com.blackchopper.onhttp.imageloader.core.DisplayImageOptions;
import com.blackchopper.onhttp.imageloader.core.assist.ImageScaleType;
import com.blackchopper.onhttp.imageloader.core.assist.ImageSize;
import com.blackchopper.onhttp.imageloader.core.assist.ViewScaleType;
import com.blackchopper.onhttp.imageloader.core.download.ImageDownloader;


public class ImageDecodingInfo {

	private final String imageKey;
	private final String imageUri;
	private final String originalImageUri;
	private final ImageSize targetSize;

	private final ImageScaleType imageScaleType;
	private final ViewScaleType viewScaleType;

	private final ImageDownloader downloader;
	private final Object extraForDownloader;

	private final boolean considerExifParams;
	private final Options decodingOptions;

	public ImageDecodingInfo(String imageKey, String imageUri, String originalImageUri, ImageSize targetSize, ViewScaleType viewScaleType,
                             ImageDownloader downloader, DisplayImageOptions displayOptions) {
		this.imageKey = imageKey;
		this.imageUri = imageUri;
		this.originalImageUri = originalImageUri;
		this.targetSize = targetSize;

		this.imageScaleType = displayOptions.getImageScaleType();
		this.viewScaleType = viewScaleType;

		this.downloader = downloader;
		this.extraForDownloader = displayOptions.getExtraForDownloader();

		considerExifParams = displayOptions.isConsiderExifParams();
		decodingOptions = new Options();
		copyOptions(displayOptions.getDecodingOptions(), decodingOptions);
	}

	private void copyOptions(Options srcOptions, Options destOptions) {
		destOptions.inDensity = srcOptions.inDensity;
		destOptions.inDither = srcOptions.inDither;
		destOptions.inInputShareable = srcOptions.inInputShareable;
		destOptions.inJustDecodeBounds = srcOptions.inJustDecodeBounds;
		destOptions.inPreferredConfig = srcOptions.inPreferredConfig;
		destOptions.inPurgeable = srcOptions.inPurgeable;
		destOptions.inSampleSize = srcOptions.inSampleSize;
		destOptions.inScaled = srcOptions.inScaled;
		destOptions.inScreenDensity = srcOptions.inScreenDensity;
		destOptions.inTargetDensity = srcOptions.inTargetDensity;
		destOptions.inTempStorage = srcOptions.inTempStorage;
		if (Build.VERSION.SDK_INT >= 10) copyOptions10(srcOptions, destOptions);
		if (Build.VERSION.SDK_INT >= 11) copyOptions11(srcOptions, destOptions);
	}

	@TargetApi(10)
	private void copyOptions10(Options srcOptions, Options destOptions) {
		destOptions.inPreferQualityOverSpeed = srcOptions.inPreferQualityOverSpeed;
	}

	@TargetApi(11)
	private void copyOptions11(Options srcOptions, Options destOptions) {
		destOptions.inBitmap = srcOptions.inBitmap;
		destOptions.inMutable = srcOptions.inMutable;
	}

	public String getImageKey() {
		return imageKey;
	}

	
	public String getImageUri() {
		return imageUri;
	}

	
	public String getOriginalImageUri() {
		return originalImageUri;
	}

	
	public ImageSize getTargetSize() {
		return targetSize;
	}

	
	public ImageScaleType getImageScaleType() {
		return imageScaleType;
	}

	
	public ViewScaleType getViewScaleType() {
		return viewScaleType;
	}

	
	public ImageDownloader getDownloader() {
		return downloader;
	}

	
	public Object getExtraForDownloader() {
		return extraForDownloader;
	}

	
	public boolean shouldConsiderExifParams() {
		return considerExifParams;
	}

	
	public Options getDecodingOptions() {
		return decodingOptions;
	}
}
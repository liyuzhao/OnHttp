
package com.aliletter.onhttp.imageloader.core.imageaware;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.aliletter.onhttp.imageloader.core.assist.ViewScaleType;


public interface ImageAware {
	/**
	 * Returns width of image aware view. This value is used to define scale size for original image.
	 * Can return 0 if width is undefined.<br />
	 * Is called on UI thread if ImageLoader was called on UI thread. Otherwise - on background thread.
	 */
	int getWidth();

	/**
	 * Returns height of image aware view. This value is used to define scale size for original image.
	 * Can return 0 if height is undefined.<br />
	 * Is called on UI thread if ImageLoader was called on UI thread. Otherwise - on background thread.
	 */
	int getHeight();


	ViewScaleType getScaleType();

	/**
	 * Returns wrapped Android {@link android.view.View View}. Can return <b>null</b> if no view is wrapped or view was
	 * collected by GC.<br />
	 * Is called on UI thread if ImageLoader was called on UI thread. Otherwise - on background thread.
	 */
	View getWrappedView();


	boolean isCollected();

	/**
	 * Returns ID of image aware view. Point of ID is similar to Object's hashCode. This ID should be unique for every
	 * image view instance and should be the same for same instances. This ID identifies processing task in ImageLoader
	 * so ImageLoader won't process two image aware views with the same ID in one time. When ImageLoader get new task
	 * it cancels old task with this ID (if any) and starts new task.
	 * <p/>
	 * It's reasonable to return hash code of wrapped view (if any) to prevent displaying non-actual images in view
	 * because of view re-using.
	 */
	int getId();


	boolean setImageDrawable(Drawable drawable);


	boolean setImageBitmap(Bitmap bitmap);
}

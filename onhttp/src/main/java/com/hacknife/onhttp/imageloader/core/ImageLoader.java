
package com.hacknife.onhttp.imageloader.core;

import android.graphics.Bitmap;
import android.text.TextUtils;

import com.hacknife.onhttp.imageloader.BaseImageLoader;
import com.hacknife.onhttp.imageloader.IImageLoader;
import com.hacknife.onhttp.imageloader.core.assist.ImageSize;
import com.hacknife.onhttp.imageloader.core.assist.LoadedFrom;
import com.hacknife.onhttp.imageloader.core.imageaware.ImageAware;
import com.hacknife.onhttp.imageloader.core.listener.ImageLoadingListener;
import com.hacknife.onhttp.imageloader.core.listener.ImageLoadingProgressListener;
import com.hacknife.onhttp.imageloader.utils.ImageSizeUtils;
import com.hacknife.onhttp.imageloader.utils.L;
import com.hacknife.onhttp.imageloader.utils.MemoryCacheUtils;


public class ImageLoader extends BaseImageLoader implements IImageLoader {

    @Override
    public void displayImage(String uri, ImageAware imageAware, DisplayImageOptions options, ImageSize targetSize, ImageLoadingListener listener, ImageLoadingProgressListener progressListener) {
        ImageLoaderConfig.getInstance().checkConfiguration();
        if (imageAware == null) {
            throw new IllegalArgumentException(ImageLoaderConfig.ERROR_WRONG_ARGUMENTS);
        }
        if (listener == null) {
            listener = ImageLoaderConfig.getInstance().getDefaultListener();
        }
        if (options == null) {
            options = ImageLoaderConfig.getInstance().getConfiguration().defaultDisplayImageOptions;
        }

        if (TextUtils.isEmpty(uri)) {
            ImageLoaderConfig.getInstance().getEngine().cancelDisplayTaskFor(imageAware);
            listener.onLoadingStarted(uri, imageAware.getWrappedView());
            if (options.shouldShowImageForEmptyUri()) {
                imageAware.setImageDrawable(options.getImageForEmptyUri(ImageLoaderConfig.getInstance().getConfiguration().resources));
            } else {
                imageAware.setImageDrawable(null);
            }
            listener.onLoadingComplete(uri, imageAware.getWrappedView(), null);
            return;
        }

        if (targetSize == null) {
            targetSize = ImageSizeUtils.defineTargetSizeForView(imageAware, ImageLoaderConfig.getInstance().getConfiguration().getMaxImageSize());
        }
        String memoryCacheKey = MemoryCacheUtils.generateKey(uri, targetSize);
        ImageLoaderConfig.getInstance().getEngine().prepareDisplayTaskFor(imageAware, memoryCacheKey);

        listener.onLoadingStarted(uri, imageAware.getWrappedView());

        Bitmap bmp = ImageLoaderConfig.getInstance().getConfiguration().memoryCache.get(memoryCacheKey);
        if (bmp != null && !bmp.isRecycled()) {
            L.d(ImageLoaderConfig.LOG_LOAD_IMAGE_FROM_MEMORY_CACHE, memoryCacheKey);

            if (options.shouldPostProcess()) {
                ImageLoadingInfo imageLoadingInfo = new ImageLoadingInfo(uri, imageAware, targetSize, memoryCacheKey,
                        options, listener, progressListener, ImageLoaderConfig.getInstance().getEngine().getLockForUri(uri));
                ProcessAndDisplayImageTask displayTask = new ProcessAndDisplayImageTask(ImageLoaderConfig.getInstance().getEngine(), bmp, imageLoadingInfo,
                        ImageLoaderConfig.getInstance().defineHandler(options));
                if (options.isSyncLoading()) {
                    displayTask.run();
                } else {
                    ImageLoaderConfig.getInstance().getEngine().submit(displayTask);
                }
            } else {
                options.getDisplayer().display(bmp, imageAware, LoadedFrom.MEMORY_CACHE);
                listener.onLoadingComplete(uri, imageAware.getWrappedView(), bmp);
            }
        } else {
            if (options.shouldShowImageOnLoading()) {
                imageAware.setImageDrawable(options.getImageOnLoading(ImageLoaderConfig.getInstance().getConfiguration().resources));
            } else if (options.isResetViewBeforeLoading()) {
                imageAware.setImageDrawable(null);
            }

            ImageLoadingInfo imageLoadingInfo = new ImageLoadingInfo(uri, imageAware, targetSize, memoryCacheKey,
                    options, listener, progressListener, ImageLoaderConfig.getInstance().getEngine().getLockForUri(uri));
            LoadAndDisplayImageTask displayTask = new LoadAndDisplayImageTask(ImageLoaderConfig.getInstance().getEngine(), imageLoadingInfo,
                    ImageLoaderConfig.getInstance().defineHandler(options));
            if (options.isSyncLoading()) {
                displayTask.run();
            } else {
                ImageLoaderConfig.getInstance().getEngine().submit(displayTask);
            }
        }
    }



}

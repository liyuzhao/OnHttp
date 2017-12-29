
package com.aliletter.onhttp.imageloader.core;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.aliletter.onhttp.imageloader.BaseImageLoader;
import com.aliletter.onhttp.imageloader.IImageLoader;
import com.aliletter.onhttp.imageloader.cache.disc.DiskCache;
import com.aliletter.onhttp.imageloader.cache.memory.MemoryCache;
import com.aliletter.onhttp.imageloader.core.DisplayImageOptions;
import com.aliletter.onhttp.imageloader.core.ImageLoaderConfig;
import com.aliletter.onhttp.imageloader.core.ImageLoadingInfo;
import com.aliletter.onhttp.imageloader.core.LoadAndDisplayImageTask;
import com.aliletter.onhttp.imageloader.core.ProcessAndDisplayImageTask;
import com.aliletter.onhttp.imageloader.core.assist.ImageSize;
import com.aliletter.onhttp.imageloader.core.assist.LoadedFrom;
import com.aliletter.onhttp.imageloader.core.assist.ViewScaleType;
import com.aliletter.onhttp.imageloader.core.imageaware.ImageAware;
import com.aliletter.onhttp.imageloader.core.imageaware.ImageViewAware;
import com.aliletter.onhttp.imageloader.core.imageaware.NonViewAware;
import com.aliletter.onhttp.imageloader.core.imageaware.ViewAware;
import com.aliletter.onhttp.imageloader.core.listener.ImageLoadingListener;
import com.aliletter.onhttp.imageloader.core.listener.ImageLoadingProgressListener;
import com.aliletter.onhttp.imageloader.core.listener.SimpleImageLoadingListener;
import com.aliletter.onhttp.imageloader.utils.ImageSizeUtils;
import com.aliletter.onhttp.imageloader.utils.L;
import com.aliletter.onhttp.imageloader.utils.MemoryCacheUtils;

import static com.aliletter.onhttp.imageloader.core.ImageLoaderConfig.ERROR_NOT_INIT;
import static com.aliletter.onhttp.imageloader.core.ImageLoaderConfig.ERROR_WRONG_ARGUMENTS;
import static com.aliletter.onhttp.imageloader.core.ImageLoaderConfig.LOG_DESTROY;

import static com.aliletter.onhttp.imageloader.core.ImageLoaderConfig.LOG_LOAD_IMAGE_FROM_MEMORY_CACHE;


public class ImageLoader extends BaseImageLoader implements IImageLoader {

    @Override
    public void displayImage(String uri, ImageAware imageAware, DisplayImageOptions options, ImageSize targetSize, ImageLoadingListener listener, ImageLoadingProgressListener progressListener) {
        ImageLoaderConfig.getInstance().checkConfiguration();
        if (imageAware == null) {
            throw new IllegalArgumentException(ERROR_WRONG_ARGUMENTS);
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
            L.d(LOG_LOAD_IMAGE_FROM_MEMORY_CACHE, memoryCacheKey);

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

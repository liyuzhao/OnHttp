package com.aliletter.onhttp;

import android.content.Context;
import android.graphics.Bitmap;

import com.aliletter.onhttp.downloader.DownLoader;
import com.aliletter.onhttp.downloader.IDownLoader;
import com.aliletter.onhttp.httploader.HttpLoader;
import com.aliletter.onhttp.httploader.IHttpLoader;
import com.aliletter.onhttp.imageloader.IImageLoader;
import com.aliletter.onhttp.imageloader.cache.disc.impl.UnlimitedDiskCache;
import com.aliletter.onhttp.imageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.aliletter.onhttp.imageloader.cache.memory.impl.LruMemoryCache;
import com.aliletter.onhttp.imageloader.core.DisplayImageOptions;
import com.aliletter.onhttp.imageloader.core.ImageLoader;

import com.aliletter.onhttp.imageloader.core.ImageLoaderConfig;
import com.aliletter.onhttp.imageloader.core.ImageLoaderConfiguration;
import com.aliletter.onhttp.imageloader.core.assist.QueueProcessingType;
import com.aliletter.onhttp.imageloader.core.decode.BaseImageDecoder;
import com.aliletter.onhttp.imageloader.core.download.BaseImageDownloader;
import com.aliletter.onhttp.imageloader.utils.StorageUtils;
import com.aliletter.onhttp.uploader.IUpLoader;
import com.aliletter.onhttp.uploader.UpLoader;

import java.io.File;

/**
 * Author: aliletter
 * Github: http://github.com/aliletter
 * Data: 2017/11/15.
 */

public class OnHttp {

    public static IHttpLoader httpLoader() {
        return new HttpLoader();
    }

    public static IUpLoader upLoader() {
        return new UpLoader();
    }

    public static IDownLoader downLoader() {
        return new DownLoader();
    }

    public static IImageLoader imageLoader() {
        return new ImageLoader();
    }


    public static void initDefault(Context context) {
        initDefault(context, null);
    }

    public static void initDefault(Context context, String cache) {
        File cacheDir;
        if (cache == null)
            cacheDir = StorageUtils.getCacheDirectory(context);
        else
            cacheDir = new File(cache);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                //  .memoryCacheExtraOptions(480, 800) // default = device screen dimensions
                .threadPoolSize(3)
                .threadPriority(Thread.NORM_PRIORITY - 1) // default
                .tasksProcessingOrder(QueueProcessingType.FIFO) // default
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024)
                .memoryCacheSizePercentage(13) // default
                .diskCache(new UnlimitedDiskCache(cacheDir)) // default
                .diskCacheSize(50 * 1024 * 1024)
                .diskCacheFileCount(100)
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
                .imageDownloader(new BaseImageDownloader(context)) // default
                .imageDecoder(new BaseImageDecoder(false)) // default
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
                .writeDebugLogs()
                .build();
        init(config);
    }


    public static void init(ImageLoaderConfiguration configuration) {
        ImageLoaderConfig.getInstance().init(configuration);
    }
}

package com.hacknife.onhttp;

import android.content.Context;

import com.hacknife.onhttp.downloader.DownLoader;
import com.hacknife.onhttp.downloader.IDownLoader;
import com.hacknife.onhttp.httploader.HttpLoader;
import com.hacknife.onhttp.httploader.IHttpLoader;
import com.hacknife.onhttp.imageloader.IImageLoader;
import com.hacknife.onhttp.imageloader.cache.disc.impl.UnlimitedDiskCache;
import com.hacknife.onhttp.imageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.hacknife.onhttp.imageloader.cache.memory.impl.LruMemoryCache;
import com.hacknife.onhttp.imageloader.core.DisplayImageOptions;
import com.hacknife.onhttp.imageloader.core.ImageLoader;

import com.hacknife.onhttp.imageloader.core.ImageLoaderConfig;
import com.hacknife.onhttp.imageloader.core.ImageLoaderConfiguration;
import com.hacknife.onhttp.imageloader.core.assist.QueueProcessingType;
import com.hacknife.onhttp.imageloader.core.decode.BaseImageDecoder;
import com.hacknife.onhttp.imageloader.core.download.BaseImageDownloader;
import com.hacknife.onhttp.imageloader.utils.StorageUtils;
import com.hacknife.onhttp.uploader.IUpLoader;
import com.hacknife.onhttp.uploader.UpLoader;

import java.io.File;

/**
 * author  : Hacknife
 * e-mail  : 4884280@qq.com
 * github  : http://github.com/hacknife
 * project : OnHttp
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

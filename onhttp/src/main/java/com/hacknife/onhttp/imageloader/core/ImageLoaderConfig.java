package com.hacknife.onhttp.imageloader.core;

import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import com.hacknife.onhttp.imageloader.cache.disc.DiskCache;
import com.hacknife.onhttp.imageloader.cache.memory.MemoryCache;
import com.hacknife.onhttp.imageloader.core.imageaware.ImageAware;
import com.hacknife.onhttp.imageloader.core.imageaware.ImageViewAware;
import com.hacknife.onhttp.imageloader.core.listener.ImageLoadingListener;
import com.hacknife.onhttp.imageloader.core.listener.SimpleImageLoadingListener;
import com.hacknife.onhttp.imageloader.utils.L;

/**
 * author  : Hacknife
 * e-mail  : 4884280@qq.com
 * github  : http://github.com/hacknife
 * project : OnHttp
 */

public class ImageLoaderConfig {
    public static final String LOG_INIT_CONFIG = "Initialize ImageLoader with configuration";
    public static final String LOG_DESTROY = "Destroy ImageLoader";
    public static final String LOG_LOAD_IMAGE_FROM_MEMORY_CACHE = "Load image from memory cache [%s]";
    public static final String WARNING_RE_INIT_CONFIG = "Try to initialize ImageLoader which had already been initialized before. " + "To re-init ImageLoader with new configuration call ImageLoader.destroy() at first.";
    public static final String ERROR_WRONG_ARGUMENTS = "Wrong arguments were passed to displayImage() method (ImageView reference must not be null)";
    public static final String ERROR_NOT_INIT = "ImageLoader must be init with configuration before using";
    public static final String ERROR_INIT_CONFIG_WITH_NULL = "ImageLoader configuration can not be initialized with null";


    private volatile static ImageLoaderConfig instance;
    private ImageLoaderConfiguration configuration;
    private ImageLoaderEngine engine;
    private ImageLoadingListener defaultListener = new SimpleImageLoadingListener();

    public static ImageLoaderConfig getInstance() {
        if (instance == null) {
            synchronized (ImageLoader.class) {
                if (instance == null) {
                    instance = new ImageLoaderConfig();
                }
            }
        }
        return instance;
    }


    public synchronized void init(ImageLoaderConfiguration configuration) {
        if (configuration == null) {
            throw new IllegalArgumentException(ERROR_INIT_CONFIG_WITH_NULL);
        }
        if (this.configuration == null) {
            L.d(LOG_INIT_CONFIG);
            engine = new ImageLoaderEngine(configuration);
            this.configuration = configuration;
        } else {
            L.w(WARNING_RE_INIT_CONFIG);
        }
    }

    public ImageLoaderConfiguration getConfiguration() {
        return configuration;
    }

    public ImageLoaderEngine getEngine() {
        return engine;
    }

    public ImageLoadingListener getDefaultListener() {
        return defaultListener;
    }

    public void setConfiguration(ImageLoaderConfiguration configuration) {
        this.configuration = configuration;
    }

    public void setEngine(ImageLoaderEngine engine) {
        this.engine = engine;
    }

    public void setDefaultListener(ImageLoadingListener defaultListener) {
        this.defaultListener = defaultListener;
    }

    public void setDefaultLoadingListener(ImageLoadingListener listener) {
        setDefaultListener(listener == null ? new SimpleImageLoadingListener() : listener);
    }

    public void checkConfiguration() {
        if (configuration == null) {
            throw new IllegalStateException(ERROR_NOT_INIT);
        }
    }

    public MemoryCache getMemoryCache() {
        checkConfiguration();
        return configuration.memoryCache;
    }

    public void clearMemoryCache() {
        checkConfiguration();
        configuration.memoryCache.clear();
    }


    public DiskCache getDiskCache() {
        checkConfiguration();
        return configuration.diskCache;
    }

    public void clearDiskCache() {
        checkConfiguration();
        configuration.diskCache.clear();
    }


    public String getLoadingUriForView(ImageAware imageAware) {
        return engine.getLoadingUriForView(imageAware);
    }


    public String getLoadingUriForView(ImageView imageView) {
        return engine.getLoadingUriForView(new ImageViewAware(imageView));
    }


    public void cancelDisplayTask(ImageAware imageAware) {
        engine.cancelDisplayTaskFor(imageAware);
    }


    public void cancelDisplayTask(ImageView imageView) {
        engine.cancelDisplayTaskFor(new ImageViewAware(imageView));
    }


    public void denyNetworkDownloads(boolean denyNetworkDownloads) {
        engine.denyNetworkDownloads(denyNetworkDownloads);
    }


    public void handleSlowNetwork(boolean handleSlowNetwork) {
        engine.handleSlowNetwork(handleSlowNetwork);
    }


    public void pause() {
        engine.pause();
    }


    public void resume() {
        engine.resume();
    }


    public void stop() {
        engine.stop();
    }

    public void destroy() {
        if (configuration != null) L.d(LOG_DESTROY);
        stop();
        configuration.diskCache.close();
        engine = null;
        configuration = null;
    }

    public static Handler defineHandler(DisplayImageOptions options) {
        Handler handler = options.getHandler();
        if (options.isSyncLoading()) {
            handler = null;
        } else if (handler == null && Looper.myLooper() == Looper.getMainLooper()) {
            handler = new Handler();
        }
        return handler;
    }

}

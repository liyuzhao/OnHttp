package com.hacknife.onhttp.imageloader;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import com.hacknife.onhttp.imageloader.core.DisplayImageOptions;
import com.hacknife.onhttp.imageloader.core.assist.ImageSize;
import com.hacknife.onhttp.imageloader.core.imageaware.ImageAware;
import com.hacknife.onhttp.imageloader.core.imageaware.ImageViewAware;
import com.hacknife.onhttp.imageloader.core.listener.ImageLoadingListener;
import com.hacknife.onhttp.imageloader.core.listener.ImageLoadingProgressListener;

/**
 * author  : Hacknife
 * e-mail  : 4884280@qq.com
 * github  : http://github.com/hacknife
 * project : OnHttp
 */

public abstract class BaseImageLoader implements IImageLoader {
    protected String url;
    protected ImageViewAware view;
    protected int defaultId = 0;
    protected int errorId = 0;
    protected DisplayImageOptions option;
    protected ImageLoadingListener listener;
    protected ImageLoadingProgressListener progress;
    protected ImageSize size;


    @Override
    public IImageLoader url(String url) {
        this.url = url;
        return this;
    }

    @Override
    public IImageLoader view(ImageView view) {
        this.view = new ImageViewAware(view);
        return this;
    }

    @Override
    public IImageLoader size(ImageSize size) {
        this.size = size;
        return this;
    }

    @Override
    public IImageLoader defaultId(Integer id) {
        this.defaultId = id;
        return this;
    }

    @Override
    public IImageLoader errorId(Integer id) {
        this.errorId = id;
        return this;
    }

    @Override
    public IImageLoader option(DisplayImageOptions options) {
        this.option = options;
        return this;
    }

    @Override
    public IImageLoader listener(ImageLoadingListener listener) {
        this.listener = listener;
        return this;
    }

    @Override
    public IImageLoader progress(ImageLoadingProgressListener listener) {
        this.progress = listener;
        return this;
    }

    @Override
    public void run() {

    }

    @Override
    public void executor() {
        if (!checkParameters()) return;
        if (option == null) {
            option = new DisplayImageOptions.Builder()
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .showImageOnFail(errorId)
                    .showImageOnLoading(defaultId)
                    .build();
        }
        displayImage(url, view, option, size, listener, progress);
    }

    protected abstract void displayImage(String url, ImageAware view, DisplayImageOptions option, ImageSize size, ImageLoadingListener listener, ImageLoadingProgressListener progress);


    @Override
    public boolean checkParameters() {
        if (url == null) {
            Log.w("OnHttp", "url is null");
            return false;
        }
        return true;
    }
}

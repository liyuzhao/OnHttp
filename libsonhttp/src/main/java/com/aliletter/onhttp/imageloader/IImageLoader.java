package com.aliletter.onhttp.imageloader;

import android.widget.ImageView;

import com.aliletter.onhttp.core.IBaseLoader;
import com.aliletter.onhttp.imageloader.core.DisplayImageOptions;
import com.aliletter.onhttp.imageloader.core.assist.ImageSize;
import com.aliletter.onhttp.imageloader.core.listener.ImageLoadingListener;
import com.aliletter.onhttp.imageloader.core.listener.ImageLoadingProgressListener;

/**
 * Author: aliletter
 * Github: http://github.com/aliletter
 * Data: 2017/11/16.
 */

public interface IImageLoader extends IBaseLoader {
    IImageLoader url(String url);

    IImageLoader view(ImageView view);

    IImageLoader size(ImageSize size);

    IImageLoader defaultId(Integer resId);

    IImageLoader errorId(Integer resId);

    IImageLoader option(DisplayImageOptions options);

    IImageLoader listener(ImageLoadingListener listener);

    IImageLoader progress(ImageLoadingProgressListener listener);

}

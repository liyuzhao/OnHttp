package com.hacknife.onhttp.imageloader;

import android.widget.ImageView;

import com.hacknife.onhttp.core.IBaseLoader;
import com.hacknife.onhttp.imageloader.core.DisplayImageOptions;
import com.hacknife.onhttp.imageloader.core.assist.ImageSize;
import com.hacknife.onhttp.imageloader.core.listener.ImageLoadingListener;
import com.hacknife.onhttp.imageloader.core.listener.ImageLoadingProgressListener;

/**
 * author  : Hacknife
 * e-mail  : 4884280@qq.com
 * github  : http://github.com/hacknife
 * project : OnHttp
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

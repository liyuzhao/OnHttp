package com.blackchopper.onhttp.imageloader;

import android.widget.ImageView;

import com.blackchopper.onhttp.core.IBaseLoader;
import com.blackchopper.onhttp.imageloader.core.DisplayImageOptions;
import com.blackchopper.onhttp.imageloader.core.assist.ImageSize;
import com.blackchopper.onhttp.imageloader.core.listener.ImageLoadingListener;
import com.blackchopper.onhttp.imageloader.core.listener.ImageLoadingProgressListener;

/**
 * author  : Black Chopper
 * e-mail  : 4884280@qq.com
 * github  : http://github.com/BlackChopper
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

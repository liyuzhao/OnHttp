package com.aliletter.onhttp.imgloader;

import android.widget.ImageView;

import com.aliletter.onhttp.core.IBaseService;

/**
 * Author: aliletter
 * Github: http://github.com/aliletter
 * Data: 2017/11/15.
 */

public interface IImageService extends IBaseService {
    IImageService url(String url);

    IImageService view(ImageView view);

    IImageService defaultId(int id);

    IImageService errorId(int id);
}

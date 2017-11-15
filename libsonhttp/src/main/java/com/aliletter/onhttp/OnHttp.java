package com.aliletter.onhttp;

import com.aliletter.onhttp.downloader.DownService;
import com.aliletter.onhttp.downloader.IDownService;
import com.aliletter.onhttp.httploader.HttpService;
import com.aliletter.onhttp.httploader.IHttpService;
import com.aliletter.onhttp.imgloader.IImageService;
import com.aliletter.onhttp.imgloader.ImageService;
import com.aliletter.onhttp.uploader.IUpService;
import com.aliletter.onhttp.uploader.UpService;

/**
 * Author: aliletter
 * Github: http://github.com/aliletter
 * Data: 2017/11/15.
 */

public class OnHttp {

    public static IHttpService httpBuilder() {
        return new HttpService();
    }

    public static IUpService upBuilder() {
        return new UpService();
    }

    public static IDownService downBuilder() {
        return new DownService();
    }

    public static IImageService imageBuilder() {
        return new ImageService();
    }
}

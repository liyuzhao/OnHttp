package com.blackchopper.onhttp.uploader;

import java.io.File;

/**
 * author  : Black Chopper
 * e-mail  : 4884280@qq.com
 * github  : http://github.com/BlackChopper
 * project : OnHttp
 */

public interface IUpListener<T> {
    void onProgress(float progress);

    void onSuccess(T obj);

    void onError(int code);
}

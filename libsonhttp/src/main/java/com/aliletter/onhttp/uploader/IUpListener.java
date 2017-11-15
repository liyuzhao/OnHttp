package com.aliletter.onhttp.uploader;

import java.io.File;

/**
 * Author: aliletter
 * Github: http://github.com/aliletter
 * Data: 2017/11/15.
 */

public interface IUpListener<T> {
    void onProgress(float progress);

    void onSuccess(T obj);

    void onError(int code);
}

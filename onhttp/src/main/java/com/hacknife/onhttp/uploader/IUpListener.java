package com.hacknife.onhttp.uploader;

/**
 * author  : Hacknife
 * e-mail  : 4884280@qq.com
 * github  : http://github.com/hacknife
 * project : OnHttp
 */

public interface IUpListener<T> {
    void onProgress(float progress);

    void onSuccess(T obj);

    void onError(int code);
}

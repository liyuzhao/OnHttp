package com.hacknife.onhttp.httploader;

/**
 * author  : Hacknife
 * e-mail  : 4884280@qq.com
 * github  : http://github.com/hacknife
 * project : OnHttp
 */

public interface IHttpListener<T> {
    void onSuccess(T t);
    void onError(int code);
}

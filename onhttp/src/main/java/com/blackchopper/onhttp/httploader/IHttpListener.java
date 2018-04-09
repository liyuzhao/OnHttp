package com.blackchopper.onhttp.httploader;

/**
 * author  : Black Chopper
 * e-mail  : 4884280@qq.com
 * github  : http://github.com/BlackChopper
 * project : OnHttp
 */

public interface IHttpListener<T> {
    void onSuccess(T t);
    void onError(int code);
}

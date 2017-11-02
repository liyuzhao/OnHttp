package com.aliletter.onhttp.base;

/**
 * Created by 段泽全 on 2017/9/4.
 * BLog：http://blog.csdn.net/mr_absurd
 * Emile:4884280@qq.com
 */

public interface IHttpListener<T> {
    void onSuccess(T t);
    void onError(int code);
}

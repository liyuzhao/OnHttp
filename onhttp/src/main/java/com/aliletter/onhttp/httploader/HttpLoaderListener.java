package com.aliletter.onhttp.httploader;

import com.aliletter.onhttp.core.BaseLoaderListener;
import com.aliletter.onhttp.util.OnHttpUtil;

import java.io.IOException;
import java.io.InputStream;

/**
 * Author: aliletter
 * Github: http://github.com/aliletter
 * Data: 2017/11/15.
 */

public class HttpLoaderListener<T> extends BaseLoaderListener {
    private IHttpListener<T> listener;
    private Class<?> clazz;

    public HttpLoaderListener(IHttpListener<T> listener, Class<?> clazz) {
        this.listener = listener;
        this.clazz = clazz;
    }

    @Override
    public void onSuccess(final InputStream inputStream) {
        final T response = (T) OnHttpUtil.responseFromInputStream(inputStream, clazz);

        hander.post(new Runnable() {
            @Override
            public void run() {
                listener.onSuccess(response);
            }
        });
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void error(final int code) {
        hander.post(new Runnable() {
            @Override
            public void run() {
                listener.onError(code);
            }
        });
    }
}

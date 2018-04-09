package com.blackchopper.onhttp.httploader;

import com.blackchopper.onhttp.core.BaseLoaderListener;
import com.blackchopper.onhttp.util.OnHttpUtil;

import java.io.IOException;
import java.io.InputStream;

/**
 * author  : Black Chopper
 * e-mail  : 4884280@qq.com
 * github  : http://github.com/BlackChopper
 * project : OnHttp
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

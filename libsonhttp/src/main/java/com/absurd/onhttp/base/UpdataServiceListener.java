package com.absurd.onhttp.base;

import android.os.Handler;
import android.os.Looper;

import com.absurd.onhttp.base.base.BaseServiceListener;

import java.io.InputStream;

/**
 * Author: mr-absurd
 * Github: http://github.com/mr-absurd
 * Data: 2017/9/12.
 */

public class UpdataServiceListener<T> extends BaseServiceListener {
    private Handler hander = new Handler(Looper.getMainLooper());
    private IHttpListener<T> listener;
    private Class<?> T;

    public UpdataServiceListener(Class<?> T, IHttpListener<T> listener) {
        this.T = T;
        this.listener = listener;
    }

    @Override
    public void onSuccess(InputStream inputStream) {
        T respone = null;
        if (T.getName().equalsIgnoreCase(STRING_NAME)) {
            respone = (T) getString(inputStream);
        } else if (T.getName().equalsIgnoreCase(BITMAP_NAME)) {
            respone = (T) getBitmap(inputStream);
        } else if (T.getName().equalsIgnoreCase(FILE_NAME)) {
        } else {
            respone = (T) getJson(inputStream);
        }
        final T finalRespone = respone;
        hander.post(new Runnable() {
            @Override
            public void run() {
                if (listener != null)
                    listener.onSuccess(finalRespone);
            }
        });
    }

    @Override
    public void error(final int code) {
        hander.post(new Runnable() {
            @Override
            public void run() {
                if (listener != null)
                    listener.onError(code);
            }
        });
    }
}

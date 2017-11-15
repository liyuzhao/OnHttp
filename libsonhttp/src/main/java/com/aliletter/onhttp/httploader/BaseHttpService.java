package com.aliletter.onhttp.httploader;

import android.util.Log;

import com.aliletter.onhttp.core.IHeaderListener;
import com.aliletter.onhttp.core.BaseService;
import com.aliletter.onhttp.core.IServiceListener;
import com.aliletter.onhttp.core.Method;

/**
 * Author: aliletter
 * Github: http://github.com/aliletter
 * Data: 2017/11/15.
 */

public abstract class BaseHttpService extends BaseService implements IHttpService {
    protected Method method = null;
    protected IHttpListener listener = null;
    protected IHeaderListener headerListener = null;

    protected Class<?> clazz = null;
    protected IServiceListener serviceListener;



    @Override
    public IHttpService url(String url) {
        this.url = url;
        return this;
    }

    @Override
    public IHttpService header(Object header) {
        this.header = header;
        return this;
    }

    @Override
    public IHttpService body(Object body) {
        this.body = body;
        return this;
    }


    @Override
    public IHttpService method(Method method) {
        this.method = method;
        return this;
    }

    @Override
    public IHttpService clazz(Class<?> clazz) {
        this.clazz = clazz;
        return this;
    }

    @Override
    public <T> IHttpService listener(IHttpListener<T> listener) {
        this.listener = listener;
        return this;
    }

    @Override
    public IHttpService headerListener(IHeaderListener headerListener) {
        this.headerListener = headerListener;
        return this;
    }

    @Override
    public boolean checkParameters() {
        if (url == null) {
            Log.w("OnHttp", "url is null");
            return false;
        }
        if (clazz == null) {
            Log.w("OnHttp", "clazz is null");
            return false;
        }
        if (listener == null) {
            Log.w("OnHttp", "listener is null");
            return false;
        }
        serviceListener = new HttpServiceListener<>(listener, clazz);
        return true;
    }
}

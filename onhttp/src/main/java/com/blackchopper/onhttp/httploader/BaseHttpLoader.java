package com.blackchopper.onhttp.httploader;

import android.util.Log;

import com.blackchopper.onhttp.core.IHeaderListener;
import com.blackchopper.onhttp.core.BaseLoader;
import com.blackchopper.onhttp.core.ILoaderListener;
import com.blackchopper.onhttp.core.Method;

/**
 * author  : Black Chopper
 * e-mail  : 4884280@qq.com
 * github  : http://github.com/BlackChopper
 * project : OnHttp
 */

public abstract class BaseHttpLoader extends BaseLoader implements IHttpLoader {
    protected Method method = null;
    protected IHttpListener listener = null;
    protected IHeaderListener headerListener = null;

    protected Class<?> clazz = null;
    protected ILoaderListener serviceListener;



    @Override
    public IHttpLoader url(String url) {
        this.url = url;
        return this;
    }

    @Override
    public IHttpLoader header(Object header) {
        this.header = header;
        return this;
    }

    @Override
    public IHttpLoader body(Object body) {
        this.body = body;
        return this;
    }


    @Override
    public IHttpLoader method(Method method) {
        this.method = method;
        return this;
    }

    @Override
    public IHttpLoader clazz(Class<?> clazz) {
        this.clazz = clazz;
        return this;
    }

    @Override
    public <T> IHttpLoader listener(IHttpListener<T> listener) {
        this.listener = listener;
        return this;
    }

    @Override
    public IHttpLoader headerListener(IHeaderListener headerListener) {
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
        serviceListener = new HttpLoaderListener<>(listener, clazz);
        return true;
    }
}

package com.hacknife.onhttp.uploader;

import android.util.Log;

import com.hacknife.onhttp.core.BaseLoader;

import java.io.File;

/**
 * author  : Hacknife
 * e-mail  : 4884280@qq.com
 * github  : http://github.com/hacknife
 * project : OnHttp
 */

public abstract class BaseUpLoader extends BaseLoader implements IUpLoader {
    protected IUpListener listener;
    protected File file;
    protected Class<?> clazz;
    protected UpLoaderListener upServiceListener;


    @Override
    public IUpLoader url(String url) {
        this.url = url;
        return this;
    }

    @Override
    public IUpLoader header(Object header) {
        this.header = header;
        return this;
    }

    @Override
    public IUpLoader body(Object body) {
        this.body = body;
        return this;
    }

    @Override
    public IUpLoader file(File file) {
        this.file = file;
        return this;
    }

    @Override
    public IUpLoader clazz(Class<?> clazz) {
        this.clazz = clazz;
        return this;
    }

    @Override
    public IUpLoader listener(IUpListener listener) {
        this.listener = listener;
        return this;
    }

    @Override
    public boolean checkParameters() {
        if (url == null) {
            Log.w("OnHttp", "url is null");
            return false;
        }

        if (listener == null) {
            Log.w("OnHttp", "listener is null");
            return false;
        }
        if (!file.exists()) {
            Log.w("OnHttp", "file is not exists");
            return false;
        }
        upServiceListener = new UpLoaderListener(listener, clazz);
        return true;
    }


}

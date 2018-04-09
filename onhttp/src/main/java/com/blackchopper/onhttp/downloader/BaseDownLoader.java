package com.blackchopper.onhttp.downloader;

import android.util.Log;

import com.blackchopper.onhttp.core.BaseLoader;

import java.io.File;

/**
 * author  : Black Chopper
 * e-mail  : 4884280@qq.com
 * github  : http://github.com/BlackChopper
 * project : OnHttp
 */

public abstract class BaseDownLoader extends BaseLoader implements IDownLoader {
    protected File file = null;
    protected IDownListener listener;
    protected DownLoaderListener downServiceListener;


    @Override
    public IDownLoader url(String url) {
        this.url = url;
        return this;
    }

    @Override
    public IDownLoader header(Object header) {
        this.header = header;
        return this;
    }

    @Override
    public IDownLoader body(Object body) {
        this.body = body;
        return this;
    }

    @Override
    public IDownLoader listener(IDownListener listener) {
        this.listener = listener;
        return this;
    }

    @Override
    public IDownLoader file(File file) {
        this.file = file;
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
        if (file == null) {
            Log.w("OnHttp", "file is null");
            return false;
        }
        downServiceListener = new DownLoaderListener(listener, file);
        return true;
    }


}

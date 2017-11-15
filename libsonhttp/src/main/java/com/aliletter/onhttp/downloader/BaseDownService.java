package com.aliletter.onhttp.downloader;

import android.util.Log;

import com.aliletter.onhttp.core.BaseService;
import com.aliletter.onhttp.httploader.IHttpService;

import java.io.File;

/**
 * Author: aliletter
 * Github: http://github.com/aliletter
 * Data: 2017/11/15.
 */

public abstract class BaseDownService extends BaseService implements IDownService {
    protected File file = null;
    protected IDownListener listener;
    protected DownServiceListener downServiceListener;


    @Override
    public IDownService url(String url) {
        this.url = url;
        return this;
    }

    @Override
    public IDownService header(Object header) {
        this.header = header;
        return this;
    }

    @Override
    public IDownService body(Object body) {
        this.body = body;
        return this;
    }

    @Override
    public IDownService listener(IDownListener listener) {
        this.listener = listener;
        return this;
    }

    @Override
    public IDownService file(File file) {
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
        downServiceListener = new DownServiceListener(listener, file);
        return true;
    }


}

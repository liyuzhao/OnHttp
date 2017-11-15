package com.aliletter.onhttp.uploader;

import android.util.Log;

import com.aliletter.onhttp.core.BaseService;
import com.aliletter.onhttp.core.IBaseService;

import java.io.File;

/**
 * Author: aliletter
 * Github: http://github.com/aliletter
 * Data: 2017/11/15.
 */

public abstract class BaseUpservice extends BaseService implements IUpService {
    protected IUpListener listener;
    protected File file;
    protected Class<?> clazz;
    protected UpServiceListener upServiceListener;


    @Override
    public IUpService url(String url) {
        this.url = url;
        return this;
    }

    @Override
    public IUpService header(Object header) {
        this.header = header;
        return this;
    }

    @Override
    public IUpService body(Object body) {
        this.body = body;
        return this;
    }

    @Override
    public IUpService file(File file) {
        this.file = file;
        return this;
    }

    @Override
    public IUpService clazz(Class<?> clazz) {
        this.clazz = clazz;
        return this;
    }

    @Override
    public IUpService listener(IUpListener listener) {
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
        upServiceListener = new UpServiceListener(listener, clazz);
        return true;
    }


}

package com.aliletter.onhttp.core;

import java.net.HttpURLConnection;
import java.util.concurrent.FutureTask;

/**
 * Author: aliletter
 * Github: http://github.com/aliletter
 * Data: 2017/11/15.
 */

public abstract class BaseLoader implements IBaseLoader {
    protected HttpURLConnection urlConnection;
    protected String url = null;
    protected Object header = null;
    protected Object body = null;




    @Override
    public void run() {
        if (!checkParameters())return;
         RequstNetWork();
    }


    protected abstract void RequstNetWork();

    @Override
    public void build() {
        ThreadPoolManager.getInstance().excute(new FutureTask<Object>(this, null));
    }
}

package com.blackchopper.onhttp.core;

import java.net.HttpURLConnection;
import java.util.concurrent.FutureTask;

/**
 * author  : Black Chopper
 * e-mail  : 4884280@qq.com
 * github  : http://github.com/BlackChopper
 * project : OnHttp
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
    public void executor() {
        ThreadPoolManager.getInstance().excute(new FutureTask<Object>(this, null));
    }
}

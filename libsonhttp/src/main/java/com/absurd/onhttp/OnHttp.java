package com.absurd.onhttp;

import com.absurd.onhttp.base.IHttpListener;
import com.absurd.onhttp.base.IServiceListener;
import com.absurd.onhttp.base.ServiceListener;
import com.absurd.onhttp.base.ThreadPoolManager;

import java.util.Map;
import java.util.concurrent.FutureTask;

/**
 * Created by 段泽全 on 2017/9/4.
 * BLog：http://blog.csdn.net/mr_absurd
 * Emile:4884280@qq.com
 */

public class OnHttp {
    public final static int GET = 0;
    public final static int POST = 1;
    public static OnHttp instance;
    private Map<String, String> mHeaders;
    private Map<String, String> mBody;
    private String mUrl;
    private int mMethod = 1;
    private IHttpListener mHttpListener;
    private Class<?> t;
    private boolean mCacheHeader = false;

    public static OnHttp getInstance() {
        if (instance == null) {
            synchronized (OnHttp.class) {
                if (instance == null)
                    instance = new OnHttp();
            }
        }
        return instance;
    }

    public OnHttp url(String url) {
        this.mUrl = url;
        return instance;
    }

    public OnHttp headers(Map<String, String> header) {
        this.mHeaders = header;
        return instance;
    }

    public OnHttp body(Map<String, String> body) {
        mBody = body;
        return instance;
    }

    public OnHttp listener(IHttpListener listener) {
        mHttpListener = listener;
        return instance;
    }

    public OnHttp method(int method) {
        mMethod = method;
        return instance;
    }

    public OnHttp cacheHeader(boolean cache) {
        mCacheHeader = cache;
        return instance;
    }

    public OnHttp clazz(Class<?> t) {
        this.t = t;
        return instance;
    }

    public void excute() {
        sendRequest(mUrl, mMethod, mHeaders, mBody, t, mHttpListener);
        clear();
    }

    private void clear() {
        if (!mCacheHeader)
            this.mHeaders = null;
        this.mBody = null;
        this.mUrl = "";
        this.mHttpListener = null;
        mMethod = 1;
    }


    private <T, M> void sendRequest(String url, int method, Map<String, String> header, Map<String, String> body, Class<T> clazz, IHttpListener<M> httpListener) {
        IServiceListener serviceListener = new ServiceListener(clazz, httpListener);
        HttpTask httpTask = new HttpTask(url, method, header, body, serviceListener);
        ThreadPoolManager.getInstance().excute(new FutureTask<Object>(httpTask, null));
    }

}

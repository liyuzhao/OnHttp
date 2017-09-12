package com.absurd.onhttp;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;

import com.absurd.onhttp.base.BitmapServiceListener;
import com.absurd.onhttp.base.FileServiceListener;
import com.absurd.onhttp.base.IHeaderListener;
import com.absurd.onhttp.base.IHttpListener;
import com.absurd.onhttp.base.IServiceListener;
import com.absurd.onhttp.base.ServiceListener;
import com.absurd.onhttp.base.ThreadPoolManager;
import com.absurd.onhttp.base.UpdataServiceListener;
import com.absurd.onhttp.cache.BitmapCache;
import com.absurd.onhttp.cache.LRUCache;

import java.io.File;
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
    private static OnHttp instance;
    private Map<String, String> mHeaders;
    private Map<String, String> mBody;
    private String mUrl;
    private int mMethod = 1;
    private IHttpListener mHttpListener;
    private Class<?> t;
    private boolean mCacheHeader = false;
    private ImageView mView;
    private File mFile;
    private int mResId = 0;
    private boolean mIsUpdataFile = false;
    private IHeaderListener mHeaderListener;
    private Handler handler = new Handler(Looper.getMainLooper());

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

    public OnHttp view(ImageView view) {
        mView = view;
        method(GET);
        clazz(Bitmap.class);
        return instance;
    }

    public OnHttp updata(boolean isUpdata) {
        mIsUpdataFile = isUpdata;
        return instance;
    }

    public OnHttp id(int resid) {
        mResId = resid;
        return instance;
    }

    public OnHttp file(File file) {
        mFile = file;
        method(GET);
        clazz(File.class);
        return instance;
    }

    public OnHttp headerListener(IHeaderListener listener) {
        mHeaderListener = listener;
        return instance;
    }

    public void excute() {
        loadDefault();
        if (!checkParam(mView, mUrl, t, mFile,mIsUpdataFile)) return;
        sendRequest(mView, mUrl, mMethod, mHeaders, mBody, mIsUpdataFile, t, mFile, mHttpListener, mHeaderListener);
        clear();
    }

    private void loadDefault() {
        if (mResId != 0 & mView != null) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    mView.setImageResource(mResId);
                }
            });
        }
    }

    private boolean checkParam(final ImageView view, String url, Class<?> t, File file, boolean isupdata) {
        if (url.equalsIgnoreCase("")) {
            Log.e("OnHttp", "url is null");
            return false;
        }
        if (t == null) {
            Log.e("OnHttp", "class is null");
            return false;
        }
        if (file != null) {
            if (file.exists() && isupdata == false) {
                Log.e("OnHttp", "file is exists (" + file.getAbsolutePath() + ")");
                return false;
            }
        }
        if (view != null) {
            url = url.replace("/", "_").replace(":", "-");
            if (LRUCache.getInstance().exists(url)) {
                final Bitmap bitmap = LRUCache.getInstance().get(url);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        view.setImageBitmap(bitmap);
                    }
                });
                return false;
            } else if (BitmapCache.getInstance().exists(url)) {
                final Bitmap bitmap = BitmapCache.getInstance().get(url);
                LRUCache.getInstance().put(url, bitmap);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        view.setImageBitmap(bitmap);
                    }
                });
                return false;
            }
        }
        return true;
    }

    private void clear() {
        if (!mCacheHeader)
            this.mHeaders = null;
        this.mBody = null;
        this.mUrl = "";
        this.mHttpListener = null;
        mMethod = 1;
        mResId = 0;
        mIsUpdataFile = false;
        mView = null;
        mFile = null;
    }


    private <T, M> void sendRequest(ImageView view, String url, int method, Map<String, String> header, Map<String, String> body, boolean isUpdata, Class<T> clazz, File file, IHttpListener<M> httpListener, IHeaderListener headerListener) {
        IServiceListener serviceListener;
        if (view != null) {
            serviceListener = new BitmapServiceListener(view, httpListener, url);
            Log.v("TAG", "BitmapServiceListener");
        } else if (isUpdata == true && file != null) {
            serviceListener = new UpdataServiceListener(clazz, httpListener);
            Log.v("TAG", "UpdataServiceListener");
        } else if (file != null) {
            serviceListener = new FileServiceListener(file, httpListener);
            Log.v("TAG", "FileServiceListener");
        } else {
            serviceListener = new ServiceListener(clazz, httpListener);
            Log.v("TAG", "ServiceListener");
        }
        HttpTask httpTask = new HttpTask(url, method, header, body, isUpdata, file, serviceListener, headerListener);
        ThreadPoolManager.getInstance().excute(new FutureTask<Object>(httpTask, null));
    }

}

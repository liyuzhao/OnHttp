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
import com.absurd.onhttp.base.base.IDownloadListener;
import com.absurd.onhttp.cache.BitmapCache;
import com.absurd.onhttp.cache.LRUCache;
import com.absurd.onhttp.imageloader.core.DisplayImageOptions;
import com.absurd.onhttp.imageloader.core.ImageLoader;
import com.absurd.onhttp.util.OnHttpUtil;

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
    private IDownloadListener mDownloadListener;
    private Handler handler = new Handler(Looper.getMainLooper());

    public static OnHttp getInstance() {
        return new OnHttp();
    }

    public OnHttp url(String url) {
        this.mUrl = url;
        return this;
    }

    public OnHttp headers(Map<String, String> header) {
        this.mHeaders = OnHttpUtil.javaBeanToMap(header);
        return this;
    }

    public OnHttp downloadListener(IDownloadListener listener) {
        this.mDownloadListener = listener;
        return this;
    }

    public OnHttp body(Object body) {
        mBody = OnHttpUtil.javaBeanToMap(body);
        return this;
    }

    public OnHttp listener(IHttpListener listener) {
        mHttpListener = listener;
        return this;
    }

    public OnHttp method(int method) {
        mMethod = method;
        return this;
    }

    public OnHttp cacheHeader(boolean cache) {
        mCacheHeader = cache;
        return this;
    }

    public OnHttp clazz(Class<?> t) {
        this.t = t;
        return this;
    }

    public OnHttp view(ImageView view) {
        mView = view;
        method(GET);
        clazz(Bitmap.class);
        return this;
    }

    public OnHttp updata(boolean isUpdata) {
        mIsUpdataFile = isUpdata;
        return this;
    }

    public OnHttp id(int resid) {
        mResId = resid;
        return this;
    }

    public OnHttp file(File file) {
        mFile = file;
        method(GET);
        clazz(File.class);
        return this;
    }

    public OnHttp headerListener(IHeaderListener listener) {
        mHeaderListener = listener;
        return this;
    }

    public void excute() {
        if (checkParam(mView, mUrl, t, mFile, mResId, mIsUpdataFile, mHttpListener)) {
            sendRequest(mView, mUrl, mMethod, mHeaders, mBody, mIsUpdataFile, t, mFile, mHttpListener, mHeaderListener, mDownloadListener);
        }
     }


    private boolean checkParam(final ImageView view, String url, Class<?> t, File file, int resid, boolean isupdata, IHttpListener httpListener) {
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
                Log.w("OnHttp", "file is exists (" + file.getAbsolutePath() + ")");
                if (httpListener != null) {
                    httpListener.onSuccess(file);
                }
                return false;
            }
        }
        if (view != null) {
            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    .showImageOnLoading(resid)
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .build();
            ImageLoader.getInstance().displayImage(url, view, options);
            return false;
        }
        return true;
    }


    private <T, M> void sendRequest(ImageView view, String url, int method, Map<String, String> header, Map<String, String> body, boolean isUpdata, Class<T> clazz, File file, IHttpListener<M> httpListener, IHeaderListener headerListener, IDownloadListener downloadListener) {
        IServiceListener serviceListener;
        if (view != null) {
            serviceListener = new BitmapServiceListener(view, httpListener, url);
        } else if (isUpdata == true && file != null) {
            serviceListener = new ServiceListener(clazz, httpListener);
        } else if (file != null) {
            serviceListener = new FileServiceListener(file, httpListener, downloadListener);
        } else {
            serviceListener = new ServiceListener(clazz, httpListener);
        }
        HttpTask httpTask = new HttpTask(url, method, header, body, isUpdata, file, serviceListener, headerListener);
        ThreadPoolManager.getInstance().excute(new FutureTask<Object>(httpTask, null));
    }

}

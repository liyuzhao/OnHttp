package com.aliletter.onhttp.base;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import com.aliletter.onhttp.cache.BitmapCache;
import com.aliletter.onhttp.cache.LRUCache;
import com.aliletter.onhttp.entity.CacheData;
import com.aliletter.onhttp.util.OnHttpUtil;

import java.io.InputStream;

/**
 * Author: aliletter
 * Github: http://github.com/aliletter
 * Data: 2017/9/7.
 */

public class BitmapServiceListener<T> implements IServiceListener {
    private ImageView view;
    private Handler hander = new Handler(Looper.getMainLooper());
    private IHttpListener<T> listener;
    private String url;

    public BitmapServiceListener(ImageView view, IHttpListener<T> listener, String url) {
        this.view = view;
        this.listener = listener;
        this.url = url;
    }

    @Override
    public void onSuccess(InputStream inputStream) {
        final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        String key = OnHttpUtil.url2FileName(url);
        LRUCache.getInstance().put(key, bitmap);
        BitmapCache.getInstance().put(new CacheData(key, bitmap));
        hander.post(new Runnable() {
            @Override
            public void run() {
                view.setImageBitmap(bitmap);
                if (listener != null)
                    listener.onSuccess((T) bitmap);
            }
        });
    }

    @Override
    public void error(int code) {
        if (listener != null)
            listener.onError(code);
    }
}

package com.absurd.onhttp.base;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import com.absurd.onhttp.cache.BitmapCache;
import com.absurd.onhttp.cache.LRUCache;
import com.absurd.onhttp.entity.CacheData;

import java.io.InputStream;

/**
 * Author: mr-absurd
 * Github: http://github.com/mr-absurd
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
        LRUCache.getInstance().put(url.replace("/","_").replace(":","-"), bitmap);
        BitmapCache.getInstance().put(new CacheData(url.replace("/","_").replace(":","-"), bitmap));
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

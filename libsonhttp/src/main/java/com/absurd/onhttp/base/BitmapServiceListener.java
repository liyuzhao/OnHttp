package com.absurd.onhttp.base;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

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

    public BitmapServiceListener(ImageView view, IHttpListener<T> listener) {
        this.view = view;
        this.listener = listener;
    }

    @Override
    public void onSuccess(InputStream inputStream) {
        final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
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

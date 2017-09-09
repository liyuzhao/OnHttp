package com.absurd.onhttp.base;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import com.absurd.onhttp.util.JsonUtil;
import com.absurd.onhttp.util.StringUtil;

import java.io.InputStream;


/**
 * Created by 段泽全 on 2017/9/4.
 * BLog：http://blog.csdn.net/mr_absurd
 * Emile:4884280@qq.com
 */

public class ServiceListener<T> implements IServiceListener {
    private Class<?> T;
    private IHttpListener<T> listener;
    private Handler hander = new Handler(Looper.getMainLooper());
    private final static String STRING_NAME = "java.lang.String";
    private final static String BITMAP_NAME = "android.graphics.Bitmap";
    private final static String FILE_NAME = "java.io.File";

    public ServiceListener(Class<?> T, IHttpListener<T> listener) {
        this.T = T;
        this.listener = listener;

    }

    @Override
    public void onSuccess(InputStream inputStream) {

        T respone = null;
        if (T.getName().equalsIgnoreCase(STRING_NAME)) {
            respone = getString(inputStream);
        } else if (T.getName().equalsIgnoreCase(BITMAP_NAME)) {
            respone = getBitmap(inputStream);
        } else if (T.getName().equalsIgnoreCase(FILE_NAME)) {
        } else {
            respone = getJson(inputStream);
        }
        final T finalRespone = respone;
        hander.post(new Runnable() {
            @Override
            public void run() {
                if (listener != null)
                    listener.onSuccess(finalRespone);
            }
        });
    }

    private T getBitmap(InputStream inputStream) {
        return (T) BitmapFactory.decodeStream(inputStream);
    }

    private T getString(InputStream inputStream) {
        return (T) StringUtil.streamToString(inputStream);
    }

    private T getJson(InputStream inputStream) {
        T respone = null;
        try {
            respone = (T) JsonUtil.fromJsonString(StringUtil.streamToString(inputStream), T);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return respone;
    }

    @Override
    public void error(int code) {
        if (listener != null)
            listener.onError(code);
    }
}

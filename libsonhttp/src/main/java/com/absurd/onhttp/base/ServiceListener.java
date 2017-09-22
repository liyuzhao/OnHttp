package com.absurd.onhttp.base;
import com.absurd.onhttp.base.base.BaseServiceListener;


import java.io.InputStream;


/**
 * Created by 段泽全 on 2017/9/4.
 * BLog：http://blog.csdn.net/mr_absurd
 * Emile:4884280@qq.com
 */

public class ServiceListener<T> extends BaseServiceListener {


    public ServiceListener(Class<?> T, IHttpListener<T> listener) {
        this.T = T;
        this.listener = listener;

    }

    @Override
    public void onSuccess(InputStream inputStream) {

        T respone = null;
        if (T.getName().equalsIgnoreCase(STRING_NAME)) {
            respone = (T) getString(inputStream);
        } else if (T.getName().equalsIgnoreCase(BITMAP_NAME)) {
            respone = (T) getBitmap(inputStream);
        } else if (T.getName().equalsIgnoreCase(FILE_NAME)) {
        } else {
            respone = (T) getJson(inputStream);
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



    @Override
    public void error(int code) {
        if (listener != null)
            listener.onError(code);
    }
}

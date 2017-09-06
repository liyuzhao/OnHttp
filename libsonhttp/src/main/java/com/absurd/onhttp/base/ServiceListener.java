package com.absurd.onhttp.base;

import android.os.Handler;
import android.os.Looper;

import com.absurd.onhttp.util.JsonUtil;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by 段泽全 on 2017/9/4.
 * BLog：http://blog.csdn.net/mr_absurd
 * Emile:4884280@qq.com
 */

public class ServiceListener<T> implements IServiceListener {
    private Class<?> T;
    private IHttpListener<T> listener;
    private Handler hander = new Handler(Looper.getMainLooper());

    public ServiceListener(Class<?> T, IHttpListener<T> listener) {
        this.T = T;
        T.getName();
        this.listener = listener;

    }

    @Override
    public void onSuccess(InputStream inputStream) {
        final String content = getContent(inputStream);
        T respone = null;
        try {
            respone = (T) JsonUtil.fromJsonString(content, T);
        } catch (Exception e) {
            e.printStackTrace();
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

    private String getContent(InputStream inputStream) {
        String content = null;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder builder = new StringBuilder();
            String line = null;
            try {
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

    @Override
    public void error(int code) {
        listener.onError(code);
    }
}

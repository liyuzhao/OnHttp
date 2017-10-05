package com.absurd.onhttp.base.base;

import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;

import com.absurd.onhttp.base.IHttpListener;
import com.absurd.onhttp.base.IServiceListener;
import com.absurd.onhttp.util.OnHttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;

/**
 * Author: mr-absurd
 * Github: http://github.com/mr-absurd
 * Data: 2017/9/12.
 */

public class BaseServiceListener<T> implements IServiceListener {
    protected Class<?> T;
    protected IHttpListener<T> listener;
    protected Handler hander = new Handler(Looper.getMainLooper());
    protected final static String STRING_NAME = "java.lang.String";
    protected final static String BITMAP_NAME = "android.graphics.Bitmap";
    protected final static String FILE_NAME = "java.io.File";
    protected final static String JSONOBJECT_NAME="org.json.JSONObject";
    @Override
    public void onSuccess(InputStream inputStream) {

    }

    @Override
    public void error(int code) {

    }


    protected T getBitmap(InputStream inputStream) {
        return (T) BitmapFactory.decodeStream(inputStream);
    }

    protected T getString(InputStream inputStream) {
        return (T) OnHttpUtil.streamToString(inputStream);
    }

    protected T getJson(InputStream inputStream) {
        T respone = null;
        try {
            respone = (T) OnHttpUtil.fromJsonString(OnHttpUtil.streamToString(inputStream), T);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return respone;
    }

    public T getJSONObject(String s) {
        JSONObject object = null;
        try {
            object = new JSONObject(s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return (T) object;
    }
}

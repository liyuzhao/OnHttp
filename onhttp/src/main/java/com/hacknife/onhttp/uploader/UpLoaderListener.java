package com.hacknife.onhttp.uploader;

import com.hacknife.onhttp.core.BaseLoaderListener;
import com.hacknife.onhttp.util.OnHttpUtil;

import java.io.IOException;
import java.io.InputStream;

/**
 * author  : Hacknife
 * e-mail  : 4884280@qq.com
 * github  : http://github.com/hacknife
 * project : OnHttp
 */

public class UpLoaderListener<T> extends BaseLoaderListener {
    private IUpListener listener;
    private Class<?> clazz;
    private float mFileLength;

    public UpLoaderListener(IUpListener<T> listener, Class<?> clazz) {
        this.listener = listener;
        this.clazz = clazz;
    }

    public void setFileLength(float fileLength) {
        this.mFileLength = fileLength;
    }

    public void onProgress(int len) {
        listener.onProgress(len / mFileLength);
    }

    @Override
    public void onSuccess(final InputStream inputStream) {
        final T response = (T) OnHttpUtil.responseFromInputStream(inputStream, clazz);
        hander.post(new Runnable() {
            @Override
            public void run() {
                listener.onSuccess(response);
            }
        });
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void error(int code) {

    }
}

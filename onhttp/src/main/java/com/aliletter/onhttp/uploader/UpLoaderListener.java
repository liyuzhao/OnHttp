package com.aliletter.onhttp.uploader;

import com.aliletter.onhttp.core.BaseLoaderListener;
import com.aliletter.onhttp.util.OnHttpUtil;

import java.io.IOException;
import java.io.InputStream;

/**
 * Author: aliletter
 * Github: http://github.com/aliletter
 * Data: 2017/11/15.
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

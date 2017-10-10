package com.absurd.onhttp.base;

import android.util.Log;

import com.absurd.onhttp.base.base.BaseServiceListener;
import com.absurd.onhttp.base.base.IDownloadListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Author: mr-absurd
 * Github: http://github.com/mr-absurd
 * Data: 2017/9/7.
 */

public class FileServiceListener<T> extends BaseServiceListener {
    private File file;
    private int size;
    private IDownloadListener downloadListener;

    public FileServiceListener(File file, IHttpListener<T> listener, IDownloadListener downloadListener) {
        this.file = file;
        this.listener = listener;
        this.downloadListener = downloadListener;
    }

    public void setDataSize(int size) {
        this.size = size;
    }

    @Override
    public void onSuccess(InputStream inputStream) {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            float currentSize = 0;
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                currentSize = currentSize + len;
                if (downloadListener != null) {
                    downloadListener.onProgress(currentSize / size * 100);
                }
                fos.write(buffer, 0, len);
            }
            hander.post(new Runnable() {
                @Override
                public void run() {
                    if (listener != null)
                        listener.onSuccess((T) file);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void error(int code) {
        if (listener != null)
            listener.onError(code);
    }
}

package com.hacknife.onhttp.downloader;

import com.hacknife.onhttp.core.BaseLoaderListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * author  : Hacknife
 * e-mail  : 4884280@qq.com
 * github  : http://github.com/hacknife
 * project : OnHttp
 */

public class DownLoaderListener extends BaseLoaderListener {
    private IDownListener mListener;
    private File mFile;
    private float mFileLength;

    public DownLoaderListener(IDownListener mListener, File mFile) {
        this.mListener = mListener;
        this.mFile = mFile;
    }

    public void setFileLength(float length) {
        mFileLength = length;
    }

    @Override
    public void onSuccess(InputStream inputStream) {
        try {
            final FileOutputStream fos = new FileOutputStream(mFile);
            byte[] buffer = new byte[1024];
            float currentSize = 0;
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                currentSize = currentSize + len;
                if (mListener != null) {
                    mListener.onProgress(currentSize / mFileLength * 100);
                }
                fos.write(buffer, 0, len);
            }
            hander.post(new Runnable() {
                @Override
                public void run() {
                    mListener.onSuccess(mFile);
                }
            });
            fos.close();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void error(final int code) {
        hander.post(new Runnable() {
            @Override
            public void run() {
                mListener.onError(code);
            }
        });
    }
}

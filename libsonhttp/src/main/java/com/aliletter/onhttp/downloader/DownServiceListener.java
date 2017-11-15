package com.aliletter.onhttp.downloader;

import com.aliletter.onhttp.core.BaseServiceListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Author: aliletter
 * Github: http://github.com/aliletter
 * Data: 2017/11/15.
 */

public class DownServiceListener extends BaseServiceListener {
    private IDownListener mListener;
    private File mFile;
    private float mFileLength;

    public DownServiceListener(IDownListener mListener, File mFile) {
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

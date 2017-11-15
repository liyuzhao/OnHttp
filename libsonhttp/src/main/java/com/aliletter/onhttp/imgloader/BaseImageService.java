package com.aliletter.onhttp.imgloader;

import android.util.Log;
import android.widget.ImageView;

/**
 * Author: aliletter
 * Github: http://github.com/aliletter
 * Data: 2017/11/15.
 */

public class BaseImageService implements IImageService {
    protected String url;
    private ImageView view;
    private Integer defaultId;
    private Integer errorId;


    @Override
    public IImageService url(String url) {
        this.url = url;
        return this;
    }

    @Override
    public IImageService view(ImageView view) {
        this.view = view;
        return this;
    }

    @Override
    public IImageService defaultId(int id) {
        this.defaultId = id;
        return this;
    }

    @Override
    public IImageService errorId(int id) {
        this.errorId = id;
        return this;
    }

    @Override
    public boolean checkParameters() {
        if (url == null) {
            Log.w("OnHttp", "url is null");
            return false;
        }
        if (view == null) {
            Log.w("OnHttp", "view is null");
            return false;
        }
        return true;
    }

    @Override
    public void build() {

    }

    @Override
    public void run() {

    }
}

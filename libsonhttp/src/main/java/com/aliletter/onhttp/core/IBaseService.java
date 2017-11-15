package com.aliletter.onhttp.core;

import android.graphics.Bitmap;

import com.aliletter.onhttp.httploader.IHttpService;

/**
 * Author: aliletter
 * Github: http://github.com/aliletter
 * Data: 2017/11/15.
 */

public interface IBaseService extends Runnable {



    void build();

    boolean checkParameters();


}

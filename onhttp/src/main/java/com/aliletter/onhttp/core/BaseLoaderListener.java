package com.aliletter.onhttp.core;

import android.os.Handler;
import android.os.Looper;

/**
 * Author: aliletter
 * Github: http://github.com/aliletter
 * Data: 2017/11/15.
 */

public abstract class BaseLoaderListener implements ILoaderListener {
    protected Handler hander = new Handler(Looper.getMainLooper());
}
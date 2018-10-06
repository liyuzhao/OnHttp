package com.hacknife.onhttp.core;

import android.os.Handler;
import android.os.Looper;

/**
 * author  : Hacknife
 * e-mail  : 4884280@qq.com
 * github  : http://github.com/hacknife
 * project : OnHttp
 */

public abstract class BaseLoaderListener implements ILoaderListener {
    protected Handler hander = new Handler(Looper.getMainLooper());
}

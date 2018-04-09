package com.blackchopper.onhttp.core;

import android.os.Handler;
import android.os.Looper;

/**
 * author  : Black Chopper
 * e-mail  : 4884280@qq.com
 * github  : http://github.com/BlackChopper
 * project : OnHttp
 */

public abstract class BaseLoaderListener implements ILoaderListener {
    protected Handler hander = new Handler(Looper.getMainLooper());
}

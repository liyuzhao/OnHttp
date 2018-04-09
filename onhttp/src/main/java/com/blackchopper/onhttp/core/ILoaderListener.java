package com.blackchopper.onhttp.core;

import java.io.InputStream;

/**
 * author  : Black Chopper
 * e-mail  : 4884280@qq.com
 * github  : http://github.com/BlackChopper
 * project : OnHttp
 */

public interface ILoaderListener {
    void onSuccess(InputStream inputStream);

    void error(int code);
}

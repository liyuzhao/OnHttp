package com.hacknife.onhttp.core;

import java.io.InputStream;

/**
 * author  : Hacknife
 * e-mail  : 4884280@qq.com
 * github  : http://github.com/hacknife
 * project : OnHttp
 */

public interface ILoaderListener {
    void onSuccess(InputStream inputStream);

    void error(int code);
}

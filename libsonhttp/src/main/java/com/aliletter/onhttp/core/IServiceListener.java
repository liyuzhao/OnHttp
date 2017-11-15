package com.aliletter.onhttp.core;

import java.io.InputStream;

/**
 * Created by 段泽全 on 2017/9/4.
 * BLog：http://blog.csdn.net/mr_absurd
 * Emile:4884280@qq.com
 */

public interface IServiceListener {
    void onSuccess(InputStream inputStream);

    void error(int code);
}

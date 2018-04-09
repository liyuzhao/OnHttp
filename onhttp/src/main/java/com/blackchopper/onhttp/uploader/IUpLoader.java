package com.blackchopper.onhttp.uploader;

import com.blackchopper.onhttp.core.IBaseLoader;

import java.io.File;

/**
 * author  : Black Chopper
 * e-mail  : 4884280@qq.com
 * github  : http://github.com/BlackChopper
 * project : OnHttp
 */

public interface IUpLoader extends IBaseLoader {
    IUpLoader url(String url);

    IUpLoader header(Object header);

    IUpLoader body(Object body);

    IUpLoader file(File file);

    IUpLoader listener(IUpListener listener);

    IUpLoader clazz(Class<?> clazz);
}

package com.hacknife.onhttp.uploader;

import com.hacknife.onhttp.core.IBaseLoader;

import java.io.File;

/**
 * author  : Hacknife
 * e-mail  : 4884280@qq.com
 * github  : http://github.com/hacknife
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

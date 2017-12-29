package com.aliletter.onhttp.uploader;

import com.aliletter.onhttp.core.IBaseLoader;

import java.io.File;

/**
 * Author: aliletter
 * Github: http://github.com/aliletter
 * Data: 2017/11/15.
 */

public interface IUpLoader extends IBaseLoader {
    IUpLoader url(String url);

    IUpLoader header(Object header);

    IUpLoader body(Object body);

    IUpLoader file(File file);

    IUpLoader listener(IUpListener listener);

    IUpLoader clazz(Class<?> clazz);
}

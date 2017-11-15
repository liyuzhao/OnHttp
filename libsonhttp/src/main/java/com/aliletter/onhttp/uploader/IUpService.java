package com.aliletter.onhttp.uploader;

import com.aliletter.onhttp.core.IBaseService;

import java.io.File;

/**
 * Author: aliletter
 * Github: http://github.com/aliletter
 * Data: 2017/11/15.
 */

public interface IUpService extends IBaseService {
    IUpService url(String url);

    IUpService header(Object header);

    IUpService body(Object body);

    IUpService file(File file);

    IUpService listener(IUpListener listener);

    IUpService clazz(Class<?> clazz);
}

package com.aliletter.onhttp.downloader;

import com.aliletter.onhttp.core.IBaseService;

import java.io.File;

/**
 * Author: aliletter
 * Github: http://github.com/aliletter
 * Data: 2017/11/15.
 */

public interface IDownService extends IBaseService {
    IDownService url(String url);

    IDownService header(Object header);

    IDownService body(Object body);

    IDownService listener(IDownListener listener);

    IDownService file(File file);
}

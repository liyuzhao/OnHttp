package com.aliletter.onhttp.downloader;

import com.aliletter.onhttp.core.IBaseLoader;

import java.io.File;

/**
 * Author: aliletter
 * Github: http://github.com/aliletter
 * Data: 2017/11/15.
 */

public interface IDownLoader extends IBaseLoader {
    IDownLoader url(String url);

    IDownLoader header(Object header);

    IDownLoader body(Object body);

    IDownLoader listener(IDownListener listener);

    IDownLoader file(File file);
}

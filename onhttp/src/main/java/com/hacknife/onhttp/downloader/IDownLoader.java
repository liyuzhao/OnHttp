package com.hacknife.onhttp.downloader;

import com.hacknife.onhttp.core.IBaseLoader;

import java.io.File;

/**
 * author  : Hacknife
 * e-mail  : 4884280@qq.com
 * github  : http://github.com/hacknife
 * project : OnHttp
 */

public interface IDownLoader extends IBaseLoader {
    IDownLoader url(String url);

    IDownLoader header(Object header);

    IDownLoader body(Object body);

    IDownLoader listener(IDownListener listener);

    IDownLoader file(File file);
}

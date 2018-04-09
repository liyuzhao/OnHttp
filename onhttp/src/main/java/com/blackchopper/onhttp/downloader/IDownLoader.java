package com.blackchopper.onhttp.downloader;

import com.blackchopper.onhttp.core.IBaseLoader;

import java.io.File;

/**
 * author  : Black Chopper
 * e-mail  : 4884280@qq.com
 * github  : http://github.com/BlackChopper
 * project : OnHttp
 */

public interface IDownLoader extends IBaseLoader {
    IDownLoader url(String url);

    IDownLoader header(Object header);

    IDownLoader body(Object body);

    IDownLoader listener(IDownListener listener);

    IDownLoader file(File file);
}

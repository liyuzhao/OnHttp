package com.hacknife.onhttp.downloader;

import java.io.File;

/**
 * author  : Hacknife
 * e-mail  : 4884280@qq.com
 * github  : http://github.com/hacknife
 * project : OnHttp
 */

public interface IDownListener {

    void onProgress(float progress);

    void onSuccess(File file);

    void onError(int code);
}

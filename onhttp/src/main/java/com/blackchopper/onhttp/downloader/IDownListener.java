package com.blackchopper.onhttp.downloader;

import java.io.File;

/**
 * author  : Black Chopper
 * e-mail  : 4884280@qq.com
 * github  : http://github.com/BlackChopper
 * project : OnHttp
 */

public interface IDownListener {

    void onProgress(float progress);

    void onSuccess(File file);

    void onError(int code);
}

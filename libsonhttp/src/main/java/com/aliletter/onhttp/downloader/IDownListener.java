package com.aliletter.onhttp.downloader;

import java.io.File;

/**
 * Author: aliletter
 * Github: http://github.com/aliletter
 * Data: 2017/11/15.
 */

public interface IDownListener {

    void onProgress(float progress);

    void onSuccess(File file);

    void onError(int code);
}

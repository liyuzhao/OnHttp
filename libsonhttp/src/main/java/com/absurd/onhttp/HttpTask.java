package com.absurd.onhttp;


import com.absurd.onhttp.base.HttpService;
import com.absurd.onhttp.base.IHeaderListener;
import com.absurd.onhttp.base.IHttpService;
import com.absurd.onhttp.base.IServiceListener;
import com.absurd.onhttp.base.UpdataService;

import java.io.File;
import java.util.Map;

/**
 * Created by 段泽全 on 2017/9/4.
 * BLog：http://blog.csdn.net/mr_absurd
 * Emile:4884280@qq.com
 */

class HttpTask implements Runnable {
    private IHttpService httpService;

    public HttpTask(String url, int method, Map<String, String> header, Map<String, String> body, boolean isUpdata, File file, IServiceListener listener, IHeaderListener headerListener) {
        if (isUpdata)
            this.httpService = new UpdataService();
        else
            this.httpService = new HttpService();
        httpService.serUrl(url);
        httpService.setMethod(method);
        httpService.setheader(header);
        httpService.setBody(body);
        httpService.setHttpCallBack(listener);
        httpService.setHeaderCallBack(headerListener);
        httpService.setFile(file);
    }

    @Override
    public void run() {
        httpService.excute();
    }
}

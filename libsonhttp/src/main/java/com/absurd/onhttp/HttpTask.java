package com.absurd.onhttp;


import com.absurd.onhttp.base.HttpService;
import com.absurd.onhttp.base.IHttpService;
import com.absurd.onhttp.base.IServiceListener;

import java.util.Map;

/**
 * Created by 段泽全 on 2017/9/4.
 * BLog：http://blog.csdn.net/mr_absurd
 * Emile:4884280@qq.com
 */

public class HttpTask implements Runnable {
    private IHttpService httpService;

    public HttpTask(String url, int method, Map<String, String> header, Map<String, String> body, IServiceListener listener) {
        this.httpService = new HttpService();
        httpService.serUrl(url);
        httpService.setMethod(method);
        httpService.setheader(header);
        httpService.setBody(body);
        httpService.setHttpCallBack(listener);

    }
    @Override
    public void run() {
        httpService.excute();
    }
}

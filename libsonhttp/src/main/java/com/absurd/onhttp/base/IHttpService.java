package com.absurd.onhttp.base;

import java.io.File;
import java.io.InputStream;
import java.util.Map;

/**
 * Created by 段泽全 on 2017/9/4.
 * BLog：http://blog.csdn.net/mr_absurd
 * Emile:4884280@qq.com
 */

public interface IHttpService {
    void serUrl(String url);

    void setheader(Map<String, String> header);

    void setBody(Map<String, String> body);

    void excute();

    void setHttpCallBack(IServiceListener listener);

    void setMethod(int method);

    void setHeaderCallBack(IHeaderListener listener);

    void setFile(File file);

    InputStream loadLoacal();
}

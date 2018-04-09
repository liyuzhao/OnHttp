package com.blackchopper.onhttp.httploader;

import com.blackchopper.onhttp.core.IHeaderListener;
import com.blackchopper.onhttp.core.IBaseLoader;
import com.blackchopper.onhttp.core.Method;

/**
 * author  : Black Chopper
 * e-mail  : 4884280@qq.com
 * github  : http://github.com/BlackChopper
 * project : OnHttp
 */

public interface IHttpLoader extends IBaseLoader {
    IHttpLoader url(String url);

    IHttpLoader header(Object header);

    IHttpLoader body(Object body);

    IHttpLoader clazz(Class<?> clazz);

    IHttpLoader method(Method method);

    <T> IHttpLoader listener(IHttpListener<T> listener);

    IHttpLoader headerListener(IHeaderListener headerListener);

}

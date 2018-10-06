package com.hacknife.onhttp.httploader;

import com.hacknife.onhttp.core.IHeaderListener;
import com.hacknife.onhttp.core.IBaseLoader;
import com.hacknife.onhttp.core.Method;

/**
 * author  : Hacknife
 * e-mail  : 4884280@qq.com
 * github  : http://github.com/hacknife
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

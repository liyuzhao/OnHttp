package com.aliletter.onhttp.httploader;

import com.aliletter.onhttp.core.IHeaderListener;
import com.aliletter.onhttp.core.IBaseLoader;
import com.aliletter.onhttp.core.Method;

/**
 * Author: aliletter
 * Github: http://github.com/aliletter
 * Data: 2017/11/15.
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

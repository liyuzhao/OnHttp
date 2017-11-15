package com.aliletter.onhttp.httploader;

import com.aliletter.onhttp.core.IHeaderListener;
import com.aliletter.onhttp.core.IBaseService;
import com.aliletter.onhttp.core.Method;

/**
 * Author: aliletter
 * Github: http://github.com/aliletter
 * Data: 2017/11/15.
 */

public interface IHttpService extends IBaseService {
    IHttpService url(String url);

    IHttpService header(Object header);

    IHttpService body(Object body);

    IHttpService clazz(Class<?> clazz);

    IHttpService method(Method method);

    <T> IHttpService listener(IHttpListener<T> listener);

    IHttpService headerListener(IHeaderListener headerListener);

}

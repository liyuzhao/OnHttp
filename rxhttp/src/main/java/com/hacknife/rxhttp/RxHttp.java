package com.hacknife.rxhttp;

import com.hacknife.onhttp.OnHttp;
import com.hacknife.onhttp.core.Method;
import com.hacknife.onhttp.httploader.IHttpListener;



import java.lang.reflect.ParameterizedType;
import java.util.Map;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * author  : Hacknife
 * e-mail  : 4884280@qq.com
 * github  : http://github.com/hacknife
 * project : OnHttp
 */

public abstract class RxHttp<T> implements ObservableOnSubscribe<T>, IHttpListener<T> {
    protected ObservableEmitter<T> emitter;


    public RxHttp() {
        OnHttp.httpLoader()
                .body(body())
                .url(url())
                .method(method())
                .header(header())
                .clazz(response())
                .listener(this)
                .executor();
    }

    protected Map<String, String> header() {
        return null;
    }

    public abstract String url();

    public Map<String, String> body() {
        return null;
    }

    public Method method() {
        return Method.POST;
    }

    public Class<?> response() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public void subscribe(ObservableEmitter<T> emitter) throws Exception {
        this.emitter = emitter;
    }

    @Override
    public void onError(int code) {
        emitter.onError(new Throwable(String.valueOf(code)));
    }

    @Override
    public void onSuccess(T t) {
        emitter.onNext(t);
    }
}

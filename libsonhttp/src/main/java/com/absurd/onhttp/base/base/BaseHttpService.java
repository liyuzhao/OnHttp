package com.absurd.onhttp.base.base;

import com.absurd.onhttp.base.IHeaderListener;
import com.absurd.onhttp.base.IHttpService;
import com.absurd.onhttp.base.IServiceListener;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * Author: mr-absurd
 * Github: http://github.com/mr-absurd
 * Data: 2017/9/12.
 */

public abstract class BaseHttpService implements IHttpService {
    protected IServiceListener mListener;
    protected IHeaderListener mHeaderListener;
    protected URL mUrl;
    protected Map<String, String> mBody;
    protected Map<String, String> mHeader;
    protected HttpURLConnection mUrlConnection;
    protected byte[] mPostData;
    protected File mFile;
    protected int mMethod;

    @Override
    public void serUrl(String url) {
        try {
            this.mUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setheader(Map<String, String> header) {
        this.mHeader = header;
    }

    @Override
    public void setBody(Map<String, String> body) {
        this.mBody = body;
    }

    @Override
    public void excute() {

    }

    @Override
    public void setHttpCallBack(IServiceListener listener) {
        this.mListener = listener;
    }

    @Override
    public void setMethod(int method) {
        this.mMethod = method;
    }

    @Override
    public void setHeaderCallBack(IHeaderListener listener) {
        mHeaderListener = listener;
    }

    @Override
    public void setFile(File file) {
        mFile = file;
    }
}

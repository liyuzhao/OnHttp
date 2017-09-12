package com.absurd.onhttp.base;

import com.absurd.onhttp.base.base.BaseHttpService;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by 段泽全 on 2017/9/4.
 * BLog：http://blog.csdn.net/mr_absurd
 * Emile:4884280@qq.com
 */

public class HttpService extends BaseHttpService {

    @Override
    public void excute() {
        if (mMethod == 0) get();
        else post();
        InputStream in = exec();
        if (mListener != null && in != null) {
            mListener.onSuccess(in);
            mUrlConnection.disconnect();
        }

    }

    private void get() {
        try {
            StringBuilder tempParams = new StringBuilder();
            int pos = 0;
            String requestUrl = String.valueOf(mUrl);
            if (mBody != null) {
                for (String key : mBody.keySet()) {
                    if (pos > 0) {
                        tempParams.append("&");
                    }
                    tempParams.append(String.format("%s=%s", key, mBody.get(key)));
                    pos++;
                }
                requestUrl = requestUrl + "?" + tempParams.toString();
            }

            URL turl = new URL(requestUrl);
            mUrlConnection = (HttpURLConnection) turl.openConnection();
            mUrlConnection.setConnectTimeout(5 * 1000);
            mUrlConnection.setReadTimeout(5 * 1000);
            mUrlConnection.setUseCaches(true);
            mUrlConnection.setRequestMethod("GET");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void post() {
        try {
            StringBuilder tempParams = new StringBuilder();
            int pos = 0;
            if (mBody != null) {
                for (String key : mBody.keySet()) {
                    if (pos > 0) {
                        tempParams.append("&");
                    }
                    tempParams.append(String.format("%s=%s", key, URLEncoder.encode(mBody.get(key), "utf-8")));
                    pos++;
                }
            }
            String params = tempParams.toString();
            // 请求的参数转换为byte数组
            mPostData = params.getBytes();
            // 打开一个HttpURLConnection连接
            mUrlConnection = (HttpURLConnection) mUrl.openConnection();
            // 设置连接超时时间
            mUrlConnection.setConnectTimeout(5 * 1000);
            //设置从主机读取数据超时
            mUrlConnection.setReadTimeout(5 * 1000);
            // Post请求必须设置允许输出 默认false
            mUrlConnection.setDoOutput(true);
            //设置请求允许输入 默认是true
            mUrlConnection.setDoInput(true);
            // Post请求不能使用缓存
            mUrlConnection.setUseCaches(false);
            // 设置为Post请求
            mUrlConnection.setRequestMethod("POST");
            //设置本次连接是否自动处理重定向
            mUrlConnection.setInstanceFollowRedirects(true);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private InputStream exec() {
        InputStream inputStream = null;
        try {
            if (mHeader != null) {
                for (Map.Entry<String, String> map : mHeader.entrySet()) {
                    mUrlConnection.addRequestProperty(map.getKey(), map.getValue());
                }
            }
            if (mMethod == 1) {
                DataOutputStream dos = new DataOutputStream(mUrlConnection.getOutputStream());
                dos.write(mPostData);
                dos.flush();
                dos.close();
            }
            mUrlConnection.connect();
            if (mUrlConnection.getResponseCode() == 200) {
                if (mHeaderListener != null)
                    mHeaderListener.onHeader(mUrlConnection.getHeaderFields());
                inputStream = mUrlConnection.getInputStream();
            } else {
                mListener.error(mUrlConnection.getResponseCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputStream;
    }
}

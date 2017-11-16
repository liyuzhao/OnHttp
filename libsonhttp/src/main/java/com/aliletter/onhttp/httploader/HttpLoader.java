package com.aliletter.onhttp.httploader;

import com.aliletter.onhttp.core.Method;
import com.aliletter.onhttp.util.OnHttpUtil;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * Author: aliletter
 * Github: http://github.com/aliletter
 * Data: 2017/11/15.
 */

public class HttpLoader extends BaseHttpLoader {
    @Override
    protected void RequstNetWork() {
        try {
            URL url ;
            if (method == Method.GET) {
                url = new URL(this.url + OnHttpUtil.bodyTUrl(body));
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setConnectTimeout(5 * 1000);
                urlConnection.setReadTimeout(5 * 1000);
                urlConnection.setUseCaches(true);
                urlConnection.setRequestMethod("GET");
            } else {
                url = new URL(this.url);
                urlConnection = (HttpURLConnection) url.openConnection();
                // 设置连接超时时间
                urlConnection.setConnectTimeout(5 * 1000);
                //设置从主机读取数据超时
                urlConnection.setReadTimeout(5 * 1000);
                // Post请求必须设置允许输出 默认false
                urlConnection.setDoOutput(true);
                //设置请求允许输入 默认是true
                urlConnection.setDoInput(true);
                // Post请求不能使用缓存
                urlConnection.setUseCaches(false);
                // 设置为Post请求
                urlConnection.setRequestMethod("POST");
                //设置本次连接是否自动处理重定向
                urlConnection.setInstanceFollowRedirects(true);
            }
            for (Map.Entry<String, String> entry : OnHttpUtil.javaBeanToMap(header).entrySet()) {
                urlConnection.addRequestProperty(entry.getKey(), entry.getValue());
            }
            if (method == Method.POST) {
                DataOutputStream dos = new DataOutputStream(urlConnection.getOutputStream());
                dos.write(OnHttpUtil.body2Byte(body));
                dos.flush();
                dos.close();
            }
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                if (headerListener != null)
                    headerListener.onHeader(urlConnection.getHeaderFields());
                serviceListener.onSuccess(urlConnection.getInputStream());
            } else {
                serviceListener.error(urlConnection.getResponseCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

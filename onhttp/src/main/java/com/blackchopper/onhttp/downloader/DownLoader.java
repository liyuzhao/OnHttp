package com.blackchopper.onhttp.downloader;

import com.blackchopper.onhttp.util.OnHttpUtil;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * author  : Black Chopper
 * e-mail  : 4884280@qq.com
 * github  : http://github.com/BlackChopper
 * project : OnHttp
 */

public class DownLoader extends BaseDownLoader implements IDownLoader {
    @Override
    protected void RequstNetWork() {
        try {
            URL url = new URL(this.url + OnHttpUtil.bodyTUrl(body));
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(5 * 1000);
            urlConnection.setReadTimeout(5 * 1000);
            urlConnection.setUseCaches(true);
            urlConnection.setRequestMethod("GET");
            for (Map.Entry<String, String> entry : OnHttpUtil.javaBeanToMap(header).entrySet()) {
                urlConnection.addRequestProperty(entry.getKey(), entry.getValue());
            }
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                downServiceListener.setFileLength(Integer.parseInt(urlConnection.getHeaderField("Content-Length")));
                downServiceListener.onSuccess(urlConnection.getInputStream());
            } else {
                listener.onError(urlConnection.getResponseCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

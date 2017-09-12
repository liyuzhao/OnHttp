package com.absurd.onhttp.base;

import android.util.Log;

import com.absurd.onhttp.base.base.BaseHttpService;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: mr-absurd
 * Github: http://github.com/mr-absurd
 * Data: 2017/9/11.
 */

public class UpdataService extends BaseHttpService {


    @Override
    public void excute() {
        Log.d("TAG", "excute");
        try {
            mUrlConnection = (HttpURLConnection) mUrl.openConnection();
            mUrlConnection.setDoOutput(true);
            //设置该连接允许写入
            mUrlConnection.setDoInput(true);
            //设置不能适用缓存
            mUrlConnection.setUseCaches(false);
            //设置连接超时时间
            mUrlConnection.setConnectTimeout(5 * 1000);   //设置连接超时时间
            //设置读取超时时间
            mUrlConnection.setReadTimeout(5 * 1000);   //读取超时
            //设置连接方法post
            mUrlConnection.setRequestMethod("POST");
            //设置维持长连接
            mUrlConnection.setRequestProperty("connection", "Keep-Alive");
            //设置文件字符集
            mUrlConnection.setRequestProperty("Accept-Charset", "UTF-8");
            //设置文件类型
            mUrlConnection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + "*****");
            String name = mFile.getName();
            DataOutputStream requestStream = new DataOutputStream(mUrlConnection.getOutputStream());
            requestStream.writeBytes("--" + "*****" + "\r\n");

            //发送文件参数信息
            StringBuilder tempParams = new StringBuilder();
            tempParams.append("Content-Disposition: form-data; name=\"" + name + "\"; filename=\"" + name + "\"; ");
            if (mBody != null) {
                int pos = 0;
                int size = mBody.size();
                for (String key : mBody.keySet()) {
                    tempParams.append(String.format("%s=\"%s\"", key, mBody.get(key), "utf-8"));
                    if (pos < size - 1) {
                        tempParams.append("; ");
                    }
                    pos++;
                }
            }
            tempParams.append("\r\n");
            tempParams.append("Content-Type: application/octet-stream\r\n");
            tempParams.append("\r\n");
            String params = tempParams.toString();
            requestStream.writeBytes(params);
            //发送文件数据
            FileInputStream fileInput = new FileInputStream(mFile);
            int bytesRead;
            byte[] buffer = new byte[1024];
            DataInputStream in = new DataInputStream(new FileInputStream(mFile));
            while ((bytesRead = in.read(buffer)) != -1) {
                requestStream.write(buffer, 0, bytesRead);
            }
            requestStream.writeBytes("\r\n");
            requestStream.flush();
            requestStream.writeBytes("--" + "*****" + "--" + "\r\n");
            requestStream.flush();
            fileInput.close();
            int statusCode = mUrlConnection.getResponseCode();
            if (statusCode == 200) {
                // 获取返回的数据
                if (mHeaderListener != null)
                    mHeaderListener.onHeader(mUrlConnection.getHeaderFields());
                mListener.onSuccess(mUrlConnection.getInputStream());
            } else {
                mListener.error(statusCode);
            }
        } catch (Exception e) {
            Log.e("TAG", e.toString());
            e.printStackTrace();
        }
    }

}

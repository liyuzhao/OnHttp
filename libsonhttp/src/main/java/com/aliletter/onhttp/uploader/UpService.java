package com.aliletter.onhttp.uploader;

import com.aliletter.onhttp.util.OnHttpUtil;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.UUID;

/**
 * Author: aliletter
 * Github: http://github.com/aliletter
 * Data: 2017/11/15.
 */

public class UpService extends BaseUpservice implements IUpService {
    private static final String BOUNDARY = UUID.randomUUID().toString();
    private static final String PREFIX = "--";
    private static final String LINE_END = "\r\n";
    private static final String CONTENT_TYPE = "multipart/form-data";

    @Override
    protected void RequstNetWork() {
        try {
            URL url = new URL(this.url);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            //设置该连接允许写入
            urlConnection.setDoInput(true);
            //设置不能适用缓存
            urlConnection.setUseCaches(false);
            //设置连接超时时间
            urlConnection.setConnectTimeout(5 * 1000);   //设置连接超时时间
            //设置读取超时时间
            urlConnection.setReadTimeout(5 * 1000);   //读取超时
            //设置连接方法post
            urlConnection.setRequestMethod("POST");
            urlConnection.setInstanceFollowRedirects(true);
            //设置维持长连接
            urlConnection.setRequestProperty("connection", "Keep-Alive");
            //设置文件字符集
            //    mUrlConnection.setRequestProperty("Charset", "UTF-8");
            urlConnection.setRequestProperty("Accept-Charset", "UTF-8");
            //设置文件类型
            urlConnection.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);

            DataOutputStream dos = new DataOutputStream(urlConnection.getOutputStream());
            StringBuilder builder = new StringBuilder();
            Map<String, String> mBody = OnHttpUtil.javaBeanToMap(body);
            if (mBody != null && mBody.size() > 0) {
                for (Map.Entry<String, String> entry : mBody.entrySet()) {
                    builder.append(PREFIX).append(BOUNDARY).append(LINE_END);
                    builder.append("Content-Disposition: form-data; name=\"").append(entry.getKey()).append("\"").append(LINE_END);
                    builder.append("Content-Type: text/plain; charset=UTF-8").append(LINE_END);
                    builder.append("Content-Transfer-Encoding: 8bit");
                    builder.append(LINE_END).append(LINE_END);
                    builder.append(entry.getValue()).append(LINE_END);
                    dos.write(builder.toString().getBytes());
                    builder.delete(0, builder.length());
                }
            }

            builder.append(PREFIX).append(BOUNDARY).append(LINE_END);
            builder.append("Content-Disposition:form-data; name=\"" + "file" + "\"; filename=\"" + file.getAbsolutePath() + "\"" + LINE_END);
            // 这里配置的Content-type很重要的 ，用于服务器端辨别文件的类型的
            builder.append("Content-Type: application/octet-stream").append(LINE_END);
            builder.append("Content-Transfer-Encoding: binary");
            builder.append(LINE_END).append(LINE_END);
            dos.write(builder.toString().getBytes());
            /**上传文件*/
            InputStream is = new FileInputStream(file);
            upServiceListener.setFileLength(file.length());
            byte[] bytes = new byte[1024];
            int len = 0;
            int curLen = 0;
            while ((len = is.read(bytes)) != -1) {
                curLen += len;
                dos.write(bytes, 0, len);
                upServiceListener.onProgress(curLen);
            }
            is.close();

            dos.write(LINE_END.getBytes());
            byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END).getBytes();
            dos.write(end_data);
            dos.flush();

            int statusCode = urlConnection.getResponseCode();
            if (statusCode == 200) {
                upServiceListener.onSuccess(urlConnection.getInputStream());
            } else {
                upServiceListener.error(statusCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

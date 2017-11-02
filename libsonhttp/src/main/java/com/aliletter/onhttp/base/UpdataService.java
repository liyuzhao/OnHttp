package com.aliletter.onhttp.base;

import com.aliletter.onhttp.base.base.BaseHttpService;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.Map;
import java.util.UUID;

/**
 * Author: aliletter
 * Github: http://github.com/aliletter
 * Data: 2017/9/11.
 */

public class UpdataService extends BaseHttpService {
    private static final String BOUNDARY = UUID.randomUUID().toString();
    private static final String PREFIX = "--";
    private static final String LINE_END = "\r\n";
    private static final String CONTENT_TYPE = "multipart/form-data";

    @Override
    public void excute() {
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
            mUrlConnection.setInstanceFollowRedirects(true);
            //设置维持长连接
            mUrlConnection.setRequestProperty("connection", "Keep-Alive");
            //设置文件字符集
            //    mUrlConnection.setRequestProperty("Charset", "UTF-8");
            mUrlConnection.setRequestProperty("Accept-Charset", "UTF-8");
            //设置文件类型
            mUrlConnection.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);

            DataOutputStream dos = new DataOutputStream(mUrlConnection.getOutputStream());
            StringBuilder builder = new StringBuilder();
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
            builder.append("Content-Disposition:form-data; name=\"" + "file" + "\"; filename=\"" + mFile.getAbsolutePath() + "\"" + LINE_END);
            // 这里配置的Content-type很重要的 ，用于服务器端辨别文件的类型的
            builder.append("Content-Type: application/octet-stream").append(LINE_END);
            builder.append("Content-Transfer-Encoding: binary");
            builder.append(LINE_END).append(LINE_END);
            dos.write(builder.toString().getBytes());
            /**上传文件*/
            InputStream is = new FileInputStream(mFile);
            //  onUploadProcessListener.initUpload((int)file.length());
            byte[] bytes = new byte[1024];
            int len = 0;
            int curLen = 0;
            while ((len = is.read(bytes)) != -1) {
                curLen += len;
                dos.write(bytes, 0, len);
                //     onUploadProcessListener.onUploadProcess(curLen);
            }
            is.close();

            dos.write(LINE_END.getBytes());
            byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END).getBytes();
            dos.write(end_data);
            dos.flush();

            int statusCode = mUrlConnection.getResponseCode();
            if (statusCode == 200) {
                if (mHeaderListener != null) {
                    mHeaderListener.onHeader(mUrlConnection.getHeaderFields());
                }
                mListener.onSuccess(mUrlConnection.getInputStream());
            } else {
                mListener.error(statusCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

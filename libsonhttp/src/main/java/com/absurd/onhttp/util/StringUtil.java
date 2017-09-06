package com.absurd.onhttp.util;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;


/**
 * Author: mr-absurd
 * Github: http://github.com/mr-absurd
 * Data: 2017/9/5.
 */

public class StringUtil {

    public static String streamToString(InputStream is) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = is.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            baos.close();
            is.close();
            byte[] byteArray = baos.toByteArray();
            return new String(byteArray);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

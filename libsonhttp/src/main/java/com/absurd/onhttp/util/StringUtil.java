package com.absurd.onhttp.util;

import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * Author: mr-absurd
 * Github: http://github.com/mr-absurd
 * Data: 2017/9/5.
 */

public class StringUtil {

    public static String streamToString(InputStream inputStream) {
        String content = null;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder builder = new StringBuilder();
            String line = null;
            try {
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }
}

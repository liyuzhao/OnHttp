package com.absurd.onhttp.util;

import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;


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

    public static Map<String, String> javaBeanToMap(Object o) {
        if (o.getClass().getName().endsWith(".Map") | o.getClass().getName().endsWith("HashMap"))
            return (Map<String, String>) o;
        Map<String, String> map = new HashMap<>();
        Field[] fields = o.getClass().getDeclaredFields();
        System.out.print(o.getClass().getName());
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                if (null != field.get(o)){
                    if (!field.getName().contains("serialVersionUID"))
                    map.put(field.getName(), field.get(o).toString());}
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return map;
    }
}

package com.absurd.onhttp.util;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Author: mr-absurd
 * Github: http://github.com/mr-absurd
 * Data: 2017/9/5.
 */

public class OnHttpUtil {
    public static Object fromJsonString(String content, Class<?> clazz) {
        Object obj = null;
        try {
            obj = clazz.newInstance();
            JSONObject json = new JSONObject(content);
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (field.getName().contains("serialVersionUID")|field.getName().contains("$change")) continue;
                field.setAccessible(true);
                if (json.isNull(field.getName())) continue;
                setField(obj, field, json);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return obj;
    }

    public static Object newObject(Class<?> clazz, JSONObject json) {
        Object obj = null;
        try {
            obj = clazz.newInstance();
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (field.getName().contains("serialVersionUID")|field.getName().contains("$change")) continue;
                field.setAccessible(true);
                if (json.isNull(field.getName())) continue;
                setField(obj, field, json);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    private static void setField(Object obj, Field field, JSONObject jsonObject) throws JSONException, IllegalAccessException {
        if (jsonObject.optJSONArray(field.getName()) != null) {
            Class<?> clazz = getTypeFromList(field);
            if (clazz != null) {
                JSONArray array = jsonObject.getJSONArray(field.getName());
                ArrayList list = new ArrayList<>();
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    list.add(newObject(clazz, object));
                }
                field.set(obj, list);
            }
        } else if (jsonObject.optJSONObject(field.getName()) != null) {
            field.set(obj, newObject(field.getType(), jsonObject.optJSONObject(field.getName())));
        } else if (Integer.class == field.getType()) {
            field.set(obj, jsonObject.getInt(field.getName()));
        } else if (String.class == field.getType()) {
            field.set(obj, jsonObject.getString(field.getName()));
        } else if (Long.class == field.getType()) {
            field.set(obj, jsonObject.getLong(field.getName()));
        } else if (Float.class == field.getType()) {
        } else if (Double.class == field.getType()) {
            field.set(obj, jsonObject.getDouble(field.getName()));
        } else if (Boolean.class == field.getType()) {
            field.set(obj, jsonObject.getBoolean(field.getName()));
        } else if (Short.class == field.getType()) {
        } else if (Byte.class == field.getType()) {
        } else if (int.class == field.getType()) {
            field.set(obj, jsonObject.getInt(field.getName()));
        } else if (long.class == field.getType()) {
            field.set(obj, jsonObject.getLong(field.getName()));
        } else if (double.class == field.getType()) {
            field.set(obj, jsonObject.getDouble(field.getName()));
        } else if (boolean.class == field.getType()) {
            field.set(obj, jsonObject.getBoolean(field.getName()));
        } else if (short.class == field.getType()) {

        }

    }

    private static Class<?> getTypeFromList(Field field) {
        if (field.getType().isAssignableFrom(List.class)) {
            Type type = field.getGenericType();
            if (type == null) return null;
            if (type instanceof ParameterizedType) {
                ParameterizedType parameterType = (ParameterizedType) type;
                return (Class) parameterType.getActualTypeArguments()[0];
            }
        }
        return null;
    }


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
                    if (!field.getName().contains("serialVersionUID")|field.getName().contains("$change"))
                        map.put(field.getName(), field.get(o).toString());}
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static String url2FileName(String url){
        return url.replace("/", "_").replace(":", "-").replace(".","-");
    }
}

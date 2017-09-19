package com.absurd.onhttp.util;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * Author: mr-absurd
 * Github: http://github.com/mr-absurd
 * Data: 2017/9/5.
 */

public class JsonUtil {
    public static Object fromJsonString(String content, Class<?> clazz) {
        Object obj = null;
        try {
            obj = clazz.newInstance();
            JSONObject json = new JSONObject(content);
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (field.getName().contains("serialVersionUID")) continue;
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

    public static Object newObject(Class<?> clazz, JSONObject json ) {
        Object obj = null;
        try {
            obj = clazz.newInstance();
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (field.getName().contains("serialVersionUID")) continue;
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
        if (jsonObject.optJSONObject(field.getName()) != null) {
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
}

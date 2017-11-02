package com.aliletter.onhttp.entity;

import android.graphics.Bitmap;


/**
 * Author: aliletter
 * Github: http://github.com/aliletter
 * Data: 2017/9/9.
 */

public class CacheData {
    private String key;
    private Bitmap value;

    public CacheData(String key, Bitmap value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Bitmap getValue() {
        return value;
    }

    public void setValue(Bitmap value) {
        this.value = value;
    }
}

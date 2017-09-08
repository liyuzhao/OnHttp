package com.absurd.onhttp.util;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Author: mr-absurd
 * Github: http://github.com/mr-absurd
 * Data: 2017/9/8.
 */

public class LRUCache {
    private static final float factor = 0.75f; //扩容因子
    private Map<String, Bitmap> map; //数据存储容器
    private int cacheSize;//缓存大小

    public LRUCache(int cacheSize) {
        this.cacheSize = cacheSize;
        int capacity = (int) Math.ceil(cacheSize / factor) + 1;
        map = new LinkedHashMap<String, Bitmap>(capacity, factor, true) {
            private static final long serialVersionUID = 1L;

            /**
             * 重写LinkedHashMap的removeEldestEntry()固定table中链表的长度
             **/
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, Bitmap> eldest) {
                boolean todel = size() > LRUCache.this.cacheSize;
                return todel;
            }
        };
    }

    /**
     * 根据key获取value
     *
     * @param key
     * @return value
     **/
    public synchronized Bitmap get(String key) {
        return map.get(key);
    }

    /**
     * put一个key-value
     *
     * @param key value
     **/
    public synchronized void put(String key, Bitmap value) {
        map.put(key, value);
    }

    /**
     * 根据key来删除一个缓存
     *
     * @param key
     **/
    public synchronized void remove(String key) {
        map.remove(key);
    }

    /**
     * 清空缓存
     **/
    public synchronized void clear() {
        map.clear();
    }

    /**
     * 已经使用缓存的大小
     **/
    public synchronized int cacheSize() {
        return map.size();
    }

    /**
     * 获取缓存中所有的键值对
     **/
    public synchronized Collection<Map.Entry<String, Bitmap>> getAll() {
        return new ArrayList<Map.Entry<String, Bitmap>>(map.entrySet());
    }
}
package com.absurd.onhttp.cache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.absurd.onhttp.entity.CacheData;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Author: mr-absurd
 * Github: http://github.com/mr-absurd
 * Data: 2017/9/8.
 */

public class BitmapCache {
    private static volatile BitmapCache instance = null;
    private File mCacheDir;

    public static BitmapCache getInstance() {
        if (instance == null) {
            synchronized (BitmapCache.class) {
                if (instance == null)
                    throw new RuntimeException("You must first implement a parameter constructor in BitmapCache before you use OnHttp !");
            }
        }
        return instance;
    }

    public static BitmapCache getInstance(String path) {
        if (instance == null) {
            synchronized (BitmapCache.class) {
                if (instance == null)
                    instance = new BitmapCache(path);
            }
        }
        return instance;
    }

    public BitmapCache(String path) {
        mCacheDir = new File(path);
        if (!mCacheDir.exists()) {
            mCacheDir.mkdirs();
        }
    }


    public synchronized void put(CacheData cache) {
        File bitmapFile = new File(mCacheDir, cache.getKey());
        try {
            FileOutputStream out = new FileOutputStream(bitmapFile);
            cache.getValue().compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized Bitmap get(String key) {
        File bitmapFile = new File(mCacheDir, key);
        Bitmap bitmap = null;
        if (!bitmapFile.exists()) return null;
        try {
            bitmap = BitmapFactory.decodeFile(bitmapFile.getAbsolutePath(), getBitmapOption(1));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    private BitmapFactory.Options getBitmapOption(int inSampleSize) {
        System.gc();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPurgeable = true;
        options.inSampleSize = inSampleSize;
        return options;
    }

    public int size() {
        return mCacheDir.list().length;
    }

    public synchronized boolean exists(String key) {
        File bitmap = new File(mCacheDir, key);
        return bitmap.exists();
    }

    public void clear() {
        File[] files = mCacheDir.listFiles();
        for (File file : files) {
            file.delete();
        }
    }
}

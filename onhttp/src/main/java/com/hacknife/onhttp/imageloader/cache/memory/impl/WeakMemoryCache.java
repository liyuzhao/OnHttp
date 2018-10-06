
package com.hacknife.onhttp.imageloader.cache.memory.impl;

import android.graphics.Bitmap;

import com.hacknife.onhttp.imageloader.cache.memory.BaseMemoryCache;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;


public class WeakMemoryCache extends BaseMemoryCache {
	@Override
	protected Reference<Bitmap> createReference(Bitmap value) {
		return new WeakReference<Bitmap>(value);
	}
}


package com.aliletter.onhttp.imageloader.cache.memory.impl;

import android.graphics.Bitmap;

import com.aliletter.onhttp.imageloader.cache.memory.LimitedMemoryCache;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


public class UsingFreqLimitedMemoryCache extends LimitedMemoryCache {
	
	private final Map<Bitmap, Integer> usingCounts = Collections.synchronizedMap(new HashMap<Bitmap, Integer>());

	public UsingFreqLimitedMemoryCache(int sizeLimit) {
		super(sizeLimit);
	}

	@Override
	public boolean put(String key, Bitmap value) {
		if (super.put(key, value)) {
			usingCounts.put(value, 0);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Bitmap get(String key) {
		Bitmap value = super.get(key);
		// Increment usage count for value if value is contained in hardCahe
		if (value != null) {
			Integer usageCount = usingCounts.get(value);
			if (usageCount != null) {
				usingCounts.put(value, usageCount + 1);
			}
		}
		return value;
	}

	@Override
	public Bitmap remove(String key) {
		Bitmap value = super.get(key);
		if (value != null) {
			usingCounts.remove(value);
		}
		return super.remove(key);
	}

	@Override
	public void clear() {
		usingCounts.clear();
		super.clear();
	}

	@Override
	protected int getSize(Bitmap value) {
		return value.getRowBytes() * value.getHeight();
	}

	@Override
	protected Bitmap removeNext() {
		Integer minUsageCount = null;
		Bitmap leastUsedValue = null;
		Set<Entry<Bitmap, Integer>> entries = usingCounts.entrySet();
		synchronized (usingCounts) {
			for (Entry<Bitmap, Integer> entry : entries) {
				if (leastUsedValue == null) {
					leastUsedValue = entry.getKey();
					minUsageCount = entry.getValue();
				} else {
					Integer lastValueUsage = entry.getValue();
					if (lastValueUsage < minUsageCount) {
						minUsageCount = lastValueUsage;
						leastUsedValue = entry.getKey();
					}
				}
			}
		}
		usingCounts.remove(leastUsedValue);
		return leastUsedValue;
	}

	@Override
	protected Reference<Bitmap> createReference(Bitmap value) {
		return new WeakReference<Bitmap>(value);
	}
}

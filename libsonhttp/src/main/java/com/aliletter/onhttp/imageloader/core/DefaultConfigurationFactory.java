
package com.aliletter.onhttp.imageloader.core;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build;

import com.aliletter.onhttp.imageloader.cache.disc.DiskCache;
import com.aliletter.onhttp.imageloader.cache.disc.impl.UnlimitedDiskCache;
import com.aliletter.onhttp.imageloader.cache.disc.impl.ext.LruDiskCache;
import com.aliletter.onhttp.imageloader.cache.disc.naming.FileNameGenerator;
import com.aliletter.onhttp.imageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.aliletter.onhttp.imageloader.cache.memory.MemoryCache;
import com.aliletter.onhttp.imageloader.cache.memory.impl.LruMemoryCache;
import com.aliletter.onhttp.imageloader.core.assist.QueueProcessingType;
import com.aliletter.onhttp.imageloader.core.assist.deque.LIFOLinkedBlockingDeque;
import com.aliletter.onhttp.imageloader.core.decode.BaseImageDecoder;
import com.aliletter.onhttp.imageloader.core.decode.ImageDecoder;
import com.aliletter.onhttp.imageloader.core.display.BitmapDisplayer;
import com.aliletter.onhttp.imageloader.core.display.SimpleBitmapDisplayer;
import com.aliletter.onhttp.imageloader.core.download.BaseImageDownloader;
import com.aliletter.onhttp.imageloader.core.download.ImageDownloader;
import com.aliletter.onhttp.imageloader.utils.L;
import com.aliletter.onhttp.imageloader.utils.StorageUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


public class DefaultConfigurationFactory {

	
	public static Executor createExecutor(int threadPoolSize, int threadPriority,
                                          QueueProcessingType tasksProcessingType) {
		boolean lifo = tasksProcessingType == QueueProcessingType.LIFO;
		BlockingQueue<Runnable> taskQueue =
				lifo ? new LIFOLinkedBlockingDeque<Runnable>() : new LinkedBlockingQueue<Runnable>();
		return new ThreadPoolExecutor(threadPoolSize, threadPoolSize, 0L, TimeUnit.MILLISECONDS, taskQueue,
				createThreadFactory(threadPriority, "uil-pool-"));
	}

	
	public static Executor createTaskDistributor() {
		return Executors.newCachedThreadPool(createThreadFactory(Thread.NORM_PRIORITY, "uil-pool-d-"));
	}

	public static FileNameGenerator createFileNameGenerator() {
		return new HashCodeFileNameGenerator();
	}


	public static DiskCache createDiskCache(Context context, FileNameGenerator diskCacheFileNameGenerator,
											long diskCacheSize, int diskCacheFileCount) {
		File reserveCacheDir = createReserveDiskCacheDir(context);
		if (diskCacheSize > 0 || diskCacheFileCount > 0) {
			File individualCacheDir = StorageUtils.getIndividualCacheDirectory(context);
			try {
				return new LruDiskCache(individualCacheDir, reserveCacheDir, diskCacheFileNameGenerator, diskCacheSize,
						diskCacheFileCount);
			} catch (IOException e) {
				L.e(e);
				// continue and create unlimited cache
			}
		}
		File cacheDir = StorageUtils.getCacheDirectory(context);
		return new UnlimitedDiskCache(cacheDir, reserveCacheDir, diskCacheFileNameGenerator);
	}

	
	private static File createReserveDiskCacheDir(Context context) {
		File cacheDir = StorageUtils.getCacheDirectory(context, false);
		File individualDir = new File(cacheDir, "uil-images");
		if (individualDir.exists() || individualDir.mkdir()) {
			cacheDir = individualDir;
		}
		return cacheDir;
	}


	public static MemoryCache createMemoryCache(Context context, int memoryCacheSize) {
		if (memoryCacheSize == 0) {
			ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
			int memoryClass = am.getMemoryClass();
			if (hasHoneycomb() && isLargeHeap(context)) {
				memoryClass = getLargeMemoryClass(am);
			}
			memoryCacheSize = 1024 * 1024 * memoryClass / 8;
		}
		return new LruMemoryCache(memoryCacheSize);
	}

	private static boolean hasHoneycomb() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private static boolean isLargeHeap(Context context) {
		return (context.getApplicationInfo().flags & ApplicationInfo.FLAG_LARGE_HEAP) != 0;
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private static int getLargeMemoryClass(ActivityManager am) {
		return am.getLargeMemoryClass();
	}

	public static ImageDownloader createImageDownloader(Context context) {
		return new BaseImageDownloader(context);
	}

	public static ImageDecoder createImageDecoder(boolean loggingEnabled) {
		return new BaseImageDecoder(loggingEnabled);
	}

	public static BitmapDisplayer createBitmapDisplayer() {
		return new SimpleBitmapDisplayer();
	}

	private static ThreadFactory createThreadFactory(int threadPriority, String threadNamePrefix) {
		return new DefaultThreadFactory(threadPriority, threadNamePrefix);
	}

	private static class DefaultThreadFactory implements ThreadFactory {

		private static final AtomicInteger poolNumber = new AtomicInteger(1);

		private final ThreadGroup group;
		private final AtomicInteger threadNumber = new AtomicInteger(1);
		private final String namePrefix;
		private final int threadPriority;

		DefaultThreadFactory(int threadPriority, String threadNamePrefix) {
			this.threadPriority = threadPriority;
			group = Thread.currentThread().getThreadGroup();
			namePrefix = threadNamePrefix + poolNumber.getAndIncrement() + "-thread-";
		}

		@Override
		public Thread newThread(Runnable r) {
			Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
			if (t.isDaemon()) t.setDaemon(false);
			t.setPriority(threadPriority);
			return t;
		}
	}
}

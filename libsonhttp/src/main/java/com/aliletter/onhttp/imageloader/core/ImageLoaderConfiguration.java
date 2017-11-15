
package com.aliletter.onhttp.imageloader.core;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;


import com.aliletter.onhttp.imageloader.cache.disc.DiskCache;
import com.aliletter.onhttp.imageloader.cache.disc.naming.FileNameGenerator;
import com.aliletter.onhttp.imageloader.cache.memory.MemoryCache;
import com.aliletter.onhttp.imageloader.cache.memory.impl.FuzzyKeyMemoryCache;
import com.aliletter.onhttp.imageloader.core.assist.FlushedInputStream;
import com.aliletter.onhttp.imageloader.core.assist.ImageSize;
import com.aliletter.onhttp.imageloader.core.assist.QueueProcessingType;
import com.aliletter.onhttp.imageloader.core.decode.ImageDecoder;
import com.aliletter.onhttp.imageloader.core.download.ImageDownloader;
import com.aliletter.onhttp.imageloader.core.process.BitmapProcessor;
import com.aliletter.onhttp.imageloader.utils.L;
import com.aliletter.onhttp.imageloader.utils.MemoryCacheUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Executor;


public final class ImageLoaderConfiguration {

	final Resources resources;

	final int maxImageWidthForMemoryCache;
	final int maxImageHeightForMemoryCache;
	final int maxImageWidthForDiskCache;
	final int maxImageHeightForDiskCache;
	final BitmapProcessor processorForDiskCache;

	final Executor taskExecutor;
	final Executor taskExecutorForCachedImages;
	final boolean customExecutor;
	final boolean customExecutorForCachedImages;

	final int threadPoolSize;
	final int threadPriority;
	final QueueProcessingType tasksProcessingType;

	final MemoryCache memoryCache;
	final DiskCache diskCache;
	final ImageDownloader downloader;
	final ImageDecoder decoder;
	final DisplayImageOptions defaultDisplayImageOptions;

	final ImageDownloader networkDeniedDownloader;
	final ImageDownloader slowNetworkDownloader;

	private ImageLoaderConfiguration(final Builder builder) {
		resources = builder.context.getResources();
		maxImageWidthForMemoryCache = builder.maxImageWidthForMemoryCache;
		maxImageHeightForMemoryCache = builder.maxImageHeightForMemoryCache;
		maxImageWidthForDiskCache = builder.maxImageWidthForDiskCache;
		maxImageHeightForDiskCache = builder.maxImageHeightForDiskCache;
		processorForDiskCache = builder.processorForDiskCache;
		taskExecutor = builder.taskExecutor;
		taskExecutorForCachedImages = builder.taskExecutorForCachedImages;
		threadPoolSize = builder.threadPoolSize;
		threadPriority = builder.threadPriority;
		tasksProcessingType = builder.tasksProcessingType;
		diskCache = builder.diskCache;
		memoryCache = builder.memoryCache;
		defaultDisplayImageOptions = builder.defaultDisplayImageOptions;
		downloader = builder.downloader;
		decoder = builder.decoder;

		customExecutor = builder.customExecutor;
		customExecutorForCachedImages = builder.customExecutorForCachedImages;

		networkDeniedDownloader = new NetworkDeniedImageDownloader(downloader);
		slowNetworkDownloader = new SlowNetworkImageDownloader(downloader);

		L.writeDebugLogs(builder.writeLogs);
	}


	public static ImageLoaderConfiguration createDefault(Context context) {
		return new Builder(context).build();
	}

	ImageSize getMaxImageSize() {
		DisplayMetrics displayMetrics = resources.getDisplayMetrics();

		int width = maxImageWidthForMemoryCache;
		if (width <= 0) {
			width = displayMetrics.widthPixels;
		}
		int height = maxImageHeightForMemoryCache;
		if (height <= 0) {
			height = displayMetrics.heightPixels;
		}
		return new ImageSize(width, height);
	}

	
	public static class Builder {

		private static final String WARNING_OVERLAP_DISK_CACHE_PARAMS = "diskCache(), diskCacheSize() and diskCacheFileCount calls overlap each other";
		private static final String WARNING_OVERLAP_DISK_CACHE_NAME_GENERATOR = "diskCache() and diskCacheFileNameGenerator() calls overlap each other";
		private static final String WARNING_OVERLAP_MEMORY_CACHE = "memoryCache() and memoryCacheSize() calls overlap each other";
		private static final String WARNING_OVERLAP_EXECUTOR = "threadPoolSize(), threadPriority() and tasksProcessingOrder() calls "
				+ "can overlap taskExecutor() and taskExecutorForCachedImages() calls.";

		
		public static final int DEFAULT_THREAD_POOL_SIZE = 3;
		
		public static final int DEFAULT_THREAD_PRIORITY = Thread.NORM_PRIORITY - 2;
		
		public static final QueueProcessingType DEFAULT_TASK_PROCESSING_TYPE = QueueProcessingType.FIFO;

		private Context context;

		private int maxImageWidthForMemoryCache = 0;
		private int maxImageHeightForMemoryCache = 0;
		private int maxImageWidthForDiskCache = 0;
		private int maxImageHeightForDiskCache = 0;
		private BitmapProcessor processorForDiskCache = null;

		private Executor taskExecutor = null;
		private Executor taskExecutorForCachedImages = null;
		private boolean customExecutor = false;
		private boolean customExecutorForCachedImages = false;

		private int threadPoolSize = DEFAULT_THREAD_POOL_SIZE;
		private int threadPriority = DEFAULT_THREAD_PRIORITY;
		private boolean denyCacheImageMultipleSizesInMemory = false;
		private QueueProcessingType tasksProcessingType = DEFAULT_TASK_PROCESSING_TYPE;

		private int memoryCacheSize = 0;
		private long diskCacheSize = 0;
		private int diskCacheFileCount = 0;

		private MemoryCache memoryCache = null;
		private DiskCache diskCache = null;
		private FileNameGenerator diskCacheFileNameGenerator = null;
		private ImageDownloader downloader = null;
		private ImageDecoder decoder;
		private DisplayImageOptions defaultDisplayImageOptions = null;

		private boolean writeLogs = false;

		public Builder(Context context) {
			this.context = context.getApplicationContext();
		}

		
		public Builder memoryCacheExtraOptions(int maxImageWidthForMemoryCache, int maxImageHeightForMemoryCache) {
			this.maxImageWidthForMemoryCache = maxImageWidthForMemoryCache;
			this.maxImageHeightForMemoryCache = maxImageHeightForMemoryCache;
			return this;
		}

		@Deprecated
		public Builder discCacheExtraOptions(int maxImageWidthForDiskCache, int maxImageHeightForDiskCache,
				BitmapProcessor processorForDiskCache) {
			return diskCacheExtraOptions(maxImageWidthForDiskCache, maxImageHeightForDiskCache, processorForDiskCache);
		}


		public Builder diskCacheExtraOptions(int maxImageWidthForDiskCache, int maxImageHeightForDiskCache,
				BitmapProcessor processorForDiskCache) {
			this.maxImageWidthForDiskCache = maxImageWidthForDiskCache;
			this.maxImageHeightForDiskCache = maxImageHeightForDiskCache;
			this.processorForDiskCache = processorForDiskCache;
			return this;
		}

		public Builder taskExecutor(Executor executor) {
			if (threadPoolSize != DEFAULT_THREAD_POOL_SIZE || threadPriority != DEFAULT_THREAD_PRIORITY || tasksProcessingType != DEFAULT_TASK_PROCESSING_TYPE) {
				L.w(WARNING_OVERLAP_EXECUTOR);
			}

			this.taskExecutor = executor;
			return this;
		}


		public Builder taskExecutorForCachedImages(Executor executorForCachedImages) {
			if (threadPoolSize != DEFAULT_THREAD_POOL_SIZE || threadPriority != DEFAULT_THREAD_PRIORITY || tasksProcessingType != DEFAULT_TASK_PROCESSING_TYPE) {
				L.w(WARNING_OVERLAP_EXECUTOR);
			}

			this.taskExecutorForCachedImages = executorForCachedImages;
			return this;
		}


		public Builder threadPoolSize(int threadPoolSize) {
			if (taskExecutor != null || taskExecutorForCachedImages != null) {
				L.w(WARNING_OVERLAP_EXECUTOR);
			}

			this.threadPoolSize = threadPoolSize;
			return this;
		}

		public Builder threadPriority(int threadPriority) {
			if (taskExecutor != null || taskExecutorForCachedImages != null) {
				L.w(WARNING_OVERLAP_EXECUTOR);
			}

			if (threadPriority < Thread.MIN_PRIORITY) {
				this.threadPriority = Thread.MIN_PRIORITY;
			} else {
				if (threadPriority > Thread.MAX_PRIORITY) {
					this.threadPriority = Thread.MAX_PRIORITY;
				} else {
					this.threadPriority = threadPriority;
				}
			}
			return this;
		}


		public Builder denyCacheImageMultipleSizesInMemory() {
			this.denyCacheImageMultipleSizesInMemory = true;
			return this;
		}


		public Builder tasksProcessingOrder(QueueProcessingType tasksProcessingType) {
			if (taskExecutor != null || taskExecutorForCachedImages != null) {
				L.w(WARNING_OVERLAP_EXECUTOR);
			}

			this.tasksProcessingType = tasksProcessingType;
			return this;
		}


		public Builder memoryCacheSize(int memoryCacheSize) {
			if (memoryCacheSize <= 0) throw new IllegalArgumentException("memoryCacheSize must be a positive number");

			if (memoryCache != null) {
				L.w(WARNING_OVERLAP_MEMORY_CACHE);
			}

			this.memoryCacheSize = memoryCacheSize;
			return this;
		}


		public Builder memoryCacheSizePercentage(int availableMemoryPercent) {
			if (availableMemoryPercent <= 0 || availableMemoryPercent >= 100) {
				throw new IllegalArgumentException("availableMemoryPercent must be in range (0 < % < 100)");
			}

			if (memoryCache != null) {
				L.w(WARNING_OVERLAP_MEMORY_CACHE);
			}

			long availableMemory = Runtime.getRuntime().maxMemory();
			memoryCacheSize = (int) (availableMemory * (availableMemoryPercent / 100f));
			return this;
		}


		public Builder memoryCache(MemoryCache memoryCache) {
			if (memoryCacheSize != 0) {
				L.w(WARNING_OVERLAP_MEMORY_CACHE);
			}

			this.memoryCache = memoryCache;
			return this;
		}

		
		@Deprecated
		public Builder discCacheSize(int maxCacheSize) {
			return diskCacheSize(maxCacheSize);
		}


		public Builder diskCacheSize(int maxCacheSize) {
			if (maxCacheSize <= 0) throw new IllegalArgumentException("maxCacheSize must be a positive number");

			if (diskCache != null) {
				L.w(WARNING_OVERLAP_DISK_CACHE_PARAMS);
			}

			this.diskCacheSize = maxCacheSize;
			return this;
		}

		
		@Deprecated
		public Builder discCacheFileCount(int maxFileCount) {
			return diskCacheFileCount(maxFileCount);
		}


		public Builder diskCacheFileCount(int maxFileCount) {
			if (maxFileCount <= 0) throw new IllegalArgumentException("maxFileCount must be a positive number");

			if (diskCache != null) {
				L.w(WARNING_OVERLAP_DISK_CACHE_PARAMS);
			}

			this.diskCacheFileCount = maxFileCount;
			return this;
		}

		@Deprecated
		public Builder discCacheFileNameGenerator(FileNameGenerator fileNameGenerator) {
			return diskCacheFileNameGenerator(fileNameGenerator);
		}


		public Builder diskCacheFileNameGenerator(FileNameGenerator fileNameGenerator) {
			if (diskCache != null) {
				L.w(WARNING_OVERLAP_DISK_CACHE_NAME_GENERATOR);
			}

			this.diskCacheFileNameGenerator = fileNameGenerator;
			return this;
		}

		@Deprecated
		public Builder discCache(DiskCache diskCache) {
			return diskCache(diskCache);
		}

		public Builder diskCache(DiskCache diskCache) {
			if (diskCacheSize > 0 || diskCacheFileCount > 0) {
				L.w(WARNING_OVERLAP_DISK_CACHE_PARAMS);
			}
			if (diskCacheFileNameGenerator != null) {
				L.w(WARNING_OVERLAP_DISK_CACHE_NAME_GENERATOR);
			}

			this.diskCache = diskCache;
			return this;
		}


		public Builder imageDownloader(ImageDownloader imageDownloader) {
			this.downloader = imageDownloader;
			return this;
		}


		public Builder imageDecoder(ImageDecoder imageDecoder) {
			this.decoder = imageDecoder;
			return this;
		}


		public Builder defaultDisplayImageOptions(DisplayImageOptions defaultDisplayImageOptions) {
			this.defaultDisplayImageOptions = defaultDisplayImageOptions;
			return this;
		}


		public Builder writeDebugLogs() {
			this.writeLogs = true;
			return this;
		}

 		public ImageLoaderConfiguration build() {
			initEmptyFieldsWithDefaultValues();
			return new ImageLoaderConfiguration(this);
		}

		private void initEmptyFieldsWithDefaultValues() {
			if (taskExecutor == null) {
				taskExecutor = DefaultConfigurationFactory
						.createExecutor(threadPoolSize, threadPriority, tasksProcessingType);
			} else {
				customExecutor = true;
			}
			if (taskExecutorForCachedImages == null) {
				taskExecutorForCachedImages = DefaultConfigurationFactory
						.createExecutor(threadPoolSize, threadPriority, tasksProcessingType);
			} else {
				customExecutorForCachedImages = true;
			}
			if (diskCache == null) {
				if (diskCacheFileNameGenerator == null) {
					diskCacheFileNameGenerator = DefaultConfigurationFactory.createFileNameGenerator();
				}
				diskCache = DefaultConfigurationFactory
						.createDiskCache(context, diskCacheFileNameGenerator, diskCacheSize, diskCacheFileCount);
			}
			if (memoryCache == null) {
				memoryCache = DefaultConfigurationFactory.createMemoryCache(context, memoryCacheSize);
			}
			if (denyCacheImageMultipleSizesInMemory) {
				memoryCache = new FuzzyKeyMemoryCache(memoryCache, MemoryCacheUtils.createFuzzyKeyComparator());
			}
			if (downloader == null) {
				downloader = DefaultConfigurationFactory.createImageDownloader(context);
			}
			if (decoder == null) {
				decoder = DefaultConfigurationFactory.createImageDecoder(writeLogs);
			}
			if (defaultDisplayImageOptions == null) {
				defaultDisplayImageOptions = DisplayImageOptions.createSimple();
			}
		}
	}

	
	private static class NetworkDeniedImageDownloader implements ImageDownloader {

		private final ImageDownloader wrappedDownloader;

		public NetworkDeniedImageDownloader(ImageDownloader wrappedDownloader) {
			this.wrappedDownloader = wrappedDownloader;
		}

		@Override
		public InputStream getStream(String imageUri, Object extra) throws IOException {
			switch (Scheme.ofUri(imageUri)) {
				case HTTP:
				case HTTPS:
					throw new IllegalStateException();
				default:
					return wrappedDownloader.getStream(imageUri, extra);
			}
		}
	}

	private static class SlowNetworkImageDownloader implements ImageDownloader {

		private final ImageDownloader wrappedDownloader;

		public SlowNetworkImageDownloader(ImageDownloader wrappedDownloader) {
			this.wrappedDownloader = wrappedDownloader;
		}

		@Override
		public InputStream getStream(String imageUri, Object extra) throws IOException {
			InputStream imageStream = wrappedDownloader.getStream(imageUri, extra);
			switch (Scheme.ofUri(imageUri)) {
				case HTTP:
				case HTTPS:
					return new FlushedInputStream(imageStream);
				default:
					return imageStream;
			}
		}
	}
}

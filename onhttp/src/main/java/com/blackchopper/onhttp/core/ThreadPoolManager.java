package com.blackchopper.onhttp.core;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * author  : Black Chopper
 * e-mail  : 4884280@qq.com
 * github  : http://github.com/BlackChopper
 * project : OnHttp
 */

public class ThreadPoolManager {
    private static ThreadPoolManager instance;
    private ThreadPoolExecutor poolExecutor;
    private LinkedBlockingQueue<Future<?>> service = new LinkedBlockingQueue<>();
    private RejectedExecutionHandler handler = new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            try {
                service.put(new FutureTask<Object>(r, null));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    public ThreadPoolManager() {
        poolExecutor = new ThreadPoolExecutor(4, 10, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(4), handler);
        poolExecutor.execute(runnable);
    }

    public static ThreadPoolManager getInstance() {
        if (instance == null) {
            synchronized (ThreadPoolManager.class) {
                if (instance == null) {
                    instance = new ThreadPoolManager();
                }
            }
        }
        return instance;
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            while (true) {
                FutureTask futureTask = null;
                try {
                    futureTask = (FutureTask) service.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (futureTask != null) {
                    poolExecutor.execute(futureTask);
                }
            }
        }
    };

    public <T> void excute(FutureTask<T> futureTask) {
        if (futureTask != null) {
            try {
                service.put(futureTask);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

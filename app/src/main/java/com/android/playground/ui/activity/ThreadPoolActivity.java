package com.android.playground.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.android.playground.R;
import com.android.playground.base.BaseActivity;
import com.android.playground.base.toolbar.ToolbarFactory;
import com.android.playground.databinding.ActivityThreadPoolBinding;
import com.wangzhen.commons.toolbar.impl.Toolbar;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ThreadPoolActivity extends BaseActivity {
    private ActivityThreadPoolBinding binding;
    private StringBuilder stringBuilder;
    private ExecutorService cachedExecutor;
    private ExecutorService fixedExecutor;
    private ScheduledExecutorService scheduledExecutor;
    private ExecutorService singleExecutor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((binding = ActivityThreadPoolBinding.inflate(getLayoutInflater())).getRoot());
        addEvents();
    }

    public void addEvents() {
        binding.btnCachedthreadpool.setOnClickListener(v -> cachedThreadPool());
        binding.btnFixedthreadpool.setOnClickListener(v -> fixedThreadPool());
        binding.btnScheduledthreadpool.setOnClickListener(v -> scheduledThreadPool());
        binding.btnSinglethreadpool.setOnClickListener(v -> singleThreadPool());
    }

    private void shutdown() {
        if (cachedExecutor != null && !cachedExecutor.isShutdown()) {
            cachedExecutor.shutdown();
        }
        if (fixedExecutor != null && !fixedExecutor.isShutdown()) {
            fixedExecutor.shutdown();
        }
        if (scheduledExecutor != null && !scheduledExecutor.isShutdown()) {
            scheduledExecutor.shutdown();
        }
        if (singleExecutor != null && !singleExecutor.isShutdown()) {
            singleExecutor.shutdown();
        }
    }

    /**
     * newSingleThreadExecutor 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。
     */
    private void singleThreadPool() {
        shutdown();
        stringBuilder = new StringBuilder();
        singleExecutor = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 10; i++) {
            final int index = i;
            singleExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        stringBuilder.append("singleThreadPool:" + index + "\n");
                        Message message = Message.obtain();
                        message.obj = stringBuilder.toString();
                        handler.sendMessage(message);
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    /**
     * newScheduledThreadPool 创建一个定长线程池，支持定时及周期性任务执行。
     */
    private void scheduledThreadPool() {
        shutdown();
        stringBuilder = new StringBuilder();
        scheduledExecutor = Executors.newScheduledThreadPool(5);
        scheduledExecutor.schedule(new Runnable() {
            @Override
            public void run() {
                stringBuilder.append("scheduledThreadPool延迟3秒\n");
                Message message = Message.obtain();
                message.obj = stringBuilder.toString();
                handler.sendMessage(message);
            }
        }, 3, TimeUnit.SECONDS);
        scheduledExecutor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                stringBuilder.append("scheduledThreadPool延迟1秒，每3秒执行一次\n");
                Message message = Message.obtain();
                message.obj = stringBuilder.toString();
                handler.sendMessage(message);
            }
        }, 1, 3, TimeUnit.SECONDS);
    }

    /**
     * newFixedThreadPool 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
     */
    private void fixedThreadPool() {
        shutdown();
        stringBuilder = new StringBuilder();
        fixedExecutor = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 10; i++) {
            final int index = i;
            fixedExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        stringBuilder.append("fixedThreadPool:" + index + "\n");
                        Message message = Message.obtain();
                        message.obj = stringBuilder.toString();
                        handler.sendMessage(message);
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    /**
     * newCachedThreadPool创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
     * 线程池为无限大，当执行第二个任务时第一个任务已经完成，会复用执行第一个任务的线程，而不用每次新建线程。
     */
    private void cachedThreadPool() {
        shutdown();
        stringBuilder = new StringBuilder();
        cachedExecutor = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            final int index = i;
            cachedExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        stringBuilder.append("cachedThreadPool:" + index + "\n");
                        Message message = Message.obtain();
                        message.obj = stringBuilder.toString();
                        handler.sendMessage(message);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @SuppressLint("HandlerLeak")
    private final Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            String log = (String) msg.obj;
            binding.tvThreadpoolLog.setText(log);
        }
    };

    @Override
    public Toolbar createToolbar() {
        return ToolbarFactory.themed(this, getString(R.string.item_threadpool));
    }
}

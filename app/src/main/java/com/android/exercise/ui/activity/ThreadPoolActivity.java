package com.android.exercise.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolbarFactory;
import com.wangzhen.commons.toolbar.impl.Toolbar;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ThreadPoolActivity extends BaseActivity {

    @BindView(R.id.tv_threadpool_log)
    TextView tvThreadpoolLog;
    private StringBuilder stringBuilder;
    private ExecutorService cachedExecutor;
    private ExecutorService fixedExecutor;
    private ScheduledExecutorService scheduledExecutor;
    private ExecutorService singleExecutor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_pool);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_cachedthreadpool, R.id.btn_fixedthreadpool, R.id.btn_scheduledthreadpool, R.id.btn_singlethreadpool})
    public void onClick(View view) {
        shutdown();
        stringBuilder = new StringBuilder();
        switch (view.getId()) {
            case R.id.btn_cachedthreadpool:
                cachedThreadPool();
                break;
            case R.id.btn_fixedthreadpool:
                fixedThreadPool();
                break;
            case R.id.btn_scheduledthreadpool:
                scheduledThreadPool();
                break;
            case R.id.btn_singlethreadpool:
                singleThreadPool();
                break;
        }
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
    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            String log = (String) msg.obj;
            tvThreadpoolLog.setText(log);
        }
    };

    @Override
    public Toolbar createToolbar() {
        return ToolbarFactory.themed(this, getString(R.string.item_threadpool));
    }
}

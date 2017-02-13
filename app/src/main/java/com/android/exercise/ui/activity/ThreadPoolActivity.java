package com.android.exercise.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.base.toolbar.ToolBarCommonHolder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ThreadPoolActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_pool);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_cachedthreadpool, R.id.btn_fixedthreadpool, R.id.btn_scheduledthreadpool, R.id.btn_singlethreadpool})
    public void onClick(View view) {
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

    /**
     * newSingleThreadExecutor 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。
     */
    private void singleThreadPool() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 10; i++) {
            final int index = i;
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Log.e(TAG, "singleThreadPool:" + index);
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
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);
        scheduledExecutorService.schedule(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "scheduledThreadPool延迟3秒");
            }
        }, 3, TimeUnit.SECONDS);
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "scheduledThreadPool延迟1秒，每3秒执行一次" +
                        "");
            }
        }, 1, 3, TimeUnit.SECONDS);
    }

    /**
     * newFixedThreadPool 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
     */
    private void fixedThreadPool() {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 10; i++) {
            final int index = i;
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Log.e(TAG, "fixedThreadPool:" + index);
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
        ExecutorService executor = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            final int index = i;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    Log.e(TAG, "cachedThreadPool:" + index);
                }
            });
        }
    }

    @Override
    protected void onSetupToolbar(Toolbar toolbar, ActionBar actionBar) {
        new ToolBarCommonHolder(this, toolbar, getString(R.string.item_threadpool), true);
    }
}

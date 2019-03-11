package com.android.exercise.java.countdown;

import java.util.concurrent.CountDownLatch;

/**
 * BaseChecker
 * Created by wangzhen on 2019/3/11.
 */
public abstract class BaseChecker implements Runnable {

    private CountDownLatch latch;

    public BaseChecker(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            check();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (latch != null) {
                latch.countDown();
            }
        }
    }

    abstract void check();
}

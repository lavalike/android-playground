package com.android.exercise.java.countdown;

/**
 * BaseChecker
 * Created by wangzhen on 2019/3/11.
 */
public abstract class BaseChecker implements Runnable {

    private CountDownLatchWrapper wrapper;

    public BaseChecker(CountDownLatchWrapper wrapper) {
        this.wrapper = wrapper;
    }

    @Override
    public void run() {
        try {
            check();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (wrapper != null && wrapper.latch != null) {
                wrapper.latch.countDown();
            }
        }
    }

    public abstract void check();
}

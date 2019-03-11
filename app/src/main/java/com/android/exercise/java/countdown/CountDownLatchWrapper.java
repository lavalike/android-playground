package com.android.exercise.java.countdown;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatchWrapper
 * 解决CountDownLatch必须先指定count的问题
 * Created by wangzhen on 2019/3/11.
 */
public class CountDownLatchWrapper {
    public CountDownLatch latch;

    /**
     * 必须调用此方法初始化CountDownLatch
     *
     * @param size CountDownLatch count
     */
    public void count(int size) {
        latch = new CountDownLatch(size);
    }
}

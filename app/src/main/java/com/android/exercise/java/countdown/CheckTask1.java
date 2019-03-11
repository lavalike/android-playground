package com.android.exercise.java.countdown;

import java.util.concurrent.CountDownLatch;

/**
 * CheckTask1
 * Created by wangzhen on 2019/3/11.
 */
public class CheckTask1 extends BaseChecker {
    public CheckTask1(CountDownLatch latch) {
        super(latch);
    }

    @Override
    void check() {
        System.out.println("太阳帆板检查中...");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("太阳帆板展开正常");
    }
}

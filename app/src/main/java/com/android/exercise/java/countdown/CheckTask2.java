package com.android.exercise.java.countdown;

import java.util.concurrent.CountDownLatch;

/**
 * CheckTask1
 * Created by wangzhen on 2019/3/11.
 */
public class CheckTask2 extends BaseChecker {
    public CheckTask2(CountDownLatch latch) {
        super(latch);
    }

    @Override
    void check() {
        System.out.println("渭南瑞银雷达检查中...");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("渭南瑞银雷达跟踪正常，遥测信号正常");
    }
}

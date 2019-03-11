package com.android.exercise.java.countdown.task;

import com.android.exercise.java.countdown.BaseChecker;
import com.android.exercise.java.countdown.CountDownLatchWrapper;

import java.util.concurrent.CountDownLatch;

/**
 * CheckTask1
 * Created by wangzhen on 2019/3/11.
 */
public class CheckTask4 extends BaseChecker {
    public CheckTask4(CountDownLatchWrapper wrapper) {
        super(wrapper);
    }

    @Override
    public void check() {
        System.out.println("东风光学雷达检查中...");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("东风光学雷达跟踪正常，遥测信号正常");
    }
}

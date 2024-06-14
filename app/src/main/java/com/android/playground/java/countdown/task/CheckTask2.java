package com.android.playground.java.countdown.task;

import com.android.playground.java.countdown.BaseChecker;
import com.android.playground.java.countdown.CountDownLatchWrapper;

/**
 * CheckTask1
 * Created by wangzhen on 2019/3/11.
 */
public class CheckTask2 extends BaseChecker {
    public CheckTask2(CountDownLatchWrapper wrapper) {
        super(wrapper);
    }

    @Override
    public void check() {
        System.out.println("渭南瑞银雷达检查中...");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("渭南瑞银雷达跟踪正常，遥测信号正常");
    }
}

package com.android.exercise.java.countdown.task;

import com.android.exercise.java.countdown.BaseChecker;
import com.android.exercise.java.countdown.CountDownLatchWrapper;

/**
 * CheckTask1
 * Created by wangzhen on 2019/3/11.
 */
public class CheckTask5 extends BaseChecker {
    public CheckTask5(CountDownLatchWrapper wrapper) {
        super(wrapper);
    }

    @Override
    public void check() {
        System.out.println("东风助推器检查中...");
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("东风助推器分离，东风12分离");
    }
}

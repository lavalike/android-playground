package com.android.exercise.java.countdown;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * RocketLaunch 火箭发射流程
 * Created by wangzhen on 2019/3/11.
 */
public class RocketLaunch {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("准备发射");
        doCheck();
        System.out.println("发射成功");
    }

    private static void doCheck() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(5);
        List<BaseChecker> list = new ArrayList<>();
        list.add(new CheckTask1(latch));
        list.add(new CheckTask2(latch));
        list.add(new CheckTask3(latch));
        list.add(new CheckTask4(latch));
        list.add(new CheckTask5(latch));
        ExecutorService service = Executors.newFixedThreadPool(list.size());
        for (BaseChecker checker : list) {
            service.execute(checker);
        }
        latch.await();
    }
}

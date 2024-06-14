package com.android.playground.java.countdown;

import com.android.playground.java.countdown.task.CheckTask1;
import com.android.playground.java.countdown.task.CheckTask2;
import com.android.playground.java.countdown.task.CheckTask3;
import com.android.playground.java.countdown.task.CheckTask4;
import com.android.playground.java.countdown.task.CheckTask5;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * RocketLaunch 火箭发射流程
 * Created by wangzhen on 2019/3/11.
 */
public class RocketLaunch extends Thread {

    public static void main(String[] args) {
        new RocketLaunch().start();
    }

    @Override
    public void run() {
        try {
            System.out.println("准备发射");
            doCheck();
            System.out.println("发射成功");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void doCheck() throws InterruptedException {
        CountDownLatchWrapper wrapper = new CountDownLatchWrapper();

        List<BaseChecker> list = new ArrayList<>();
        list.add(new CheckTask1(wrapper));
        list.add(new CheckTask2(wrapper));
        list.add(new CheckTask3(wrapper));
        list.add(new CheckTask4(wrapper));
        list.add(new CheckTask5(wrapper));

        wrapper.count(list.size());

        ExecutorService service = Executors.newFixedThreadPool(list.size());
        for (BaseChecker checker : list) {
            service.execute(checker);
        }
        wrapper.latch.await();
    }
}

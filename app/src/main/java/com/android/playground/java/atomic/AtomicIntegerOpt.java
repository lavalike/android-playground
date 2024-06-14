package com.android.playground.java.atomic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * AtomicIntegerOpt
 * Created by wangzhen on 2019/2/27.
 */
public class AtomicIntegerOpt {
    private AtomicInteger ai = new AtomicInteger();
    private int i = 0;

    public static void main(String[] args) {
        final AtomicIntegerOpt cas = new AtomicIntegerOpt();
        List<Thread> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 100; j++) {
                        cas.unSafeCount();
                        cas.safeCount();
                    }
                }
            }));
        }

        for (Thread t : list) {
            t.start();
        }

        for (Thread t : list) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("非线程安全计数结果 --> " + cas.i);
        System.out.println("线程安全计数结果 --> " + cas.ai.get());
    }

    private void safeCount() {
        for (; ; ) {
            int i = ai.get();
            if (ai.compareAndSet(i, ++i))
                break;
        }
    }

    private void unSafeCount() {
        i++;
    }
}

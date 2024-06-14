package com.android.playground.java.barrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrierDemo
 * Created by wangzhen on 2019/3/11.
 */
public class CyclicBarrierDemo {
    public static void main(String[] args) {
        int threadNum = 5;
        CyclicBarrier barrier = new CyclicBarrier(threadNum, new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "最后完成任务");
            }
        });
        for (int i = 0; i < threadNum; i++) {
            new TaskThread(barrier).start();
        }
    }

    static class TaskThread extends Thread {
        private CyclicBarrier barrier;

        public TaskThread(CyclicBarrier barrier) {
            this.barrier = barrier;
        }

        @Override
        public void run() {
            try {
                Thread.sleep((long) (Math.random() * 3000));
                System.out.println(getName() + "到达");
                barrier.await();

                Thread.sleep((long) (Math.random() * 3000));
                System.out.println(getName() + "吃饭");
                barrier.await();

                Thread.sleep((long) (Math.random() * 3000));
                System.out.println(getName() + "离开");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }
}

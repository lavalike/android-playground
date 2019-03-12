package com.android.exercise.java.semaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * SemaphoreDemo 信号量
 * Created by wangzhen on 2019/3/12.
 */
public class SemaphoreDemo {
    private static final int N = 30;
    private static ExecutorService service = Executors.newFixedThreadPool(N);

    private static Semaphore semaphore = new Semaphore(5);

    public static void main(String[] args) {
        for (int i = 0; i < N; i++) {
            service.execute(new SemaphoreRunnable());
        }
    }

    static class SemaphoreRunnable implements Runnable {

        @Override
        public void run() {
            try {
                semaphore.acquire();
                String threadName = Thread.currentThread().getName();
                System.out.println(threadName + "进入");
                Thread.sleep(1000);
                System.out.println(threadName + "办理完成");
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

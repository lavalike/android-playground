package com.android.playground.java.thread;

/**
 * ThreadSync
 * Created by wangzhen on 2019/1/10.
 */
public class ThreadSync implements Runnable {
    private static ThreadSync instance;
    private static int i;

    public static void main(String[] args) throws InterruptedException {
        instance = new ThreadSync();
        Thread thread1 = new Thread(instance);
        Thread thread2 = new Thread(instance);
        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println(i);
    }

    @Override
    public void run() {
        for (int j = 0; j < 100000; j++) {
            i++;
        }
    }
}

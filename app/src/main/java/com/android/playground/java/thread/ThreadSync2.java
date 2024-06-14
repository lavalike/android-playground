package com.android.playground.java.thread;

/**
 * ThreadSync2
 * Created by wangzhen on 2019/3/4.
 */
public class ThreadSync2 {

    public static void main(String[] args) throws InterruptedException {
        Task1 task1 = new Task1();
        Task2 task2 = new Task2();

        task1.start();
        task2.start();

        task1.join();
        task2.join();
    }

    public static class Task1 extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                if (i == 0) {
                    System.out.println("\n");
                }
                System.out.println("task1 --> " + i);
            }
        }
    }

    public static class Task2 extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                if (i == 0) {
                    System.out.println("\n");
                }
                System.out.println("task2 --> " + i);
            }
        }
    }
}

package com.android.exercise.java.thread;

/**
 * MyThread
 * Created by wangzhen on 2019/3/8.
 */
public class MyThread extends Thread {
    private int i = 0;

    @Override
    public void run() {
        i++;
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("第一次：" + System.currentTimeMillis());
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("第二次：" + System.currentTimeMillis());
//            interrupt();
//            System.out.println("第一次调用thread.isInterrupted()：" + isInterrupted());
//            System.out.println("第二次调用thread.isInterrupted()：" + isInterrupted());
//
//            System.out.println("第一次调用thread.interrupted()：" + interrupted());
//            System.out.println("第二次调用thread.interrupted()：" + interrupted());
    }

    public static void main(String[] args) {
        MyThread thread = new MyThread();
        thread.start();
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.interrupt();
    }
}

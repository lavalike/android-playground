package com.android.exercise.java.lock;

/**
 * DeadLock
 * Created by wangzhen on 2019/3/8.
 */
public class DeadLock extends Thread {
    private String first;
    private String second;

    public DeadLock(String name, String first, String second) {
        super(name);
        this.first = first;
        this.second = second;
    }

    public void run() {
        synchronized (first) {
            System.out.println(this.getName() + " obtained: " + first);
            try {
                Thread.sleep(1000L);
                synchronized (second) {
                    System.out.println(this.getName() + " obtained: " + second);
                }
            } catch (InterruptedException e) {
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String lockA = "lockA";
        String lockB = "lockB";
        DeadLock t1 = new DeadLock("Thread1", lockA, lockB);
        DeadLock t2 = new DeadLock("Thread2", lockB, lockA);
        t1.start();
        t2.start();
    }

}

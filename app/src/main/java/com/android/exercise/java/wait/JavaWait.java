package com.android.exercise.java.wait;

/**
 * JavaWait 阻塞主线程
 * Created by wangzhen on 2019/1/21.
 */
public class JavaWait {
    private final Object lock = new Object();

    public static void main(String[] args) {
        final JavaWait javaWait = new JavaWait();
        new Thread() {
            @Override
            public void run() {
                javaWait.testWait(1000);
            }
        }.start();
        javaWait.testWait(1000 * 2);
        javaWait.testWait(1000 * 3);
    }

    private void testWait(long timeout) {
        if (timeout < 0) {
            throw new IllegalArgumentException("Negative timeout value");
        }
        synchronized (lock) {
            try {
                long start = System.currentTimeMillis();
                lock.wait(timeout);
                System.out.println("耗时 --> " + (System.currentTimeMillis() - start));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

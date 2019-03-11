package com.android.exercise.java.thread;

/**
 * ThreadCount 查找启动的线程数量
 * <p>
 * 线程number : 0  name : Reference Handler  // 计算对象是否可达？
 * 线程number : 1  name : Finalizer // 回收对象时触发的finalize方法？
 * 线程number : 2  name : Signal Dispatcher // 线程调度员
 * 线程number : 3  name : main
 * 线程number : 4  name : Monitor Ctrl-Break // 监控器，锁相关
 * </p>
 * Created by wangzhen on 2019/3/7.
 */
public class ThreadCount {
    public static void main(String[] args) {
        ThreadGroup group = Thread.currentThread().getThreadGroup();
        ThreadGroup topGroup = group;
        while (group != null) {
            topGroup = group;
            group = group.getParent();
        }
        int activeCount = topGroup.activeCount();
        Thread[] threads = new Thread[activeCount];
        topGroup.enumerate(threads);
        for (int i = 0; i < activeCount; i++) {
            System.out.println("线程number : " + i + "  name : " + threads[i].getName());
        }
    }
}

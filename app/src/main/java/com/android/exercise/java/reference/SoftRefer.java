package com.android.exercise.java.reference;

import java.lang.ref.SoftReference;

/**
 * SoftRefer 软引用
 * 软引用通过SoftReference类实现。 软引用的生命周期比强引用短一些。
 * 只有当 JVM 认为内存不足时，才会去试图回收软引用指向的对象：
 * 即JVM 会确保在抛出 OutOfMemoryError 之前，清理软引用指向的对象。
 * 软引用可以和一个引用队列（ReferenceQueue）联合使用，
 * 如果软引用所引用的对象被垃圾回收器回收，Java虚拟机就会把这个软引用加入到与之关联的引用队列中。
 * 后续，我们可以调用ReferenceQueue的poll()方法来检查是否有它所关心的对象被回收。
 * 如果队列为空，将返回一个null,否则该方法返回队列中前面的一个Reference对象
 * <p>
 * 应用场景：软引用通常用来实现内存敏感的缓存。如果还有空闲内存，就可以暂时保留缓存，当内存不足时清理掉，这样就保证了使用缓存的同时，不会耗尽内存。
 * Created by wangzhen on 2019/1/21.
 */
public class SoftRefer {
    public static void main(String[] args) {
        Object object = new Object();
        System.out.println("初始object --> " + object);
        SoftReference<Object> reference = new SoftReference<>(object);
        object = null;
        System.gc();
        Object refObj = reference.get();
        if (refObj != null) {
            System.out.println("取到object --> " + refObj);
        } else {
            System.out.println("object已被回收");
        }
    }
}

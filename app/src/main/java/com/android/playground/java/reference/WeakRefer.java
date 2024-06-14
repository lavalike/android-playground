package com.android.playground.java.reference;

import java.lang.ref.WeakReference;

/**
 * WeakRefer 弱引用
 * 弱引用通过WeakReference类实现。 弱引用的生命周期比软引用短。
 * 在垃圾回收器线程扫描它所管辖的内存区域的过程中，一旦发现了具有弱引用的对象，
 * 不管当前内存空间足够与否，都会回收它的内存。由于垃圾回收器是一个优先级很低的线程，
 * 因此不一定会很快回收弱引用的对象。弱引用可以和一个引用队列（ReferenceQueue）联合使用，
 * 如果弱引用所引用的对象被垃圾回收，Java虚拟机就会把这个弱引用加入到与之关联的引用队列中
 * <p>
 * 应用场景：弱应用同样可用于内存敏感的缓存。
 * Created by wangzhen on 2019/1/21.
 */
public class WeakRefer {
    public static void main(String[] args) {
        Object object = new Object();
        System.out.println("初始object --> " + object);
        WeakReference<Object> reference = new WeakReference<>(object);
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

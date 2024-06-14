package com.android.playground.java.reference;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;

/**
 * PhantomRefer 虚引用
 * 虚引用也叫幻象引用，通过PhantomReference类来实现。无法通过虚引用访问对象的任何属性或函数。幻象引用仅仅是提供了一种确保对象被 finalize 以后，做某些事情的机制。如果一个对象仅持有虚引用，那么它就和没有任何引用一样，在任何时候都可能被垃圾回收器回收。虚引用必须和引用队列 （ReferenceQueue）联合使用。当垃圾回收器准备回收一个对象时，如果发现它还有虚引用，就会在回收对象的内存之前，把这个虚引用加入到与之关联的引用队列中。
 * ReferenceQueue queue = new ReferenceQueue ();
 * PhantomReference pr = new PhantomReference (object, queue);
 * 程序可以通过判断引用队列中是否已经加入了虚引用，来了解被引用的对象是否将要被垃圾回收。如果程序发现某个虚引用已经被加入到引用队列，那么就可以在所引用的对象的内存被回收之前采取一些程序行动
 * <p>
 * 应用场景：可用来跟踪对象被垃圾回收器回收的活动，当一个虚引用关联的对象被垃圾收集器回收之前会收到一条系统通知。
 * <p>
 * 强引用就像大老婆，关系很稳固。
 * 软引用就像二老婆，随时有失宠的可能，但也有扶正的可能。
 * 弱引用就像情人，关系不稳定，可能跟别人跑了。
 * 幻像引用就是梦中情人，只在梦里出现过。
 * Created by wangzhen on 2019/1/21.
 */
public class PhantomRefer {
    public static void main(String[] args) {
        Object object = new Object();
        ReferenceQueue<Object> refQueue = new ReferenceQueue<>();
        PhantomReference<Object> p = new PhantomReference<>(object, refQueue);
        System.out.println("初始PhantomReference --> " + p);
        object = null;
        System.gc();
        try {
            Reference<?> ref = refQueue.remove();
            if (ref != null) {
                System.out.println("取到PhantomReference --> " + ref + " object对象已被回收");
            } else {
                System.out.println("PhantomReference == null");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

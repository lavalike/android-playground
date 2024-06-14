package com.android.playground.java.classloader;

public class Test {
    public void hello() {
        System.out.println("我是由" + getClass().getClassLoader().getClass() + "加载进来的");
    }
}

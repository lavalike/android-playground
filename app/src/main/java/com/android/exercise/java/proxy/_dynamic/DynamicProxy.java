package com.android.exercise.java.proxy._dynamic;

import java.lang.reflect.Proxy;

public class DynamicProxy {
    public static void main(String[] args) {
        RealSubject realSubject = new RealSubject();
        Subject subject = (Subject) Proxy.newProxyInstance(Subject.class.getClassLoader(), new Class[]{Subject.class}, new ProxyHandler(realSubject));
        subject.doSomething();
    }
}

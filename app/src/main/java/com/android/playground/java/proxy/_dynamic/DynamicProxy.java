package com.android.playground.java.proxy._dynamic;

public class DynamicProxy {
    public static void main(String[] args) {
//        Subject instance = new RealSubject();
//        Subject subject = (Subject) Proxy.newProxyInstance(Subject.class.getClassLoader(), new Class[]{Subject.class}, new ProxyHandler<>(instance));

        Subject subject = ProxyClient.create(Subject.class, RealSubject.class);
        subject.doSomething();
    }
}

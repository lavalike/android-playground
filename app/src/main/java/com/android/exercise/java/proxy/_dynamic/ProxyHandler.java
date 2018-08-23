package com.android.exercise.java.proxy._dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ProxyHandler implements InvocationHandler {
    private Object proxyObject;

    public ProxyHandler(Object proxyObject) {
        this.proxyObject = proxyObject;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("准备工作之前");
        Object invoke = method.invoke(proxyObject, args);
        System.out.println("工作完成");
        return invoke;
    }
}

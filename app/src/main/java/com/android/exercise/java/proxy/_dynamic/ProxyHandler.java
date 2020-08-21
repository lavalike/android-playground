package com.android.exercise.java.proxy._dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ProxyHandler<T> implements InvocationHandler {
    private T mProxy;

    public ProxyHandler(T proxy) {
        this.mProxy = proxy;
    }

    @Override
    public T invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("-> 准备工作");
        Object invoke = method.invoke(mProxy, args);
        System.out.println("-> 工作完成");
        return (T)invoke;
    }
}

package com.android.playground.java.proxy._dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * ProxyClient
 * Created by wangzhen on 2020/8/21.
 */
public class ProxyClient {
    public static <T> T create(Class<T> proxy, Class<?> impl) {
        return (T) Proxy.newProxyInstance(proxy.getClassLoader(), new Class[]{proxy}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("-> 准备工作");
                Object object = method.invoke(impl.newInstance(), args);
                System.out.println("-> 工作完成");
                return object;
            }
        });
    }
}

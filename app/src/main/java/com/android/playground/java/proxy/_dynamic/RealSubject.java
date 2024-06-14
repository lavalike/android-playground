package com.android.playground.java.proxy._dynamic;

public class RealSubject implements Subject {
    @Override
    public void doSomething() {
        System.out.println("一切都是我指使的 -> " + getClass());
    }
}

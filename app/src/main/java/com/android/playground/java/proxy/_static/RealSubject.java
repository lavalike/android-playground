package com.android.playground.java.proxy._static;

public class RealSubject implements Subject {
    @Override
    public String doSomething(String msg) {
        return "RealSubject ->" + msg;
    }
}

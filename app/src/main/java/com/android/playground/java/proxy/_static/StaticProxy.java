package com.android.playground.java.proxy._static;

public class StaticProxy {
    public static void main(String[] args) {
        Subject subject = new RealSubject();
        Subject subjectProxy = new SubjectProxy(subject);
        System.out.println(subjectProxy.doSomething("hello"));
    }
}

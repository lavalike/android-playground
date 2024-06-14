package com.android.playground.java.proxy._static;

public class SubjectProxy implements Subject {
    private Subject subject;

    public SubjectProxy(Subject subject) {
        this.subject = subject;
    }

    @Override
    public String doSomething(String msg) {
        System.out.println("before calling doSomething()");
        String echo = subject.doSomething(msg);
        System.out.println("after calling doSomething()");
        return echo;
    }
}

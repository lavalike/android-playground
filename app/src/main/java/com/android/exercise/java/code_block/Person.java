package com.android.exercise.java.code_block;

public class Person {

    static {
        System.out.println("Person静态代码块执行");
    }

    {
        System.out.println("Person构造代码块执行");
    }

    public Person() {
        System.out.println("Person构造函数执行");
    }

    public void saySomething() {
        System.out.println("Person saySomething");
    }
}

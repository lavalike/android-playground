package com.android.exercise.java.code_block;

public class Man extends Person {

    static {
        System.out.println("Man静态代码块执行");
    }

    {
        System.out.println("Man构造代码块执行");
    }

    public Man() {
        System.out.println("Man构造函数执行");
    }

    @Override
    public void saySomething() {
        super.saySomething();
        System.out.println("Man saySomething");
        {
            System.out.println("Man saySomething block 1");
        }
        {
            System.out.println("Man saySomething block 2");
        }
        {
            System.out.println("Man saySomething block 3");
        }
    }
}

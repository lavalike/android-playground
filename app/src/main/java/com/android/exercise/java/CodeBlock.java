package com.android.exercise.java;

public class CodeBlock {
    static {
        System.out.println("静态代码块执行");
    }

    {
        System.out.println("构造代码块执行");
    }

    public static void main(String[] args) {
        new CodeBlock();
        System.out.println("构造函数执行");
    }
}

package com.android.playground.java;

/**
 * 通过递归调用，申请栈空间来超出栈的深度
 * Created by wangzhen on 2018/10/31.
 */
public class StackOverflowError {
    private int stackLength = 1;

    public static void main(String[] args) {
        StackOverflowError stackOverflowError = new StackOverflowError();
        try {
            stackOverflowError.stackLeak();
        } catch (Throwable e) {
            System.out.println("stack length -> " + stackOverflowError.stackLength);
        }
    }

    private void stackLeak() {
        stackLength++;
        stackLeak();
    }
}

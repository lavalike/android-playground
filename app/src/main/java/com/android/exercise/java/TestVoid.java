package com.android.exercise.java;

/**
 * TestVoid
 * Created by wangzhen on 2019/3/4.
 */
public class TestVoid {

    public TestVoid() {
        this(method());
    }

    private TestVoid(Void ignore) {
    }

    private static Void method() {
        System.out.println("调用method()");
        return null;
    }

    public static void main(String[] args) {
        TestVoid testVoid = new TestVoid();
    }
}

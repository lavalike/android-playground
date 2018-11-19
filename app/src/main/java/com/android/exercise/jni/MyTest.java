package com.android.exercise.jni;

public class MyTest {
    static {
        System.loadLibrary("main-lib");
    }

    public static native String stringFromJni();

    public static native int add(int a, int b);
}

package com.android.exercise.java.intern;

public class JavaIntern {
    public static void main(String[] args) {
        String str = new String("a");
        String str1 = "a";

        System.out.println(str.intern() == str1);
    }
}

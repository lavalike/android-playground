package com.android.exercise.java.intern;

public class JavaIntern {
    public static void main(String[] args) {
        String str = new String("a");
        String intern = str.intern();
        String str1 = "a";

        System.out.println(str == intern);
    }
}

package com.android.playground.java.intern;

public class JavaIntern {
    public static void main(String[] args) {
        String str = new String("a");
        String str1 = "a";
        System.out.println(str.intern() == str1);

        Integer i = 127;
        Integer j = 127;
        System.out.println(i == j);
    }
}

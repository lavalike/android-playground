package com.android.exercise.java;

/**
 * 局部内部类
 * Created by wangzhen on 2018/10/26.
 */
public class LocalInnerClass {
    public static void main(String[] args) {
        System.out.println(new Man().getPeople().getAge());
    }
}

class People {
    private int age = 0;

    public int getAge() {
        return age;
    }
}

class Man {
    People getPeople() {
        class Woman extends People {
            @Override
            public int getAge() {
                return 26;
            }
        }
        return new Woman();
    }
}
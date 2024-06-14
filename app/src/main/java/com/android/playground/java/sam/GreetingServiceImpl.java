package com.android.playground.java.sam;

/**
 * GreetingServiceImpl
 * Created by wangzhen on 2019/3/5.
 */
public class GreetingServiceImpl implements GreetingService {
    @Override
    public void say(String message) {
        System.out.println("GreetingServiceImpl --> " + message);
    }

    @Override
    public void doSomething() {
        System.out.println("GreetingServiceImpl --> doSomething");
    }
}

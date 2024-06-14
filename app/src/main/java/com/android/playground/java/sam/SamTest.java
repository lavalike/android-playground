package com.android.playground.java.sam;

/**
 * SamTest
 * SAM --> Single Abstract Method interfaces
 * Created by wangzhen on 2019/3/5.
 */
public class SamTest {

    public static void main(String[] args) {
        GreetingService service = new GreetingServiceImpl();
        service.say("你好");
        service.doSomething();
        service.doSomething1();
        GreetingService.doSomething2();
    }
}

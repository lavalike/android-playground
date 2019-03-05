package com.android.exercise.java.sam;

/**
 * GreetingService
 * Created by wangzhen on 2019/3/5.
 */
@FunctionalInterface
public interface GreetingService {

    /**
     * abstract method
     *
     * @param message message
     */
    void say(String message);

    /**
     * default method doSomething
     */
    default void doSomething() {
        System.out.println("GreetingService --> doSomething");
    }

    /**
     * default method doSomething1
     */
    default void doSomething1() {
        System.out.println("GreetingService --> doSomething1");
    }

    /**
     * static method doSomething2
     */
    static void doSomething2() {
        System.out.println("GreetingService --> doSomething2");
    }
}
